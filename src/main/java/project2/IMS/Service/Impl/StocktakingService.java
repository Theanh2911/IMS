package project2.IMS.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project2.IMS.DTO.CreateSessionRequest;
import project2.IMS.DTO.StocktakingProductDTO;
import project2.IMS.DTO.StocktakingSessionDTO;
import project2.IMS.DTO.UpdateSessionRequest;
import project2.IMS.Entity.*;
import project2.IMS.Repository.ProductRepository;
import project2.IMS.Repository.StocktakingProductRepository;
import project2.IMS.Repository.StocktakingSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StocktakingService {

    @Autowired
    private StocktakingSessionRepository sessionRepository;

    @Autowired
    private StocktakingProductRepository productRepository;

    @Autowired
    private ProductRepository mainProductRepository;

    @Transactional
    public StocktakingSessionDTO createSession(CreateSessionRequest request, User user) {
        sessionRepository.updateActiveSessionsToCompleted(user.getId());

        List<Product> allProducts = mainProductRepository.findAll();

        StocktakingSession session = new StocktakingSession();
        session.setSessionName(request.getSessionName());
        session.setSessionNotes(request.getSessionNotes());
        session.setSessionDate(LocalDateTime.now());
        session.setStatus(SessionStatus.ACTIVE);
        session.setCreatedBy(user);

        session = sessionRepository.save(session);

        StocktakingSession finalSession = session;
        List<StocktakingProduct> stocktakingProducts = allProducts.stream()
                .map(product -> {
                    StocktakingProduct sp = new StocktakingProduct();
                    sp.setSession(finalSession);
                    sp.setProduct(product);
                    sp.setCurrentQuantity(product.getQuantity());
                    sp.setCreatedAt(LocalDateTime.now());
                    sp.setUpdatedAt(LocalDateTime.now());
                    return sp;
                })
                .collect(Collectors.toList());

        productRepository.saveAll(stocktakingProducts);
        session.setProducts(stocktakingProducts);

        return convertToDTO(session);
    }

    @Transactional
    public StocktakingProductDTO updateProductCount(Long sessionId, Long productId, Integer countedQuantity) {
        StocktakingProduct stocktakingProduct = productRepository
                .findBySessionIdAndProductId(sessionId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in session"));

        stocktakingProduct.setCountedQuantity(countedQuantity);
        stocktakingProduct.setDiscrepancy(countedQuantity - stocktakingProduct.getCurrentQuantity());
        stocktakingProduct.setUpdatedAt(LocalDateTime.now());

        stocktakingProduct = productRepository.save(stocktakingProduct);

        return convertToDTO(stocktakingProduct);
    }

    private StocktakingSessionDTO convertToDTO(StocktakingSession session) {
        StocktakingSessionDTO dto = new StocktakingSessionDTO();
        dto.setId(session.getId());
        dto.setSessionName(session.getSessionName());
        dto.setSessionDate(session.getSessionDate());
        dto.setSessionNotes(session.getSessionNotes());
        dto.setStatus(session.getStatus());

        if (session.getProducts() != null) {
            List<StocktakingProductDTO> productDTOs = session.getProducts().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            dto.setProducts(productDTOs);
        }

        return dto;
    }

    private StocktakingProductDTO convertToDTO(StocktakingProduct stocktakingProduct) {
        StocktakingProductDTO dto = new StocktakingProductDTO();
        dto.setId(stocktakingProduct.getId());
        dto.setProductId(stocktakingProduct.getProduct().getId());
        dto.setProductName(stocktakingProduct.getProduct().getProductName());
        dto.setCategory(stocktakingProduct.getProduct().getCategory());
        dto.setSupplier(stocktakingProduct.getProduct().getSupplier());
        dto.setCurrentQuantity(stocktakingProduct.getCurrentQuantity());
        dto.setCountedQuantity(stocktakingProduct.getCountedQuantity());
        dto.setDiscrepancy(stocktakingProduct.getDiscrepancy());
        return dto;
    }

    // Additional service methods
    public List<StocktakingSessionDTO> getAllSessionsByUser(User user) {
        List<StocktakingSession> sessions = sessionRepository
                .findByCreatedByIdOrderBySessionDateDesc(user.getId());
        return sessions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StocktakingSessionDTO getSessionById(Long sessionId, User user) {
        StocktakingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Check if user owns this session
        if (!session.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return convertToDTO(session);
    }

    public StocktakingSessionDTO getActiveSession(User user) {
        Optional<StocktakingSession> activeSession = sessionRepository
                .findByCreatedByIdAndStatus(user.getId(), SessionStatus.ACTIVE);

        return activeSession.map(this::convertToDTO).orElse(null);
    }

    @Transactional
    public StocktakingSessionDTO completeSession(Long sessionId, User user) {
        StocktakingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Check if user owns this session
        if (!session.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        session.setStatus(SessionStatus.COMPLETED);
        session = sessionRepository.save(session);

        return convertToDTO(session);
    }

    @Transactional
    public void deleteSession(Long sessionId, User user) {
        StocktakingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Check if user owns this session
        if (!session.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        sessionRepository.delete(session);
    }

    @Transactional
    public StocktakingSessionDTO updateSession(Long sessionId, UpdateSessionRequest request, User user) {
        StocktakingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Check if user owns this session
        if (!session.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        if (request.getSessionName() != null) {
            session.setSessionName(request.getSessionName());
        }
        if (request.getSessionNotes() != null) {
            session.setSessionNotes(request.getSessionNotes());
        }
        if (request.getStatus() != null) {
            session.setStatus(request.getStatus());
        }

        session = sessionRepository.save(session);
        return convertToDTO(session);
    }
}