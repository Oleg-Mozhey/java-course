package Utils;

import DTOs.PassengerRequest;
import Models.Passenger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PassengerService {
    public static Passenger createNewPassengerInDatabase(PassengerRequest newPassenger) throws IOException {
        URL url = new URL("https://api.instantwebtools.net/v1/passenger");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");
        String data = newPassenger.toJson();
        System.out.println("http body to create new user:" + data);
        byte[] out = data.getBytes(StandardCharsets.UTF_8);
        OutputStream stream = http.getOutputStream();
        stream.write(out);
        String responseBody = "";
        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(http.getInputStream()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    responseBody += line;
                }
            }
        }
        System.out.println("response message: " + responseBody);
        JSONObject passengerJson = new JSONObject(responseBody);
        int airline = passengerJson.getJSONArray("airline").getJSONObject(0).getInt("id");
        return new Passenger(airline, passengerJson.getString("name"), passengerJson.getString("_id"), passengerJson.getInt("trips"));
    }
}
