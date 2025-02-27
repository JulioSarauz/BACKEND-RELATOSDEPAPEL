para almacenar las categorias usar: 


curl.exe -X POST "https://b99laoc4p7:9hjczl71x6@coac29-search-3478814420.us-east-1.bonsaisearch.net:443/_bulk" `
>>     -H "Content-Type: application/json" `
>>     --data-binary "@categorias.json"



Ahora para ingresar los libros es con:

curl.exe -X POST "https://b99laoc4p7:9hjczl71x6@coac29-search-3478814420.us-east-1.bonsaisearch.net:443/_bulk" `
>>     -H "Content-Type: application/json" `
>>     --data-binary "@bulk_data.json"