package project2.IMS.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project2.IMS.Entity.Position;
import project2.IMS.Entity.Product;
import project2.IMS.Entity.ProductPosition;
import project2.IMS.Entity.ProductPositionId;
import project2.IMS.Repository.PositionRepository;
import project2.IMS.Repository.ProductPositionRepository;
import project2.IMS.Repository.ProductRepository;
import project2.IMS.Service.PositionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PositionServiceImpl implements PositionService {
    
    private final PositionRepository positionRepository;
    private final ProductPositionRepository productPositionRepository;
    private final ProductRepository productRepository;
    
    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
    
    @Override
    public Optional<Position> getPositionById(Integer id) {
        return positionRepository.findById(id);
    }
    
    @Override
    public Position createPosition(Position position) {
        if (isPositionExists(position.getShelves(), position.getRowAndColumn())) {
            throw new RuntimeException("Position already exists at shelves: " + position.getShelves() + 
                                     ", row and column: " + position.getRowAndColumn());
        }
        return positionRepository.save(position);
    }
    
    @Override
    public Position updatePosition(Integer id, Position position) {
        Position existingPosition = positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Position not found with id: " + id));
        
        // Check if the new position location already exists (excluding current position)
        Optional<Position> duplicatePosition = positionRepository.findByShelvesAndRowAndColumn(
                position.getShelves(), position.getRowAndColumn());
        
        if (duplicatePosition.isPresent() && !duplicatePosition.get().getId().equals(id)) {
            throw new RuntimeException("Position already exists at shelves: " + position.getShelves() + 
                                     ", row and column: " + position.getRowAndColumn());
        }
        
        existingPosition.setShelves(position.getShelves());
        existingPosition.setRowAndColumn(position.getRowAndColumn());
        
        return positionRepository.save(existingPosition);
    }
    
    @Override
    public void deletePosition(Integer id) {
        if (!positionRepository.existsById(id)) {
            throw new RuntimeException("Position not found with id: " + id);
        }
        
        // Remove all product-position relationships first
        productPositionRepository.deleteByPositionId(id);
        
        // Then delete the position
        positionRepository.deleteById(id);
    }
    
    @Override
    public List<Position> searchPositions(String searchTerm) {
        return positionRepository.findByShelvesContainingOrRowAndColumnContaining(searchTerm, searchTerm);
    }
    
    @Override
    public List<Position> getPositionsByShelves(String shelves) {
        return positionRepository.findByShelves(shelves);
    }
    
    @Override
    public boolean isPositionExists(String shelves, String rowAndColumn) {
        return positionRepository.existsByShelvesAndRowAndColumn(shelves, rowAndColumn);
    }
    
    @Override
    public List<ProductPosition> getProductsByPosition(Integer positionId) {
        return productPositionRepository.findByPositionId(positionId);
    }
    
    @Override
    public ProductPosition moveProductToPosition(Integer productId, Integer positionId, Integer quantity) {
        // Validate product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        // Validate position exists
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found with id: " + positionId));
        
        // Check if product-position relationship already exists
        Optional<ProductPosition> existingRelation = productPositionRepository
                .findByProductIdAndPositionId(productId, positionId);
        
        if (existingRelation.isPresent()) {
            // Update existing relationship
            ProductPosition productPosition = existingRelation.get();
            productPosition.setQuantityAtPosition(quantity);
            return productPositionRepository.save(productPosition);
        } else {
            // Create new relationship
            ProductPosition productPosition = new ProductPosition();
            productPosition.setProductId(productId);
            productPosition.setPositionId(positionId);
            productPosition.setQuantityAtPosition(quantity);
            productPosition.setProduct(product);
            productPosition.setPosition(position);
            
            // Update product's position_id
            product.setPositionId(positionId);
            productRepository.save(product);
            
            return productPositionRepository.save(productPosition);
        }
    }
    
    @Override
    public void removeProductFromPosition(Integer productId, Integer positionId) {
        ProductPositionId id = new ProductPositionId(productId, positionId);
        if (!productPositionRepository.existsById(id)) {
            throw new RuntimeException("Product-Position relationship not found");
        }
        
        // Remove the relationship
        productPositionRepository.deleteById(id);
        
        // Update product's position_id to null if this was the main position
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && positionId.equals(product.getPositionId())) {
            product.setPositionId(null);
            productRepository.save(product);
        }
    }
    
    @Override
    public List<ProductPosition> getPositionsByProduct(Integer productId) {
        return productPositionRepository.findByProductId(productId);
    }
} 