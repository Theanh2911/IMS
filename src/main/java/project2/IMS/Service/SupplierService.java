package project2.IMS.Service;

import project2.IMS.DTO.Response;
import project2.IMS.Request.AddNewSupplierRequest;
import project2.IMS.Request.AddProductRequest;
import project2.IMS.Request.UpdateProductRequest;

public interface SupplierService {
    Response addNewSupplier(AddNewSupplierRequest addNewSupplierRequest);
    Response getAllSupplier();
    Response deleteSupplier(Integer id);
}
