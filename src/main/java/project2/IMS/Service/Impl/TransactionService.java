package project2.IMS.Service.Impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project2.IMS.DTO.Response;
import project2.IMS.DTO.TransactionDetailDTO;
import project2.IMS.Entity.Product;
import project2.IMS.Entity.ProductTransaction;
import project2.IMS.Entity.Transaction;
import project2.IMS.Repository.ProductRepository;
import project2.IMS.Repository.ProductTransactionRepository;
import project2.IMS.Repository.TransactionRepository;
import project2.IMS.Request.ImportProductRequest;
import project2.IMS.Request.ExportProductRequest;
import project2.IMS.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService implements project2.IMS.Service.TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final ProductTransactionRepository productTransactionRepository;

    public TransactionService(TransactionRepository transactionRepository, 
                            ProductRepository productRepository,
                            ProductTransactionRepository productTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.productTransactionRepository = productTransactionRepository;
    }

    @Override
    @Transactional
    public Response importProduct(ImportProductRequest importProductRequest) {
        // Find the product
        Product product = productRepository.findById(importProductRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        // Update product quantity (add for import)
        product.setQuantity(product.getQuantity() + importProductRequest.getQuantity());
        productRepository.save(product);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(generateTransactionNumber());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(Transaction.TransactionType.IMPORT);
        transaction.setTotalAmount(product.getPrice().multiply(new BigDecimal(importProductRequest.getQuantity())));
        
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Log product transaction
        ProductTransaction productTransaction = new ProductTransaction();
        productTransaction.setProductId(product.getId());
        productTransaction.setTransactionId(savedTransaction.getId());
        productTransaction.setQuantity(importProductRequest.getQuantity());
        productTransaction.setPriceAtTransaction(product.getPrice());
        productTransactionRepository.save(productTransaction);

        return Response.builder()
                .status(200)
                .message("Product imported successfully. Quantity updated: " + product.getQuantity())
                .build();
    }

    @Override
    @Transactional
    public Response exportProduct(ExportProductRequest exportProductRequest) {
        // Find the product
        Product product = productRepository.findById(exportProductRequest.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        // Check if there's enough quantity
        if (product.getQuantity() < exportProductRequest.getQuantity()) {
            return Response.builder()
                    .status(400)
                    .message("Insufficient quantity. Available: " + product.getQuantity() + 
                            ", Requested: " + exportProductRequest.getQuantity())
                    .build();
        }

        product.setQuantity(product.getQuantity() - exportProductRequest.getQuantity());
        productRepository.save(product);

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(generateTransactionNumber());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(Transaction.TransactionType.EXPORT);
        transaction.setTotalAmount(product.getPrice().multiply(new BigDecimal(exportProductRequest.getQuantity())));
        
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Log product transaction
        ProductTransaction productTransaction = new ProductTransaction();
        productTransaction.setProductId(product.getId());
        productTransaction.setTransactionId(savedTransaction.getId());
        productTransaction.setQuantity(exportProductRequest.getQuantity());
        productTransaction.setPriceAtTransaction(product.getPrice());
        productTransactionRepository.save(productTransaction);

        return Response.builder()
                .status(200)
                .message("Product exported successfully. Remaining quantity: " + product.getQuantity())
                .build();
    }

    @Override
    public Response getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "transactionDate"));
        return Response.builder()
                .status(200)
                .message("Transactions retrieved successfully")
                .transactions(transactions)
                .build();
    }

    @Override
    public Response getTransactionById(Integer id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        
        // Get product transactions with product details
        List<ProductTransaction> productTransactions = productTransactionRepository
                .findByTransactionIdWithProductAndTransaction(id);
        
        // Convert to DTO with product details
        List<TransactionDetailDTO.ProductTransactionDTO> productDTOs = productTransactions.stream()
                .map(pt -> {
                    Product product = pt.getProduct();
                    TransactionDetailDTO.ProductTransactionDTO dto = TransactionDetailDTO.ProductTransactionDTO.builder()
                            .productId(product.getId())
                            .productName(product.getProductName())
                            .category(product.getCategory())
                            .supplier(product.getSupplier())
                            .quantity(pt.getQuantity())
                            .priceAtTransaction(pt.getPriceAtTransaction())
                            .totalPrice(pt.getPriceAtTransaction().multiply(new BigDecimal(pt.getQuantity())))
                            .build();
                    
                    // Add position information if available
                    if (product.getPosition() != null) {
                        dto.setPositionShelves(product.getPosition().getShelves());
                        dto.setPositionRowAndColumn(product.getPosition().getRowAndColumn());
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());
        
        TransactionDetailDTO transactionDetail = TransactionDetailDTO.builder()
                .id(transaction.getId())
                .transactionNumber(transaction.getTransactionNumber())
                .transactionDate(transaction.getTransactionDate())
                .totalAmount(transaction.getTotalAmount())
                .transactionType(transaction.getTransactionType())
                .products(productDTOs)
                .build();
        
        return Response.builder()
                .status(200)
                .message("Transaction retrieved successfully")
                .data(transactionDetail)
                .build();
    }

    private String generateTransactionNumber() {
        return "TXN-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 