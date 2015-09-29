package kiborgov.net.popularmovies1;

/**
 * Created by Yana on 28.09.2015.
 */
public class Const {
    // http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

    public static final String URL_IMAGES = "http://image.tmdb.org/t/p/";
    public static final String[] IMAGE_SIZE = {"original", "w92", "w154", "w185", "w342", "w500", "w780"};
    public static final int DEFAULT_IMAGE_SIZE = 3;

    // http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=YOUR_API_KEY

    public static final String MOVIES_KEY_PARAM = "api_key";
    public static final String MOVIES_KEY_VALUE = "PLACE-HERE-YOUR-API-KEY";

    public static final String URL_MOVIES = "http://api.themoviedb.org/3/discover/movie";
    public static final String SORT_MOVIES_PARAM = "sort_by";
    public static final String SORT_MOVIES_VALUE_BY_POPULARITY = "popularity";
    public static final String SORT_MOVIES_VALUE_BY_VOTE_AVERAGE = "vote_average";
    public static final String SORT_MOVIES_VALUE_DESC = "desc";
    public static final String SORT_MOVIES_VALUE_ASC = "asc";
}
