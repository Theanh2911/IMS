package project2.IMS.Request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private String supplier;
}
