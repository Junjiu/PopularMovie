package wenjunjie.popularmoive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.zip.Inflater;

import wenjunjie.popularmoive.Utility.UrlBuilder;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterHolder> {

    MovieAdapterOnClick mMovieAdapterOnclick;
    Picasso picasso;
    public MovieAdapter(MovieAdapterOnClick parent){
       picasso = Picasso.get();
       mMovieAdapterOnclick = parent;
       //picasso.setLoggingEnabled(true);
    }
    private JSONObject[] moviesData;
    @Override
    public MovieAdapter.MovieAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int movieItemId = R.layout.movie;
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(movieItemId ,parent ,shouldAttachToParentImmediately);
        return new MovieAdapterHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieAdapterHolder holder, int position) {
        String url = UrlBuilder.getImageUrl(moviesData[position]);
        //"http://image.tmdb.org/t/p/w185/nAU74GmpUk7t5iklEp3bufwDq4n.jpg"
        //"http://image.tmdb.org/t/p/w185/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg"
        picasso
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        if(moviesData == null) return 0;
        return moviesData.length;
    }

    public void changeData(JSONObject[] newData){
        moviesData = newData;
        notifyDataSetChanged();
    }
    class MovieAdapterHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        ImageView poster;
        public MovieAdapterHolder(View v){
            super(v);
            poster = v.findViewById(R.id.post_iv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("Adapter","Get clicked" + getAdapterPosition());
            int position = getAdapterPosition();
            mMovieAdapterOnclick.onClick(moviesData[position]);
        }
    }
    public interface MovieAdapterOnClick{
        void onClick(JSONObject oneMovieData);
    }
}
