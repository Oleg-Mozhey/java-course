package DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Passenger {
    @JsonProperty("_id")
    private String id;
    private List<AirlineItem> airline;
    private int trips;
    private String name;
    private int __v;
}