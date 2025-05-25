package project2.IMS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.IMS.DTO.Response;
import project2.IMS.Request.AddNewSupplierRequest;
import project2.IMS.Request.AddProductRequest;
import project2.IMS.Request.UpdateProductRequest;
import project2.IMS.Service.Impl.ProductService;
import project2.IMS.Service.Impl.SupplierService;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<Response> addNewSupplier(@RequestBody AddNewSupplierRequest addNewSupplierRequest) {
        Response response = supplierService.addNewSupplier(addNewSupplierRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllProducts() {
        Response response = supplierService.getAllSupplier();
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteSupplier(@PathVariable Integer id) {
        Response response = supplierService.deleteSupplier(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}
