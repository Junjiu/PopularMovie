package wenjunjie.popularmoive.Utility;

import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public  class JSONHandler {
    public static JSONObject[] handlerMoviesData(String inputData){

        JSONObject[] result =  new JSONObject[20];
        try{
            JSONObject moviesData = new JSONObject(inputData);
            JSONArray moviesDetails = moviesData.getJSONArray("results");
            for(int i = 0; i < moviesDetails.length(); ++i){
                result[i] = (JSONObject)moviesDetails.get(i);
            }
        }catch (Exception e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}
