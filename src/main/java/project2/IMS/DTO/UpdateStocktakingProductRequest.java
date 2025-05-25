package project2.IMS.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStocktakingProductRequest {
    private Integer currentQuantity;
    private Integer countedQuantity;
} 