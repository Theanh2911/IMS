package project2.IMS.Request;

import lombok.Data;

@Data
public class MoveProductRequest {
    private Integer productId;
    private Integer positionId;
    private Integer quantity;
} 