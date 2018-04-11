package wenjunjie.popularmoive;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import wenjunjie.popularmoive.Utility.JSONHandler;
import wenjunjie.popularmoive.Utility.Network;
import wenjunjie.popularmoive.Utility.UrlBuilder;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClick {
    RecyclerView mRecyclerViewMovies;
    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerViewMovies = (RecyclerView)findViewById(R.id.recycler_poster);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        movieAdapter = new MovieAdapter(this);
        mRecyclerViewMovies.setAdapter(movieAdapter);
        mRecyclerViewMovies.setLayoutManager(manager);
        getData();
    }
    private void getData(){
        String url = UrlBuilder.getMoviesDataURL();
        new FetchMovieDataTask().execute(url);
    }

    @Override
    public void onClick(JSONObject oneMovieData) {
        Toast.makeText(this,"Be clicked!", Toast.LENGTH_SHORT).show();
        Intent intentStartDetailActivity = new Intent(this, DetailsActivity.class);
        String postUrl = UrlBuilder.getImageUrl(oneMovieData);
        String title = null;
        String information = null;
        try{
            title = oneMovieData.getString("title");
            information = oneMovieData.getString("overview");
        }catch (Exception e){
            e.printStackTrace();
        }
        intentStartDetailActivity.putExtra(DetailsActivity.POST_URL_KEY, postUrl );
        intentStartDetailActivity.putExtra(DetailsActivity.TITLE_KEY, title);
        intentStartDetailActivity.putExtra(DetailsActivity.INFORMATION_KEY,information);
        startActivity(intentStartDetailActivity);

    }

    class FetchMovieDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url = null;
            if(strings == null || strings.length == 0){
                Log.d("FetchMovieDataTask","url is null");
                return null;
            }else{
                url = strings[0];
            }
            return Network.getDataFromInternet(url);
        }

        @Override
        protected void onPostExecute(String s) {

            if(s == null){
                Log.d("FetchMovieDataTask", "result is null");
                return;
            }
            Log.v("FetchMovieDataTask", s);
            JSONObject[] movies = JSONHandler.handlerMoviesData(s);
            movieAdapter.changeData(movies);
        }
    }
}
