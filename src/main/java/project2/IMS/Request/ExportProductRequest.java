package project2.IMS.Request;

import lombok.Data;

@Data
public class ExportProductRequest {
    private Integer productId;
    private Integer quantity;
    private String notes;
} 