package project2.IMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_position")
@IdClass(ProductPositionId.class)
public class ProductPosition {
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Id
    @Column(name = "position_id")
    private Integer positionId;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private Position position;

    @Column(name = "quantity_at_position")
    private Integer quantityAtPosition;
}