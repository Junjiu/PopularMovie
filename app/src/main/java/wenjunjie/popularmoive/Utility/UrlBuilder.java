package wenjunjie.popularmoive.Utility;

import android.net.Uri;
import android.util.Log;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

public  class UrlBuilder {
    private static final String APIKEY = "api_key";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String POPULAR_PATH ="popular";
    private static final String TOP_RATE_PATH = "top_rated";
    private static  String SORT_ORDER = POPULAR_PATH;



    private static final String BASE_POST_URL = "http://image.tmdb.org/t/p/";
    private static final String SIZE_92 = "w92";
    private static final String SIZE_185 = "w185";
    private static final String DEFAULT_SIZE = SIZE_185;


    private static final String apikey = "Change to your own api key";
    //TODO(1) Change the apikey by your api

    public static void sortByPopular(){
        SORT_ORDER = POPULAR_PATH;
    }

    public static void sortByRate(){
        SORT_ORDER = TOP_RATE_PATH;
    }
    public static String getMoviesDataURL(){
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(SORT_ORDER)
                .appendQueryParameter(APIKEY, apikey)
                .build();
        Log.d("url", uri.toString());
        return uri.toString();
    }
    public static String getImageUrl(JSONObject movieData){
        String ImagePath = null;
        Uri uri = null;
        try{
            ImagePath = movieData.getString("poster_path").substring(1);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(ImagePath != null){
            uri = Uri.parse(BASE_POST_URL).buildUpon()
                    .appendEncodedPath(DEFAULT_SIZE)
                    .appendEncodedPath(ImagePath)
                    .build();
        }
        if (uri == null ) return null;
        return uri.toString();

    }
}
