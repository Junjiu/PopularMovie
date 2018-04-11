package wenjunjie.popularmoive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import wenjunjie.popularmoive.Utility.Favorite;

public class DetailsActivity extends AppCompatActivity {

    public static final String POST_URL_KEY = "POST_URL_KEY";
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String PLOT_KEY = "INFORMATION_KEY";
    public static final String RELEASE_KEY = "RELEASE_KEY";
    public static final String VOTE_KEY = "VOTE_KEY";
    public static final String JSON_KEY = "JSON_KEY";
    ImageView post;
    TextView titleTv;
    TextView plotTv;
    TextView releaseDataTV;
    TextView voteTv;
    Button addFavoriteBt;
    JSONObject JSONmovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        post = (ImageView)findViewById(R.id.detail_post_iv);
        titleTv = (TextView)findViewById(R.id.detail_title_tv);
        plotTv = (TextView)findViewById(R.id.plot_synopsis);
        releaseDataTV = (TextView)findViewById(R.id.release_data);
        voteTv = (TextView)findViewById(R.id.vote_average);
        addFavoriteBt = (Button)findViewById(R.id.addFavorite);
        Intent intent = getIntent();
        if(intent.hasExtra(POST_URL_KEY)){
            String postUrl = intent.getStringExtra(POST_URL_KEY);
            Picasso.get().load(postUrl).into(post);
        }
        if(intent.hasExtra(TITLE_KEY)){
            String title = intent.getStringExtra(TITLE_KEY);
            titleTv.setText(title);
        }
        if(intent.hasExtra(RELEASE_KEY)){
            String releaseDate = intent.getStringExtra(RELEASE_KEY);
            releaseDataTV.setText(releaseDate);
        }
        if(intent.hasExtra(VOTE_KEY)){
            String vote = intent.getStringExtra(VOTE_KEY);
            voteTv.setText(vote);
        }
        if(intent.hasExtra(PLOT_KEY)){
            String information = intent.getStringExtra(PLOT_KEY);
            plotTv.setText(information);
        }
        if(intent.hasExtra(JSON_KEY)){
            String movieJSONString = intent.getStringExtra(JSON_KEY);
            try{
                JSONmovie = new JSONObject(movieJSONString);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        addFavoriteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favorite.favorite.add(JSONmovie);
            }
        });
    }
}
