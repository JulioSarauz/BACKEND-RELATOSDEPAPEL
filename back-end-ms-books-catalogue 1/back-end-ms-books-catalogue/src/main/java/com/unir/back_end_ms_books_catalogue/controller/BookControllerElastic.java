package com.unir.back_end_ms_books_catalogue.controller;
import com.unir.back_end_ms_books_catalogue.controller.model.BooksQueryResponse;
import com.unir.back_end_ms_books_catalogue.service.BookServiceElastic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class BookControllerElastic {
    private final BookServiceElastic service;

    @GetMapping("/bookelastic")
    public ResponseEntity<BooksQueryResponse> getBooks(
            @RequestParam(required = false) List<String> genderValues,
            @RequestParam(required = false) List<String> designationValues,
            @RequestParam(required = false) List<String> civilStatusValues,
            @RequestParam(required = false) List<String> ageValues,
            @RequestParam(required = false) List<String> salaryValues,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false, defaultValue = "0") String page) {

        BooksQueryResponse response = service.getBooks( 
                genderValues,
                designationValues,
                civilStatusValues,
                ageValues,
                salaryValues,
                name,
                address,
                page);
        return ResponseEntity.ok(response);
    }
}
