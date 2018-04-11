package wenjunjie.popularmoive;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wenjunjie.popularmoive.Utility.Favorite;
import wenjunjie.popularmoive.Utility.JSONHandler;
import wenjunjie.popularmoive.Utility.Network;
import wenjunjie.popularmoive.Utility.UrlBuilder;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClick {
    public List<JSONObject> favorites;
    RecyclerView mRecyclerViewMovies;
    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        favorites = new ArrayList<>();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortorder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.sortByPopular){
            UrlBuilder.sortByPopular();
            getData();
        }
        if(itemId == R.id.sortByRate){
            UrlBuilder.sortByRate();
            getData();
        }
        if(itemId == R.id.favorite){
            JSONObject[] favorite = new JSONObject[Favorite.favorite.size()];
            for(int i = 0; i < favorite.length; ++i){
                favorite[i] = Favorite.favorite.get(i);
            }
            movieAdapter.changeData(favorite);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(JSONObject oneMovieData) {
        Toast.makeText(this,"Be clicked!", Toast.LENGTH_SHORT).show();
        Intent intentStartDetailActivity = new Intent(this, DetailsActivity.class);
        String postUrl = UrlBuilder.getImageUrl(oneMovieData);
        String title = null;
        String information = null;
        String vote = null;
        String releaseDate = null;
        try{
            title = oneMovieData.getString("title");
            information = oneMovieData.getString("overview");
            vote = oneMovieData.getString("vote_average");
            releaseDate = oneMovieData.getString("release_date");
            Log.v("information",oneMovieData.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        intentStartDetailActivity.putExtra(DetailsActivity.POST_URL_KEY, postUrl);
        intentStartDetailActivity.putExtra(DetailsActivity.TITLE_KEY, title);
        intentStartDetailActivity.putExtra(DetailsActivity.PLOT_KEY,information);
        intentStartDetailActivity.putExtra(DetailsActivity.VOTE_KEY,vote);
        intentStartDetailActivity.putExtra(DetailsActivity.RELEASE_KEY, releaseDate);
        intentStartDetailActivity.putExtra(DetailsActivity.JSON_KEY, oneMovieData.toString());
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
