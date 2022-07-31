package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    // 7b04d3a1a8e087c6b6896a73a0f8678f
    public static String getWeather(String message, Model model) throws IOException {

        URL url=new URL("https://api.openweathermap.org/data/2.5/weather?q="+message+"&units=metric&appid=7b04d3a1a8e087c6b6896a73a0f8678f");
        Scanner scanner=new Scanner((InputStream) url.getContent());
        String res="";
        while(scanner.hasNext())
        {
            res+=scanner.nextLine();
        }

        JSONObject object = new JSONObject(res);

        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");

        model.setTemp(main.getDouble("temp"));

        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");

        for (int i = 0; i < getArray.length(); i++) {

            JSONObject obj = getArray.getJSONObject(i);

            model.setIcon((String) obj.get("icon"));

            model.setMain((String) obj.get("main"));

        }

        return "City: " + model.getName() + "\n" +

                "Temperature: " + model.getTemp() + "C" + "\n" +

                "Humidity:" + model.getHumidity() + "%" + "\n" +

                "Main: " + model.getMain() + "\n" +

                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";

    }
    }
