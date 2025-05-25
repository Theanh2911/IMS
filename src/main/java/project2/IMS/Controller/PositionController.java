package project2.IMS.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.IMS.DTO.PositionDTO;
import project2.IMS.DTO.ProductPositionDTO;
import project2.IMS.DTO.Response;
import project2.IMS.Entity.Position;
import project2.IMS.Entity.ProductPosition;
import project2.IMS.Request.CreatePositionRequest;
import project2.IMS.Request.MoveProductRequest;
import project2.IMS.Service.PositionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {
    
    private final PositionService positionService;
    
    @GetMapping
    public ResponseEntity<Response> getAllPositions() {
        try {
            List<Position> positions = positionService.getAllPositions();
            List<PositionDTO> positionDTOs = positions.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Positions retrieved successfully")
                    .data(positionDTOs)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .status(500)
                            .error("Failed to retrieve positions: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Response> getPositionById(@PathVariable Integer id) {
        try {
            Optional<Position> position = positionService.getPositionById(id);
            if (position.isPresent()) {
                PositionDTO positionDTO = convertToDTO(position.get());
                return ResponseEntity.ok(Response.builder()
                        .status(200)
                        .message("Position retrieved successfully")
                        .data(positionDTO)
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.builder()
                                .status(404)
                                .error("Position not found with id: " + id)
                                .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .status(500)
                            .error("Failed to retrieve position: " + e.getMessage())
                            .build());
        }
    }
    
    @PostMapping
    public ResponseEntity<Response> createPosition(@RequestBody CreatePositionRequest request) {
        try {
            Position position = new Position();
            position.setShelves(request.getShelves());
            position.setRowAndColumn(request.getRowAndColumn());
            
            Position createdPosition = positionService.createPosition(position);
            PositionDTO positionDTO = convertToDTO(createdPosition);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.builder()
                            .status(201)
                            .message("Position created successfully")
                            .data(positionDTO)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .status(400)
                            .error("Failed to create position: " + e.getMessage())
                            .build());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Response> updatePosition(@PathVariable Integer id, @RequestBody CreatePositionRequest request) {
        try {
            Position position = new Position();
            position.setShelves(request.getShelves());
            position.setRowAndColumn(request.getRowAndColumn());
            
            Position updatedPosition = positionService.updatePosition(id, position);
            PositionDTO positionDTO = convertToDTO(updatedPosition);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Position updated successfully")
                    .data(positionDTO)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .status(400)
                            .error("Failed to update position: " + e.getMessage())
                            .build());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletePosition(@PathVariable Integer id) {
        try {
            positionService.deletePosition(id);
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Position deleted successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .status(400)
                            .error("Failed to delete position: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<Response> searchPositions(@RequestParam String searchTerm) {
        try {
            List<Position> positions = positionService.searchPositions(searchTerm);
            List<PositionDTO> positionDTOs = positions.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Positions found")
                    .data(positionDTOs)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .status(500)
                            .error("Failed to search positions: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/shelves/{shelves}")
    public ResponseEntity<Response> getPositionsByShelves(@PathVariable String shelves) {
        try {
            List<Position> positions = positionService.getPositionsByShelves(shelves);
            List<PositionDTO> positionDTOs = positions.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Positions retrieved for shelves: " + shelves)
                    .data(positionDTOs)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .status(500)
                            .error("Failed to retrieve positions: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/{id}/products")
    public ResponseEntity<Response> getProductsByPosition(@PathVariable Integer id) {
        try {
            List<ProductPosition> productPositions = positionService.getProductsByPosition(id);
            List<ProductPositionDTO> productPositionDTOs = productPositions.stream()
                    .map(this::convertToProductPositionDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Products retrieved for position")
                    .data(productPositionDTOs)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .status(500)
                            .error("Failed to retrieve products: " + e.getMessage())
                            .build());
        }
    }
    
    @PostMapping("/move-product")
    public ResponseEntity<Response> moveProductToPosition(@RequestBody MoveProductRequest request) {
        try {
            ProductPosition productPosition = positionService.moveProductToPosition(
                    request.getProductId(), request.getPositionId(), request.getQuantity());
            ProductPositionDTO productPositionDTO = convertToProductPositionDTO(productPosition);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Product moved to position successfully")
                    .data(productPositionDTO)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .status(400)
                            .error("Failed to move product: " + e.getMessage())
                            .build());
        }
    }
    
    @DeleteMapping("/remove-product/{productId}/{positionId}")
    public ResponseEntity<Response> removeProductFromPosition(@PathVariable Integer productId, @PathVariable Integer positionId) {
        try {
            positionService.removeProductFromPosition(productId, positionId);
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Product removed from position successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .status(400)
                            .error("Failed to remove product: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/product/{productId}")
    public ResponseEntity<Response> getPositionsByProduct(@PathVariable Integer productId) {
        try {
            List<ProductPosition> productPositions = positionService.getPositionsByProduct(productId);
            List<ProductPositionDTO> productPositionDTOs = productPositions.stream()
                    .map(this::convertToProductPositionDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Positions retrieved for product")
                    .data(productPositionDTOs)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .status(500)
                            .error("Failed to retrieve positions: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping("/{id}/details")
    public ResponseEntity<Response> getPositionDetails(@PathVariable Integer id) {
        try {
            Optional<Position> position = positionService.getPositionById(id);
            if (position.isPresent()) {
                PositionDTO positionDTO = convertToDTO(position.get());
                List<ProductPosition> productPositions = positionService.getProductsByPosition(id);
                List<ProductPositionDTO> productPositionDTOs = productPositions.stream()
                        .map(this::convertToProductPositionDTO)
                        .collect(Collectors.toList());
                
                // Create a combined response
                Map<String, Object> combinedData = new HashMap<>();
                combinedData.put("position", positionDTO);
                combinedData.put("products", productPositionDTOs);
                combinedData.put("totalProducts", productPositionDTOs.size());
                combinedData.put("totalQuantity", productPositionDTOs.stream()
                        .mapToInt(pp -> pp.getQuantityAtPosition() != null ? pp.getQuantityAtPosition() : 0)
                        .sum());
                
                return ResponseEntity.ok(Response.builder()
                        .status(200)
                        .message("Position details retrieved successfully")
                        .data(combinedData)
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.builder()
                                .status(404)
                                .error("Position not found with id: " + id)
                                .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .status(500)
                            .error("Failed to retrieve position details: " + e.getMessage())
                            .build());
        }
    }
    
    private PositionDTO convertToDTO(Position position) {
        return PositionDTO.builder()
                .id(position.getId())
                .shelves(position.getShelves())
                .rowAndColumn(position.getRowAndColumn())
                .build();
    }
    
    private ProductPositionDTO convertToProductPositionDTO(ProductPosition productPosition) {
        return ProductPositionDTO.builder()
                .productId(productPosition.getProductId())
                .productName(productPosition.getProduct() != null ? productPosition.getProduct().getProductName() : null)
                .productPrice(productPosition.getProduct() != null ? productPosition.getProduct().getPrice() : null)
                .totalProductQuantity(productPosition.getProduct() != null ? productPosition.getProduct().getQuantity() : null)
                .category(productPosition.getProduct() != null ? productPosition.getProduct().getCategory() : null)
                .supplier(productPosition.getProduct() != null ? productPosition.getProduct().getSupplier() : null)
                .positionId(productPosition.getPositionId())
                .shelves(productPosition.getPosition() != null ? productPosition.getPosition().getShelves() : null)
                .rowAndColumn(productPosition.getPosition() != null ? productPosition.getPosition().getRowAndColumn() : null)
                .quantityAtPosition(productPosition.getQuantityAtPosition())
                .build();
    }
} 