import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {


        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to currency converter");
        System.out.println("Use the 3-letter codes when working with currency fields");
        System.out.println("Currency converting FROM?");
        fromCode = sc.nextLine().toUpperCase();

        System.out.println("Currency converting TO?");
        toCode = sc.nextLine().toUpperCase();

        System.out.println("Amount to convert?");
        amount = sc.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);
    }
    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        String GET_URL = "https://api.exchangerate.host/latest?base="+fromCode+"&symbols="+toCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }in.close();

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(amount+fromCode+" = "+amount*exchangeRate+toCode);
        }
        else {
            System.out.println("GET request failed!");
        }
    }
}
