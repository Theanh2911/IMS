package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project2.IMS.Entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);
    
    @Query("SELECT p FROM Product p WHERE p.quantity < :threshold ORDER BY p.quantity ASC")
    List<Product> findProductsWithLowStock(@Param("threshold") Integer threshold);
    
    @Query("SELECT p FROM Product p WHERE p.quantity > :threshold ORDER BY p.quantity DESC")
    List<Product> findProductsWithHighStock(@Param("threshold") Integer threshold);
}
