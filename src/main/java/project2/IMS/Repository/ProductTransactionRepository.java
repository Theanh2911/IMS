package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project2.IMS.Entity.ProductTransaction;
import project2.IMS.Entity.ProductTransactionId;

import java.util.List;

@Repository
public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, ProductTransactionId> {
    
    List<ProductTransaction> findByTransactionId(Integer transactionId);
    
    List<ProductTransaction> findByProductId(Integer productId);
    
    @Query("SELECT pt FROM ProductTransaction pt JOIN FETCH pt.product p JOIN FETCH pt.transaction t WHERE pt.transactionId = :transactionId")
    List<ProductTransaction> findByTransactionIdWithProductAndTransaction(@Param("transactionId") Integer transactionId);
} 