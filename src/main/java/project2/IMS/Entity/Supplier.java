package project2.IMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "supplier")
public class Supplier {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(nullable = false, length = 100)
        private String name;

        @Column(name = "phone_number", length = 20)
        private String phoneNumber;

}

