package edu.seattleu.elarson.moviedatabase;

import android.app.Fragment;
import android.content.Intent;


public class MovieListActivity extends SingleFragmentActivity
        implements MovieListFragment.OnMovieListListener {

    private static final int INSERT_NEW_MOVIE = 0;

    @Override
    protected Fragment createFragment() {
        return MovieListFragment.newInstance();
    }

    /*
     * MovieListFragment.OnMovieListLisener functions
     */

    // Start MovieDetailActivity using an Intent when a new movie is added
    public void onMovieInsert() {
        Intent intent = new Intent();

        intent.setClass(this, MovieDetailActivity.class);
        startActivityForResult(intent, INSERT_NEW_MOVIE);
    }

    // Start MovieDetailActivity using an Intent when a movie is edited; pass movie id
    // as an intent extra
    public void onMovieEdit(long id) {
        Intent intent = new Intent();

        intent.putExtra(MovieDetailFragment.EXTRA_ID, id);
        intent.setClass(this, MovieDetailActivity.class);
        startActivityForResult(intent, INSERT_NEW_MOVIE);
    }

}
