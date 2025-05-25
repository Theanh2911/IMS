package project2.IMS.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionDTO {
    private Integer id;
    private String shelves;
    private String rowAndColumn;
    private String fullLocation; // Combined shelves + rowAndColumn for display
    
    public String getFullLocation() {
        if (shelves != null && rowAndColumn != null) {
            return shelves + " - " + rowAndColumn;
        }
        return null;
    }
} 