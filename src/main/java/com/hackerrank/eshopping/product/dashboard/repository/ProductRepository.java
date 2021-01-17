package com.hackerrank.eshopping.product.dashboard.repository;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    Optional<Product> findById(Long aLong);

    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.retailPrice =:retail_price, p.discountedPrice=:discounted_price, p.availability=:availability where p.id =:product_id")
    public void updateProduct(@Param("product_id") Long product_id,@Param("retail_price") Double retail_price, @Param("discounted_price") Double discounted_price,@Param("availability")boolean availability);

    Optional<List<Product>> findByCategory(String category);

    Optional<List<Product>> findByCategoryAndAvailability(String category,boolean availability);
}
