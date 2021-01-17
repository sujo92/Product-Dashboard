# Product-Dashboard

Rest API that add, update and fetch products
The project by default supports H2 database.


## Endpoints:

- POST: Http://localhost:8000/products : 
add products if product with same Id present http response 400 is sent.otherwise reponse code 201 is sent.

- PUT: Http://localhost:8000/products/{product_id} :
update a product of the given ID. If not present Http response 400 is sent. otherwise 200 is sent.

- GET: Http://localhost:8000/products/{product_id} :
find product by id. If not present http response 404 is sent, otherwise 200 is sent.

- GET: Http://localhost:8000/products?category={category} :
return product by category. Http reonse 200 is sent.
Json array is sorted by availability(In stock before out of stock products). If product with same availability are present then sort them based on discounted price.Finally product with same discounted price should be sorted with id.

- GET: Http://localhost:8000/products?category={category}&availability={availability}: 
return array of all products with given availability and category. availability is given by 0(false), 1(true). Http reponse id 200. Json array should be sorted by discounted percentage in descending order.products with same discount percentage are then sorted by discounted price in ascending order. Finally product with same discounted price are sorted by id in ascending order.
	
	discounted percentage= ((retail_price - discounted_price)/retail_price) * 100


- GET: Http://localhost:8000/products :
 get all products. Response code should be 200. products are sorted by id in ascending order.
