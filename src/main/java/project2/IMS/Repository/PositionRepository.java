package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project2.IMS.Entity.Position;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
    
    @Query("SELECT p FROM Position p WHERE p.shelves = :shelves AND p.rowAndColumn = :rowAndColumn")
    Optional<Position> findByShelvesAndRowAndColumn(@Param("shelves") String shelves, @Param("rowAndColumn") String rowAndColumn);
    
    List<Position> findByShelves(String shelves);
    
    @Query("SELECT p FROM Position p WHERE p.shelves LIKE %:shelves% OR p.rowAndColumn LIKE %:rowAndColumn%")
    List<Position> findByShelvesContainingOrRowAndColumnContaining(@Param("shelves") String shelves, @Param("rowAndColumn") String rowAndColumn);
    
    @Query("SELECT COUNT(p) > 0 FROM Position p WHERE p.shelves = :shelves AND p.rowAndColumn = :rowAndColumn")
    boolean existsByShelvesAndRowAndColumn(@Param("shelves") String shelves, @Param("rowAndColumn") String rowAndColumn);
    
    @Query("SELECT p FROM Position p WHERE " +
           "LOWER(p.shelves) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.rowAndColumn) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Position> searchPositions(@Param("searchTerm") String searchTerm);
} 