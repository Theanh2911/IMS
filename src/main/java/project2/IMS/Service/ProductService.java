package project2.IMS.Service;

import project2.IMS.DTO.Response;
import project2.IMS.Entity.User;
import project2.IMS.Request.*;

public interface ProductService {
    Response addNewProduct(AddProductRequest addProductRequest);
    Response getAllProduct();
    Response updateProduct(Integer id, UpdateProductRequest updateProductRequest);
    Response deleteProduct(Integer id);
}
