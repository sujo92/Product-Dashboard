package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<HttpStatus> addProducts(Product product){
        Long id = product.getId();
        if(productRepository.findById(id).isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<HttpStatus> updateProductById(Long product_id, Product product) {
        if(productRepository.findById(product_id).isPresent()){
            productRepository.updateProduct(product_id,product.getRetailPrice(),product.getDiscountedPrice(),product.getAvailability());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity getProductById(Long product_id) {
        Optional<Product> product = productRepository.findById(product_id);
        if(product.isPresent()){
            Product product1 = product.get();
            return new ResponseEntity<>(product1,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Product>> getProductsByCategory(String category) {
        Optional<List<Product>> products = productRepository.findByCategory(category);
        if(products.isPresent()){
            Comparator<Product> compareByDiscountAndId = Comparator.comparing(Product::getAvailability).reversed()
                    .thenComparing(Product::getDiscountedPrice)
                    .thenComparing(Product::getId);
            List<Product> sortedProducts = products.get().stream()
                    .sorted(compareByDiscountAndId).collect(Collectors.toList());
            return new ResponseEntity(sortedProducts,HttpStatus.OK);
        }
        return new ResponseEntity(new ArrayList<Product>(),HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getProductsByCategoryAndAvailability(String category,boolean availability) {
        Optional<List<Product>> products = productRepository.findByCategoryAndAvailability(category,availability);

        if(products.isPresent()){
            List<Product> productList = products.get();

            Collections.sort(productList, (a,b)->{
                long ADiscountPercentage = Math.round(((a.getRetailPrice()-a.getDiscountedPrice())/a.getRetailPrice())*100);
                long BDiscountPercentage = Math.round(((b.getRetailPrice()-b.getDiscountedPrice())/b.getRetailPrice())*100);

                int comp = Long.compare(BDiscountPercentage,ADiscountPercentage);
                if(comp==0){
                    int comp2= Double.compare(a.getDiscountedPrice(),b.getDiscountedPrice());
                       if(comp2==0){
                           return Long.compare(a.getId(),b.getId());
                       }
                       return comp2;
                    }
                    return comp;
            });

            return new ResponseEntity(productList,HttpStatus.OK);
        }
        return new ResponseEntity(new ArrayList<Product>(),HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<Product> finalProducts = products.stream().sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
        return new ResponseEntity<>(finalProducts,HttpStatus.OK);
    }
}
