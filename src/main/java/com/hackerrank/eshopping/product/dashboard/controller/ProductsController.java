package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {
    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<HttpStatus> addProducts(@RequestBody Product product){
        return productService.addProducts(product);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<HttpStatus> updateProductById(@PathVariable("product_id") Long product_id, @RequestBody Product product){
        return productService.updateProductById(product_id,product);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<?> getProductById(@PathVariable("product_id") Long product_id){
        return productService.getProductById(product_id);
    }

    @GetMapping(params = {"category"})
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category){
        return productService.getProductsByCategory(category);
    }

    @GetMapping(params = {"category","availability"})
    public ResponseEntity<List<Product>> getProductsByCategoryAndAvailability(@RequestParam String category,@RequestParam int availability) throws UnsupportedEncodingException {
        String decoded = URLDecoder.decode(category, "UTF-8");
        if(availability==0)
            return productService.getProductsByCategoryAndAvailability(decoded,false);
        else
            return productService.getProductsByCategoryAndAvailability(decoded, true);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        return productService.getAllProducts();
    }
}
