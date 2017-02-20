package edu.seattleu.elarson.moviedatabase;

import android.app.Fragment;
import android.content.Intent;


public class MovieDetailActivity extends SingleFragmentActivity
        implements MovieDetailFragment.OnMovieDetailListener,
        PickUrlDialogFragment.OnPickUrlDialogListener {

    private MovieDetailFragment mMovieDetailFragment;

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(MovieDetailFragment.EXTRA_ID, -1);
        mMovieDetailFragment = MovieDetailFragment.newInstance(id);
        return mMovieDetailFragment;
    }


    // MARK: -MovieDetailFragment.OnMovieDetailListener interface functions

    // Required onMovieUpdate() method - empty method, because this class is required to
    // implement the OnMovieDetailListener interface, but doesn't really need to utilize it
    public void onMovieUpdate() {
    }

    // MARK: -MovieDetailFragment.OnPickUrlListener interface method

    // Set the url EditText to the passed-in url (from the pick url alert dialog)
    public void selectUrl(String url) {
        mMovieDetailFragment.selectUrl(url);
    }
}