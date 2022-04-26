package fordevs.reports.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputFlatFile {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
