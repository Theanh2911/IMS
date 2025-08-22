package project2.IMS.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project2.IMS.Entity.Position;
import project2.IMS.Entity.Product;
import project2.IMS.Entity.Supplier;
import project2.IMS.Entity.Transaction;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSearchDTO {
    private List<Position> positions;
    private List<Product> products;
    private List<Supplier> suppliers;
    private List<Transaction> transactions;
}
