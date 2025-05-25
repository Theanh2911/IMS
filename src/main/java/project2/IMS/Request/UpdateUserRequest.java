package project2.IMS.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {
    private String name;
    private String username;
    private String password;
    private String WorkingShift;

    public UpdateUserRequest(String name, String username, String password, String workingShift) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.WorkingShift = workingShift;
    }

}
