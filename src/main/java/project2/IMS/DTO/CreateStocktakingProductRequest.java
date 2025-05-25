package project2.IMS.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStocktakingProductRequest {
    private Long sessionId;
    private Long productId;
    private Integer currentQuantity;
    private Integer countedQuantity;
} 