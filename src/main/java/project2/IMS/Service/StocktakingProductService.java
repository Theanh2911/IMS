package project2.IMS.Service;

import project2.IMS.DTO.CreateStocktakingProductRequest;
import project2.IMS.DTO.StocktakingProductDTO;
import project2.IMS.DTO.UpdateStocktakingProductRequest;
import project2.IMS.Entity.StocktakingProduct;

import java.util.List;

public interface StocktakingProductService {
    
    /**
     * Create a new stocktaking product
     */
    StocktakingProductDTO createStocktakingProduct(CreateStocktakingProductRequest request);
    
    /**
     * Get all stocktaking products
     */
    List<StocktakingProductDTO> getAllStocktakingProducts();
    
    /**
     * Get stocktaking product by ID
     */
    StocktakingProductDTO getStocktakingProductById(Integer id);
    
    /**
     * Get stocktaking products by session ID
     */
    List<StocktakingProductDTO> getStocktakingProductsBySessionId(Long sessionId);
    
    /**
     * Update stocktaking product
     */
    StocktakingProductDTO updateStocktakingProduct(Integer id, UpdateStocktakingProductRequest request);
    
    /**
     * Update product counted quantity
     */
    StocktakingProductDTO updateProductCount(Integer id, Integer countedQuantity);
    
    /**
     * Delete stocktaking product
     */
    void deleteStocktakingProduct(Integer id);
} 