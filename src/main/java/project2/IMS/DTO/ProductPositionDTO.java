package project2.IMS.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPositionDTO {
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer totalProductQuantity; // Total quantity of product in inventory
    private String category;
    private String supplier;
    private Integer positionId;
    private String shelves;
    private String rowAndColumn;
    private String fullLocation;
    private Integer quantityAtPosition; // Quantity at this specific position
    
    public String getFullLocation() {
        if (shelves != null && rowAndColumn != null) {
            return shelves + " - " + rowAndColumn;
        }
        return null;
    }
} 