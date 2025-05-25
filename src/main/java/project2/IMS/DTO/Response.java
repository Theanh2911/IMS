package project2.IMS.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project2.IMS.Entity.Product;
import project2.IMS.Entity.Supplier;
import project2.IMS.Entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    // Login Response
    private String name;
    private String username;
    private String role;
    private String workingShift;
    private int userId;
    private String token;

    private List<UserDTO> users;

    // Add Product Response
    private List<Product> products;
    private String productName;
    private BigDecimal productPrice;
    private int productQuantity;
    private BigDecimal categoryId;

    private List<Supplier> suppliers;
    
    // Transaction Response
    private List<Transaction> transactions;

    private String message;
    private Object data;
    private String error;
    private Integer status;
} 