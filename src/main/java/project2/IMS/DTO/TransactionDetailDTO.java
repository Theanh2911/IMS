package project2.IMS.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project2.IMS.Entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDetailDTO {
    private Integer id;
    private String transactionNumber;
    private LocalDateTime transactionDate;
    private BigDecimal totalAmount;
    private Transaction.TransactionType transactionType;
    private List<ProductTransactionDTO> products;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProductTransactionDTO {
        private Integer productId;
        private String productName;
        private String category;
        private String supplier;
        private Integer quantity;
        private BigDecimal priceAtTransaction;
        private BigDecimal totalPrice;
        private String positionShelves;
        private String positionRowAndColumn;
        private String fullLocation;
        
        public String getFullLocation() {
            if (positionShelves != null && positionRowAndColumn != null) {
                return positionShelves + " - " + positionRowAndColumn;
            }
            return null;
        }
    }
} 