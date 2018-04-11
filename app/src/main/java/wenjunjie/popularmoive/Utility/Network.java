package wenjunjie.popularmoive.Utility;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Network {
    public static String getDataFromInternet(String address){
        String result = "";
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(address);
            urlConnection = (HttpURLConnection)url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            while(scanner.hasNext()){
                result += scanner.next();
            }
        }catch (Exception e){
                e.printStackTrace();
                result = null;
        }finally {
            urlConnection.disconnect();
        }
        return result;
    }
}
