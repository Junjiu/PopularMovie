package wenjunjie.popularmoive.Utility;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = r.readLine()) != null){
                builder.append(line).append('\n');
            }
            result = builder.toString();
        }catch (Exception e){
                e.printStackTrace();
                result = null;
        }finally {
            urlConnection.disconnect();
        }

        return result;
    }
}
