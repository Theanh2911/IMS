package project2.IMS.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String username;
    private String name;
    private String password;
    private String role;
    private String workingShift;
}
