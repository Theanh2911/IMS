package project2.IMS.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project2.IMS.DTO.CreateStocktakingProductRequest;
import project2.IMS.DTO.StocktakingProductDTO;
import project2.IMS.DTO.UpdateStocktakingProductRequest;
import project2.IMS.Entity.Product;
import project2.IMS.Entity.StocktakingProduct;
import project2.IMS.Entity.StocktakingSession;
import project2.IMS.Repository.ProductRepository;
import project2.IMS.Repository.StocktakingProductRepository;
import project2.IMS.Repository.StocktakingSessionRepository;
import project2.IMS.Service.StocktakingProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StocktakingProductServiceImpl implements StocktakingProductService {

    @Autowired
    private StocktakingProductRepository stocktakingProductRepository;

    @Autowired
    private StocktakingSessionRepository stocktakingSessionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public StocktakingProductDTO createStocktakingProduct(CreateStocktakingProductRequest request) {
        // Get session and product entities
        StocktakingSession session = stocktakingSessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + request.getSessionId()));
        
        Product product = productRepository.findById(request.getProductId().intValue())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

        StocktakingProduct stocktakingProduct = new StocktakingProduct();
        stocktakingProduct.setSession(session);
        stocktakingProduct.setProduct(product);
        stocktakingProduct.setCurrentQuantity(request.getCurrentQuantity());
        stocktakingProduct.setCountedQuantity(request.getCountedQuantity());
        
        // Calculate discrepancy
        if (request.getCountedQuantity() != null && request.getCurrentQuantity() != null) {
            stocktakingProduct.setDiscrepancy(request.getCountedQuantity() - request.getCurrentQuantity());
        }
        
        stocktakingProduct.setCreatedAt(LocalDateTime.now());
        stocktakingProduct.setUpdatedAt(LocalDateTime.now());

        StocktakingProduct saved = stocktakingProductRepository.save(stocktakingProduct);
        return convertToDTO(saved);
    }

    @Override
    public List<StocktakingProductDTO> getAllStocktakingProducts() {
        List<StocktakingProduct> products = stocktakingProductRepository.findAll();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StocktakingProductDTO getStocktakingProductById(Integer id) {
        StocktakingProduct product = stocktakingProductRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("StocktakingProduct not found with id: " + id));
        return convertToDTO(product);
    }

    @Override
    public List<StocktakingProductDTO> getStocktakingProductsBySessionId(Long sessionId) {
        List<StocktakingProduct> products = stocktakingProductRepository.findBySessionId(sessionId);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StocktakingProductDTO updateStocktakingProduct(Integer id, UpdateStocktakingProductRequest request) {
        StocktakingProduct existingProduct = stocktakingProductRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("StocktakingProduct not found with id: " + id));

        // Update fields if provided
        if (request.getCurrentQuantity() != null) {
            existingProduct.setCurrentQuantity(request.getCurrentQuantity());
        }
        if (request.getCountedQuantity() != null) {
            existingProduct.setCountedQuantity(request.getCountedQuantity());
        }

        // Recalculate discrepancy
        if (existingProduct.getCountedQuantity() != null && existingProduct.getCurrentQuantity() != null) {
            existingProduct.setDiscrepancy(existingProduct.getCountedQuantity() - existingProduct.getCurrentQuantity());
        }

        existingProduct.setUpdatedAt(LocalDateTime.now());

        StocktakingProduct updated = stocktakingProductRepository.save(existingProduct);
        return convertToDTO(updated);
    }

    @Override
    @Transactional
    public StocktakingProductDTO updateProductCount(Integer id, Integer countedQuantity) {
        StocktakingProduct existingProduct = stocktakingProductRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("StocktakingProduct not found with id: " + id));

        existingProduct.setCountedQuantity(countedQuantity);
        
        // Recalculate discrepancy
        if (existingProduct.getCurrentQuantity() != null) {
            existingProduct.setDiscrepancy(countedQuantity - existingProduct.getCurrentQuantity());
        }

        existingProduct.setUpdatedAt(LocalDateTime.now());

        StocktakingProduct updated = stocktakingProductRepository.save(existingProduct);
        return convertToDTO(updated);
    }

    @Override
    @Transactional
    public void deleteStocktakingProduct(Integer id) {
        StocktakingProduct existingProduct = stocktakingProductRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("StocktakingProduct not found with id: " + id));
        
        stocktakingProductRepository.delete(existingProduct);
    }

    private StocktakingProductDTO convertToDTO(StocktakingProduct stocktakingProduct) {
        StocktakingProductDTO dto = new StocktakingProductDTO();
        dto.setId(stocktakingProduct.getId());
        
        if (stocktakingProduct.getProduct() != null) {
            dto.setProductId(stocktakingProduct.getProduct().getId().intValue());
            dto.setProductName(stocktakingProduct.getProduct().getProductName());
            dto.setCategory(stocktakingProduct.getProduct().getCategory());
            dto.setSupplier(stocktakingProduct.getProduct().getSupplier());
        }
        
        dto.setCurrentQuantity(stocktakingProduct.getCurrentQuantity());
        dto.setCountedQuantity(stocktakingProduct.getCountedQuantity());
        dto.setDiscrepancy(stocktakingProduct.getDiscrepancy());
        
        return dto;
    }
} 