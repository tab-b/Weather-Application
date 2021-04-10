import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;

public class WeatherApp {

    public static void main(String[] args) {
        System.out.println("Hello there! Type in a location (all lowercase please) to get all weather-related information about the place!");

        Scanner scanner = new Scanner(System.in);
        String location = scanner.nextLine();
        String realLocation = location.replace(" ", "%20");
        System.out.println(location.toUpperCase() + ":");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.openweathermap.org/data/2.5/weather?appid=e4714b95no7c32316b0108c7&q=" + realLocation)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(WeatherApp::parse)
                .join();
        scanner.close();
    }

    public static String parse(String responseBody) {
        JSONObject wholeAPI = new JSONObject(responseBody);
        JSONObject weatherInfo = wholeAPI.getJSONObject("main");
        JSONObject windInfo = wholeAPI.getJSONObject("wind");

        double kelvinTemp = weatherInfo.getDouble("temp");
        double trueTemp = (kelvinTemp - 273.15) * 1.8 + 32;  // use this temperature value (in Fahrenheit)

        double feelsLikeTemp = weatherInfo.getDouble("feels_like");
        double trueFeelsTemp = (feelsLikeTemp - 273.15) * 1.8 + 32; // use this temperature value (in Fahrenheit)

        double speedOfWind = windInfo.getDouble("speed") * 2.237;

        

        System.out.println("Temperature: " + trueTemp + "\n" + "Feels like: " + trueFeelsTemp + "\n" + "Wind speed: " + speedOfWind + " mph");
        if(trueTemp < 50) {
            System.out.println("Seems like it's a bit too cold outside. Maybe try going outside a little later or the next day.");
        }
        else {
            System.out.println("Lovely day to go outside! Why don't you go have a nice walk?");
        }
        return null;
    }
    
}
