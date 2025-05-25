package project2.IMS.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project2.IMS.DTO.Response;
import project2.IMS.Entity.Category;
import project2.IMS.Entity.Product;
import project2.IMS.Entity.Supplier;
import project2.IMS.Repository.CategoryRepository;
import project2.IMS.Repository.ProductRepository;
import project2.IMS.Repository.SupplierRepository;
import project2.IMS.Repository.UserRepository;
import project2.IMS.Request.AddProductRequest;
import project2.IMS.Request.UpdateProductRequest;
import project2.IMS.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements project2.IMS.Service.ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;

    @Override
    public Response addNewProduct(AddProductRequest addProductRequest) {

        Product product = new Product();
        product.setProductName(addProductRequest.getProductName().trim());
        product.setPrice(addProductRequest.getPrice());
        product.setQuantity(addProductRequest.getQuantity());
        product.setCategory(addProductRequest.getCategory());
        product.setSupplier(addProductRequest.getSupplier());

        productRepository.save(product);

        return Response.builder()
                .status(200)
                .message("Product created successfully")
                .build();
    }

    @Override
    public Response getAllProduct() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return Response.builder()
                .status(200)
                .message("success")
                .products(products)
                .build();
    }

    @Override
    public Response updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        if (updateProductRequest.getName() != null) existingProduct.setProductName(updateProductRequest.getName());
        if (updateProductRequest.getPrice() != null) existingProduct.setPrice(updateProductRequest.getPrice());
        if (updateProductRequest.getQuantity() != null) existingProduct.setQuantity(updateProductRequest.getQuantity());
        if (updateProductRequest.getCategory() != null) existingProduct.setCategory(updateProductRequest.getCategory());
        if (updateProductRequest.getSupplier() != null) existingProduct.setSupplier(updateProductRequest.getSupplier());

        productRepository.save(existingProduct);

        return Response.builder()
                .status(200)
                .message("Product successfully updated")
                .build();
    }

    @Override
    public Response deleteProduct(Integer id) {
        productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        productRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Product successfully deleted")
                .build();
    }
}