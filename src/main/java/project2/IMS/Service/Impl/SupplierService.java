package project2.IMS.Service.Impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project2.IMS.DTO.Response;
import project2.IMS.Entity.Supplier;
import project2.IMS.Request.AddNewSupplierRequest;
import project2.IMS.exceptions.NotFoundException;

import java.util.List;

@Service
public class SupplierService implements project2.IMS.Service.SupplierService{

    private final project2.IMS.Repository.SupplierRepository supplierRepository;

    public SupplierService(project2.IMS.Repository.SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Response addNewSupplier(AddNewSupplierRequest addNewSupplierRequest) {
        Supplier supplier = new Supplier();
        supplier.setName(addNewSupplierRequest.getName());
        supplier.setPhoneNumber(addNewSupplierRequest.getPhoneNumber());
        supplierRepository.save(supplier);

        return Response.builder()
                .status(200)
                .message("Supplier created successfully")
                .build();
    }

    @Override
    public Response getAllSupplier() {
        List<Supplier> suppliers = supplierRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return Response.builder()
                .status(200)
                .message("success")
                .suppliers(suppliers)
                .build();
    }

    @Override
    public Response deleteSupplier(Integer id) {
        supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));
        supplierRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Supplier successfully deleted")
                .build();
    }
}

