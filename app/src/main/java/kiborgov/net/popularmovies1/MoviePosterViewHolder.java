package kiborgov.net.popularmovies1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Yana on 28.09.2015.
 */
public class MoviePosterViewHolder {
    public static final int ITEM_LAYOUT_ID = R.layout.list_item_movie_poster;
    public View view;
    public int position;
    public TextView original_title;
    public ImageView img;

    public MoviePosterViewHolder (View view) {
        this.view = view;
        getViews();
    }

    public void getViews () {
        original_title = (TextView) view.findViewById(R.id.movie_poster_original_title);
        img = (ImageView) view.findViewById(R.id.movie_poster_image);
    }
}
