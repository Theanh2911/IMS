package project2.IMS.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransactionId implements Serializable {
    private Integer productId;
    private Integer transactionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTransactionId that = (ProductTransactionId) o;
        return Objects.equals(productId, that.productId) && 
               Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, transactionId);
    }
} 