package project2.IMS.DTO;

import lombok.Getter;
import lombok.Setter;
import project2.IMS.Entity.SessionStatus;

@Getter
@Setter
public class UpdateSessionRequest {
    private String sessionName;
    private String sessionNotes;
    private SessionStatus status;
}
