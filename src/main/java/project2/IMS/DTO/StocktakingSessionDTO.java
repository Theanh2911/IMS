package project2.IMS.DTO;

import lombok.Getter;
import lombok.Setter;
import project2.IMS.Entity.SessionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StocktakingSessionDTO {
    private Long id;
    private String sessionName;
    private LocalDateTime sessionDate;
    private String sessionNotes;
    private SessionStatus status;
    private List<StocktakingProductDTO> products;
}
