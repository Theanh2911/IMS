package project2.IMS.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.IMS.DTO.*;
import project2.IMS.Entity.User;
import project2.IMS.Service.Impl.StocktakingService;
import project2.IMS.Service.Impl.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocktaking")
public class StockTakingController {

    @Autowired
    private StocktakingService stocktakingService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllSessions")
    public ResponseEntity<Response> getAllSessions() {
        try {
            User user = userService.getCurrentLoggedInUser();
            List<StocktakingSessionDTO> sessions = stocktakingService.getAllSessionsByUser(user);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Sessions retrieved successfully")
                    .data(sessions)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error retrieving sessions: " + e.getMessage())
                    .build());
        }
    }

    @PostMapping("/createSession")
    public ResponseEntity<Response> createSession(@RequestBody CreateSessionRequest request) {
        try {
            User user = userService.getCurrentLoggedInUser();
            StocktakingSessionDTO session = stocktakingService.createSession(request, user);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Session created successfully")
                    .data(session)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error creating session: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<Response> getSession(@PathVariable Long sessionId) {
        try {
            User user = userService.getCurrentLoggedInUser();
            StocktakingSessionDTO session = stocktakingService.getSessionById(sessionId, user);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Session retrieved successfully")
                    .data(session)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error retrieving session: " + e.getMessage())
                    .build());
        }
    }

    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<Response> updateSession(
            @PathVariable Long sessionId,
            @RequestBody UpdateSessionRequest request) {
        try {
            User user = userService.getCurrentLoggedInUser();
            StocktakingSessionDTO session = stocktakingService.updateSession(sessionId, request, user);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message("Session updated successfully")
                    .data(session)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error updating session: " + e.getMessage())
                    .build());
        }
    }

    @PutMapping("/sessions/{sessionId}/products/{productId}")
    public ResponseEntity<Response> updateProductCount(
            @PathVariable Long sessionId,
            @PathVariable Long productId,
            @RequestBody UpdateProductCountRequest request) {
        try {
            StocktakingProductDTO product = stocktakingService.updateProductCount(sessionId, productId, request.getCountedQuantity());
            
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

    // Get active session for current user
    @GetMapping("/sessions/active")
    public ResponseEntity<Response> getActiveSession() {
        try {
            User user = userService.getCurrentLoggedInUser();
            StocktakingSessionDTO activeSession = stocktakingService.getActiveSession(user);
            
            return ResponseEntity.ok(Response.builder()
                    .status(200)
                    .message(activeSession != null ? "Active session retrieved successfully" : "No active session found")
                    .data(activeSession)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .status(400)
                    .message("Error retrieving active session: " + e.getMessage())
                    .build());
        }
    }
}