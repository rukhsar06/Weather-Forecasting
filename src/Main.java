// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import org.json.simple.JSONArray ;
import org.json.simple.JSONObject ;
import org.json.simple.parser.JSONParser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection ;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL ;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;
import java.util.Scanner ;
import org.json.simple.parser.ParseException;




public class Main {
    public static void main(String[] args) throws MalformedURLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("========================================");

        System.out.println("welcome to weather forcasting ");
        System.out.print("enter the city : ");
        String city = sc.nextLine().trim();


        // now lets access the json object
        // json stands for javascript object notation and it is a liberary that is extended from js
        // so now we have learnt how we can get the http url of the api
        // now we just need to structure the code  and fetch the data from using get method

        // this is our real api key

        String api = "f732f92cdf065306f63bc1b76a871110";

        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+api+"&units=metric" ;


        try {
            // create the url object
            URL url = new URL(urlString) ;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // get response
            int response = connection.getResponseCode();
            System.out.println("Response code : "+response);

            if(response==HttpURLConnection.HTTP_OK) { // 200 means success

                // read the Json response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
                StringBuilder res= new StringBuilder() ;
                String inputLine ;
                while ((inputLine = in.readLine()) != null) {
                    res.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(res.toString());

                // Extract and display weather information
                JSONObject main = (JSONObject) jsonResponse.get("main");
                JSONArray weatherArray = (JSONArray) jsonResponse.get("weather");
                JSONObject weather = (JSONObject) weatherArray.get(0);

               System.out.println("City: " + city);
                System.out.println("Temperature: " + main.get("temp") + "°C");
                System.out.println("Weather: " + weather.get("description"));

            } else {
                System.out.println("Error: Unable to fetch weather data. Response code: " + response);
            }
            connection.disconnect();

        } catch (IOException e) {
            System.out.println("An error occurred while making the request: " + e.getMessage());

        } catch (ParseException e) {

            System.out.println("An error occurred while parsing the JSON response: " + e.getMessage());
        }



    }


}