package kiborgov.net.popularmovies1;

import android.graphics.Bitmap;

/**
 * Created by Yana on 28.09.2015.
 */
public class MoviePoster {
    public String original_title = null;
    public String bmpPath = null;
    public String overview;
    public double vote_average;
    public String release_date;

    public MoviePoster (String original_title, String bmpPath, String overview, double vote_average, String release_date) {
        this.original_title = original_title;
        this.bmpPath = bmpPath;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }
}
