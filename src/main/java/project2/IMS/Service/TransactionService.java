package project2.IMS.Service;

import project2.IMS.DTO.Response;
import project2.IMS.Request.ImportProductRequest;
import project2.IMS.Request.ExportProductRequest;

public interface TransactionService {
    Response importProduct(ImportProductRequest importProductRequest);
    Response exportProduct(ExportProductRequest exportProductRequest);
    Response getAllTransactions();
    Response getTransactionById(Integer id);
} 