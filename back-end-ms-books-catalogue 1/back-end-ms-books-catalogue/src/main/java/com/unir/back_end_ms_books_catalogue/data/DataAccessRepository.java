package com.unir.back_end_ms_books_catalogue.data;


import com.unir.back_end_ms_books_catalogue.controller.model.AggregationDetails;
import com.unir.back_end_ms_books_catalogue.data.model.Book;
import com.unir.back_end_ms_books_catalogue.utils.Consts;
import com.unir.back_end_ms_books_catalogue.controller.model.BooksQueryResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Repository
@RequiredArgsConstructor
@Slf4j
public class DataAccessRepository {

    private final BookRepositoryElastic bookRepositoryElastic;
    private final ElasticsearchOperations elasticClient;

    private final String[] addressFields = {"address", "address._2gram", "address._3gram"};

    @SneakyThrows
    public BooksQueryResponse findBooks(
            List<String> categoryValues,
            List<String> authorValues,
            List<String> isbnValues,
            List<String> publicationDateValues,
            List<String> ratingValues,
            String title,
            String address,
            String page) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        // Filtros de categoría
        if (categoryValues != null && !categoryValues.isEmpty()) {
            categoryValues.forEach(
                    category -> querySpec.must(QueryBuilders.termQuery(Consts.FIELD_CATEGORY_ID, category))
            );
        }

        // Filtro por título
        if (StringUtils.isNotEmpty(title)) {
            querySpec.must(QueryBuilders.matchQuery(Consts.FIELD_TITLE, title));
        }

        // Filtro por dirección
        if (StringUtils.isNotEmpty(address)) {
            querySpec.must(QueryBuilders.multiMatchQuery(address, addressFields)
                    .type(MultiMatchQueryBuilder.Type.BOOL_PREFIX));
        }

        // Filtro por autor
        if (authorValues != null && !authorValues.isEmpty()) {
            authorValues.forEach(
                    author -> querySpec.must(QueryBuilders.termQuery(Consts.FIELD_AUTHOR, author))
            );
        }

        // Filtro por ISBN
        if (isbnValues != null && !isbnValues.isEmpty()) {
            isbnValues.forEach(
                    isbn -> querySpec.must(QueryBuilders.termQuery(Consts.FIELD_ISBN, isbn))
            );
        }

        // Filtro por fecha de publicación
        if (publicationDateValues != null && !publicationDateValues.isEmpty()) {
            publicationDateValues.forEach(
                    dateRange -> {
                        String[] range = dateRange.split("-");
                        if (range.length == 2) {
                            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_PUBLICATION_DATE)
                                    .from(range[0])
                                    .to(range[1])
                                    .includeUpper(false));
                        } else if (range.length == 1) {
                            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_PUBLICATION_DATE)
                                    .from(range[0]));
                        }
                    }
            );
        }

        // Filtro por rating
        if (ratingValues != null && !ratingValues.isEmpty()) {
            ratingValues.forEach(
                    ratingRange -> {
                        String[] range = ratingRange.split("-");
                        if (range.length == 2) {
                            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_RATING)
                                    .from(range[0])
                                    .to(range[1])
                                    .includeUpper(false));
                        } else if (range.length == 1) {
                            querySpec.must(QueryBuilders.rangeQuery(Consts.FIELD_RATING)
                                    .from(range[0]));
                        }
                    }
            );
        }

        // Si no hay filtros, agregamos un `matchAllQuery`
        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        // Construcción de la consulta
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        // Agregaciones de términos
        searchQueryBuilder.addAggregation(AggregationBuilders
                .terms(Consts.AGG_KEY_TERM_CATEGORY)
                .field(Consts.FIELD_CATEGORY_ID)
                .size(10000));

        searchQueryBuilder.addAggregation(AggregationBuilders
                .terms(Consts.AGG_KEY_TERM_AUTHOR)
                .field(Consts.FIELD_AUTHOR)
                .size(10000));

        searchQueryBuilder.addAggregation(AggregationBuilders
                .terms(Consts.AGG_KEY_TERM_ISBN)
                .field(Consts.FIELD_ISBN)
                .size(10000));

        // Agregaciones de rango
        searchQueryBuilder.addAggregation(AggregationBuilders
                .range(Consts.AGG_KEY_RANGE_PUBLICATION_DATE)
                .field(Consts.FIELD_PUBLICATION_DATE)
                .addUnboundedTo("before_2000", 2000)
                .addRange("2000-2010", 2000, 2010)
                .addUnboundedFrom("after_2010", 2010));


        searchQueryBuilder.addAggregation(AggregationBuilders
                .range(Consts.AGG_KEY_RANGE_RATING)
                .field(Consts.FIELD_RATING)
                .addUnboundedTo("low", 2.0)
                .addRange("medium", 2.0, 4.0)
                .addUnboundedFrom("high", 4.0));

        // Configuración de paginación
        int pageInt = Integer.parseInt(page);
        if (pageInt >= 0) {
            searchQueryBuilder.withPageable(PageRequest.of(pageInt, 5));
        }

        Query query = searchQueryBuilder.build();
        SearchHits<Book> result = elasticClient.search(query, Book.class);
        return new BooksQueryResponse(getResponseBooks(result), getResponseAggregations(result));
    }

    private List<Book> getResponseBooks(SearchHits<Book> result) {
        return result.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    private Map<String, List<AggregationDetails>> getResponseAggregations(SearchHits<Book> result) {
        Map<String, List<AggregationDetails>> responseAggregations = new HashMap<>();

        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();

            aggs.forEach((key, value) -> {
                responseAggregations.putIfAbsent(key, new LinkedList<>());

                if (value instanceof ParsedStringTerms parsedStringTerms) {
                    parsedStringTerms.getBuckets().forEach(bucket ->
                            responseAggregations.get(key).add(new AggregationDetails(bucket.getKeyAsString(), (int) bucket.getDocCount())));
                }

                if (value instanceof ParsedRange parsedRange) {
                    parsedRange.getBuckets().forEach(bucket ->
                            responseAggregations.get(key).add(new AggregationDetails(bucket.getKeyAsString(), (int) bucket.getDocCount())));
                }
            });
        }
        return responseAggregations;
    }
}