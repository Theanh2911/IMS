package project2.IMS.Service;

import project2.IMS.Entity.Position;
import project2.IMS.Entity.ProductPosition;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    
    List<Position> getAllPositions();
    
    Optional<Position> getPositionById(Integer id);
    
    Position createPosition(Position position);
    
    Position updatePosition(Integer id, Position position);
    
    void deletePosition(Integer id);
    
    List<Position> searchPositions(String searchTerm);
    
    List<Position> getPositionsByShelves(String shelves);
    
    boolean isPositionExists(String shelves, String rowAndColumn);
    
    // Product-Position relationship methods
    List<ProductPosition> getProductsByPosition(Integer positionId);
    
    ProductPosition moveProductToPosition(Integer productId, Integer positionId, Integer quantity);
    
    void removeProductFromPosition(Integer productId, Integer positionId);
    
    List<ProductPosition> getPositionsByProduct(Integer productId);
} 