package project2.IMS.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_transaction")
@IdClass(ProductTransactionId.class)
public class ProductTransaction {
    
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Id
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", insertable = false, updatable = false)
    private Transaction transaction;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price_at_transaction", precision = 10, scale = 2)
    private BigDecimal priceAtTransaction;

    // getters and setters
}