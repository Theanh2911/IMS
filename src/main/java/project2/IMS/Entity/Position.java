package project2.IMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shelves", length = 50)
    private String shelves;

    @Column(name = "row_and_column", length = 50)
    private String rowAndColumn;
}