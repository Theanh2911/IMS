package project2.IMS.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSessionRequest {
    private String sessionName;
    private String sessionNotes;
}
