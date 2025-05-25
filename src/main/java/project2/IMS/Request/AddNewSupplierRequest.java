package project2.IMS.Request;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class AddNewSupplierRequest {
    private String name;
    private String phoneNumber;
}
