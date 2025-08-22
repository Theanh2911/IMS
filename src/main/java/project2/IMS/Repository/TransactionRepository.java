package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project2.IMS.Entity.Transaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findByTransactionNumber(String transactionNumber);
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "LOWER(t.transactionNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Transaction> searchTransactions(@Param("searchTerm") String searchTerm);
} 