package edu.seattleu.elarson.moviedatabase;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;


public class MovieListActivity extends SingleFragmentActivity
        implements MovieListFragment.OnMovieListListener,
        MovieDetailFragment.OnMovieDetailListener,
        PickUrlDialogFragment.OnPickUrlDialogListener {

    private static final int INSERT_NEW_MOVIE = 0;
    private MovieDetailFragment mMovieDetailFragment;

    protected int getLayoutId() {
        return R.layout.activity_master;
    }

    @Override
    protected Fragment createFragment() {
        return MovieListFragment.newInstance();
    }


    // MARK: -MovieListFragment.OnMovieListListener interface functions

    // Start MovieDetailActivity when a new movie is added; if a two-pane interface
    // is being used, set up the MovieDetailActivity in the second pane
    public void onMovieInsert() {
        if (findViewById(R.id.detailFragmentContainer) != null) {
            startFragment(-1);
        } else {
            Intent intent = new Intent();

            intent.setClass(this, MovieDetailActivity.class);
            startActivityForResult(intent, INSERT_NEW_MOVIE);
        }
    }

    // Start MovieDetailActivity when a movie is edited; if a two-pane interface is
    // being used, set up the MovieDetailActivity in the second pane
    public void onMovieEdit(long id) {
        if (findViewById(R.id.detailFragmentContainer) != null) {
            startFragment(id);
        } else {
            Intent intent = new Intent();

            intent.putExtra(MovieDetailFragment.EXTRA_ID, id);
            intent.setClass(this, MovieDetailActivity.class);
            startActivityForResult(intent, INSERT_NEW_MOVIE);
        }
    }


    // MARK: -MovieDetailFragment.OnMovieDetailListener interface functions

    // Update the UI from MovieListFragment when the save button is pressed
    public void onMovieUpdate() {
        FragmentManager fm = getFragmentManager();
        MovieListFragment movieListFragment = (MovieListFragment)
                fm.findFragmentById(R.id.fragmentContainer);
        movieListFragment.updateUI();
    }


    // MARK: -Private helper functions

    // Use a FragmentTransaction to remove the current fragment in the second pane (if
    // one exists) and add the new second pane fragment
    private void startFragment(long id) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment =
                fm.findFragmentById(R.id.detailFragmentContainer);
        mMovieDetailFragment = MovieDetailFragment.newInstance(id);
        if (oldFragment != null) ft.remove(oldFragment);
        ft.add(R.id.detailFragmentContainer, mMovieDetailFragment);
        ft.commit();
    }

    // MARK: -MovieDetailFragment.OnPickUrlListener interface method

    // Set the url EditText to the passed-in url (from the pick url alert dialog)
    public void selectUrl(String url) {
        mMovieDetailFragment.selectUrl(url);
    }
}
