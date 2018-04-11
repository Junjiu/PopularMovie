package wenjunjie.popularmoive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static final String POST_URL_KEY = "POST_URL_KEY";
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String INFORMATION_KEY = "INFORMATION_KEY";
    ImageView post;
    TextView titleTv;
    TextView informationTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        post = (ImageView)findViewById(R.id.detail_post_iv);
        titleTv = (TextView)findViewById(R.id.detail_title_tv);
        informationTv = (TextView)findViewById(R.id.detail_information_tv);
        Intent intent = getIntent();
        if(intent.hasExtra(POST_URL_KEY)){
            String postUrl = intent.getStringExtra(POST_URL_KEY);
            Picasso.get().load(postUrl).into(post);
        }
        if(intent.hasExtra(TITLE_KEY)){
            String title = intent.getStringExtra(TITLE_KEY);
            titleTv.setText(title);
        }
        if(intent.hasExtra(INFORMATION_KEY)){
            String information = intent.getStringExtra(INFORMATION_KEY);
            informationTv.setText(information);
        }
    }
}
