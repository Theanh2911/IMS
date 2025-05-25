package project2.IMS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.IMS.DTO.Response;
import project2.IMS.Request.ImportProductRequest;
import project2.IMS.Request.ExportProductRequest;
import project2.IMS.Service.Impl.TransactionService;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/import")
    public ResponseEntity<Response> importProduct(@RequestBody ImportProductRequest importProductRequest) {
        Response response = transactionService.importProduct(importProductRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/export")
    public ResponseEntity<Response> exportProduct(@RequestBody ExportProductRequest exportProductRequest) {
        Response response = transactionService.exportProduct(exportProductRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllTransactions() {
        Response response = transactionService.getAllTransactions();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getTransactionById(@PathVariable Integer id) {
        Response response = transactionService.getTransactionById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
} 