package project2.IMS.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StocktakingProductDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private String category;
    private String supplier;
    private Integer currentQuantity;
    private Integer countedQuantity;
    private Integer discrepancy;
}
