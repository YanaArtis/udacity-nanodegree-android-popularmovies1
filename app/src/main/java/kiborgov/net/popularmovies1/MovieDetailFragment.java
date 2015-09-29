package kiborgov.net.popularmovies1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String original_title = (intent.hasExtra(getString(R.string.EXTRA_ORIGINAL_TITLE)))
                    ? intent.getStringExtra(getString(R.string.EXTRA_ORIGINAL_TITLE)) : "";
            String image_url = (intent.hasExtra(getString(R.string.EXTRA_IMAGE_URL)))
                    ? intent.getStringExtra(getString(R.string.EXTRA_IMAGE_URL)) : "";
            String overview = (intent.hasExtra(getString(R.string.EXTRA_OVERVIEW)))
                    ? intent.getStringExtra(getString(R.string.EXTRA_OVERVIEW)) : "";
            String release_date = (intent.hasExtra(getString(R.string.EXTRA_RELEASE_DATE)))
                    ? intent.getStringExtra(getString(R.string.EXTRA_RELEASE_DATE)) : "";
            Double vote_average = (intent.hasExtra(getString(R.string.EXTRA_VOTE_AVERAGE)))
                    ? intent.getDoubleExtra(getString(R.string.EXTRA_VOTE_AVERAGE), 0) : 0;

            getActivity().setTitle(original_title);
            ((TextView) rootView.findViewById(R.id.movie_detail_overview)).setText(overview);
            ((TextView) rootView.findViewById(R.id.movie_detail_release_date)).setText(release_date);
            ((TextView) rootView.findViewById(R.id.movie_detail_vote_average)).setText(String.valueOf(vote_average));
            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_detail_image);
            Picasso.with(getActivity())
                    .load(image_url)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        }
        return rootView;
    }
}
