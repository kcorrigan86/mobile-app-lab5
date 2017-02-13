package edu.seattleu.elarson.moviedatabase;

import android.app.Fragment;
import android.content.Intent;


public class MovieDetailActivity extends SingleFragmentActivity
        implements MovieDetailFragment.OnMovieDetailListener {

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(MovieDetailFragment.EXTRA_ID, -1);
        return MovieDetailFragment.newInstance(id);
    }


    // MARK: -MovieDetailFragment.OnMovieDetailListener interface functions

    // Required onMovieUpdate() method - empty method, because this class is required to
    // implement the OnMovieDetailListener interface, but doesn't really need to utilize it
    public void onMovieUpdate() {
    }
}