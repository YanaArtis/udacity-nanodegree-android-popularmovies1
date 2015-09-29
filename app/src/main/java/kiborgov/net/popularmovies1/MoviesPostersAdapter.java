package kiborgov.net.popularmovies1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Yana on 28.09.2015.
 */
public class MoviesPostersAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MoviePoster> list;

    public MoviesPostersAdapter (Context context, ArrayList<MoviePoster> arr) {
        this.context = context;
        list = arr;
    }

    @Override
    public int getCount () {
        return (list == null) ? -1 : list.size();
    }

    @Override
    public Object getItem (int n) {
        return (list == null) ? null : list.get(n);
    }

    @Override
    public long getItemId (int n) {
        return n;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(MoviePosterViewHolder.ITEM_LAYOUT_ID, parent, false);
        }
        MoviePoster moviePoster = list.get(position);
        try {
            MoviePosterViewHolder vh = new MoviePosterViewHolder(convertView);
            vh.original_title.setText(moviePoster.original_title);
            Picasso.with(context)
                    .load(moviePoster.bmpPath)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(vh.img);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void clear () {
        list.clear();
        notifyDataSetChanged();
    }

    public void add (MoviePoster moviePoster) {
        list.add(moviePoster);
        notifyDataSetChanged();
    }
}
