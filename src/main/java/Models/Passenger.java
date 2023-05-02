package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Passenger {
    private int trips;
    private String name;
    private String id;
    private int airline;
}
