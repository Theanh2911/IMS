package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project2.IMS.Entity.ProductPosition;
import project2.IMS.Entity.ProductPositionId;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPositionRepository extends JpaRepository<ProductPosition, ProductPositionId> {
    
    List<ProductPosition> findByProductId(Integer productId);
    
    List<ProductPosition> findByPositionId(Integer positionId);
    
    Optional<ProductPosition> findByProductIdAndPositionId(Integer productId, Integer positionId);
    
    @Query("SELECT pp FROM ProductPosition pp JOIN pp.product p JOIN pp.position pos WHERE p.productName LIKE %:productName%")
    List<ProductPosition> findByProductNameContaining(@Param("productName") String productName);
    
    @Query("SELECT pp FROM ProductPosition pp JOIN pp.position pos WHERE pos.shelves = :shelves")
    List<ProductPosition> findByPositionShelves(@Param("shelves") String shelves);
    
    @Modifying
    @Transactional
    void deleteByProductId(Integer productId);
    
    @Modifying
    @Transactional
    void deleteByPositionId(Integer positionId);
} 