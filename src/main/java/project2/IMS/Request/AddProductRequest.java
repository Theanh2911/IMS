package project2.IMS.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
public class AddProductRequest {
    String productName;
    BigDecimal price;
    Integer quantity;
    String category;
    String supplier;

}
