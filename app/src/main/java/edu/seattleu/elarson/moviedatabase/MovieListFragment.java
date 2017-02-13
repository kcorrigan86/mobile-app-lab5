package edu.seattleu.elarson.moviedatabase;


import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static edu.seattleu.elarson.moviedatabase.MovieDatabaseHelper.MovieCursor;

public class MovieListFragment extends ListFragment {
    private OnMovieListListener mListener;
    private MovieCursor mCursor;

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        // NOTE: If you create your database table incorrectly or want a fresh one,
        // uncomment the following line (and don't use the Up button):
        // getActivity().deleteDatabase("movies.db");

        // Create a new MovieDatabaseHelper
        MovieDatabaseHelper movieDatabaseHelper = new MovieDatabaseHelper(this.getActivity());

        // Query the movies and obtain a cursor (store in mCursor).
        mCursor = movieDatabaseHelper.queryMovies();

        // Create a new MovieCursorAdapter using the cursor.
        MovieCursorAdapter movieCursorAdapter = new MovieCursorAdapter(this.getActivity(), mCursor);

        //Set the list adapter using the MovieCursorAdapter.
        setListAdapter(movieCursorAdapter);
    }

    // Set up a listener for the activity that contains this fragment to allow an interaction
    // in this fragment to be communicated to the activity; this method is called as soon as
    // the fragment is associated with the activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieListListener) {
            mListener = (OnMovieListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieListListener");
        }
    }

    // Done with the mListener; method called immediately prior to the fragment no longer being
    // associated with its activity
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Do the final cleanup of the fragment's state
    @Override
    public void onDestroy() {
        mCursor.close();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_list, menu);
    }

    // If the add movie menu item is selected, let the activity containing this fragment
    // know through the listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_add:
                if (mListener != null) {
                    mListener.onMovieInsert();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // If the movie edit item is selected, let the activity containing this fragment
    // know through the listener
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // The id argument will be the movie ID; CursorAdapter gives us this for free
        if (mListener != null) {
            mListener.onMovieEdit(id);
        }
    }

    // Update the UI when this fragment is resumed
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // Update the user interface
    public void updateUI() {
        //noinspection deprecation
        mCursor.requery();
        ((MovieCursorAdapter)getListAdapter()).notifyDataSetChanged();
    }

    // This interface must be implemented by activities that contain this fragment
    // to allow an interaction in this fragment to be communicated to the activity.
    public interface OnMovieListListener {
        void onMovieInsert();
        void onMovieEdit(long id);
    }

    // A CursorAdapter binds each record of the cursor to a single view control within
    // the layout resource
    private static class MovieCursorAdapter extends CursorAdapter {

        private final MovieCursor mMovieCursor;

        MovieCursorAdapter(Context context, MovieCursor cursor) {
            super(context, cursor, 0);
            mMovieCursor = cursor;
        }

        // Inflate the view
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Initialize the view
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the title of the movie
            Movie movie = mMovieCursor.getMovie();

            // Cast the passed-in view to a TextView
            TextView titleTextView = (TextView) view;

            // Set the text of the view to the movie title
            titleTextView.setText(movie.getTitle());
        }
    }
}
