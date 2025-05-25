package project2.IMS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.IMS.DTO.*;
import project2.IMS.Service.StocktakingProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocktaking-products")
public class StocktakingProductController {

    @Autowired
    private StocktakingProductService stocktakingProductService;

    @PostMapping
    public ResponseEntity<Response> createStocktakingProduct(@RequestBody CreateStocktakingProductRequest request) {
        try {
            StocktakingProductDTO product = stocktakingProductService.createStocktakingProduct(request);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Stocktaking product created successfully")
                    .data(product)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error creating stocktaking product: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<Response> getAllStocktakingProducts() {
        try {
            List<StocktakingProductDTO> products = stocktakingProductService.getAllStocktakingProducts();
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Stocktaking products retrieved successfully")
                    .data(products)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error retrieving stocktaking products: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getStocktakingProductById(@PathVariable Integer id) {
        try {
            StocktakingProductDTO product = stocktakingProductService.getStocktakingProductById(id);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Stocktaking product retrieved successfully")
                    .data(product)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error retrieving stocktaking product: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Response> getStocktakingProductsBySessionId(@PathVariable Long sessionId) {
        try {
            List<StocktakingProductDTO> products = stocktakingProductService.getStocktakingProductsBySessionId(sessionId);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Stocktaking products retrieved successfully")
                    .data(products)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error retrieving stocktaking products: " + e.getMessage())
                    .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateStocktakingProduct(
            @PathVariable Integer id,
            @RequestBody UpdateStocktakingProductRequest request) {
        try {
            StocktakingProductDTO product = stocktakingProductService.updateStocktakingProduct(id, request);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Stocktaking product updated successfully")
                    .data(product)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error updating stocktaking product: " + e.getMessage())
                    .build());
        }
    }

    @PutMapping("/{id}/count")
    public ResponseEntity<Response> updateProductCount(
            @PathVariable Integer id,
            @RequestBody UpdateProductCountRequest request) {
        try {
            StocktakingProductDTO product = stocktakingProductService.updateProductCount(id, request.getCountedQuantity());
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Product count updated successfully")
                    .data(product)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error updating product count: " + e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteStocktakingProduct(@PathVariable Integer id) {
        try {
            stocktakingProductService.deleteStocktakingProduct(id);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Stocktaking product deleted successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error deleting stocktaking product: " + e.getMessage())
                    .build());
        }
    }
} 