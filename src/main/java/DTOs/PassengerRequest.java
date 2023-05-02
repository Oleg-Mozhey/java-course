package DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@Builder
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerRequest {
    private int trips;
    private String name;
    private int airline;

    public String toJson(){
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("trips", trips);
        result.put("airline", airline);
        return result.toString();
    }

}