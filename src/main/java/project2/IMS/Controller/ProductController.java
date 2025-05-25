package project2.IMS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.IMS.DTO.Response;
import project2.IMS.Request.AddProductRequest;
import project2.IMS.Request.UpdateProductRequest;
import project2.IMS.Service.Impl.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Response> addNewProduct(@RequestBody AddProductRequest addProductRequest) {
        Response response = productService.addNewProduct(addProductRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllProducts() {
        Response response = productService.getAllProduct();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(
            @PathVariable Integer id,
            @RequestBody UpdateProductRequest updateProductRequest
    ) {
        Response response = productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Integer id) {
        Response response = productService.deleteProduct(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
