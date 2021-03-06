package edu.seattleu.elarson.moviedatabase;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MovieDetailFragment extends Fragment {

    public static final String EXTRA_ID = "edu.seattleu.elarson.moviedatabase.id";

    private Movie mMovie;
    private MovieDatabaseHelper mHelper;
    private OnMovieDetailListener mListener;
    private EditText mUrlEditText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Id of movie to access.
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(long id) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    // Set up the movie database
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = 0;
        if (getArguments() != null) {
            id = getArguments().getLong(EXTRA_ID, -1);
        }

        mHelper = new MovieDatabaseHelper(getActivity());
        if (id == -1) {
            mMovie = mHelper.insertMovie();
        } else {
            mMovie = mHelper.getMovie(id);
        }
    }

    // Set up the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        TextView idTextView = (TextView) v.findViewById(R.id.idTextView);
        idTextView.setText(String.valueOf(mMovie.getId()));

        // Hook up the movie title EditText
        EditText movieEditText = (EditText) v.findViewById(R.id.titleEditText);
        movieEditText.setText(mMovie.getTitle());
        movieEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mMovie.setTitle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int before, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        // Hook up the movie genre EditText
        EditText genreEditText = (EditText) v.findViewById(R.id.genreEditText);
        genreEditText.setText(mMovie.getGenre());
        genreEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mMovie.setGenre(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int before, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        // Hook up the URL EditText
        mUrlEditText = (EditText) v.findViewById(R.id.urlEditText);
        mUrlEditText.setText(mMovie.getUrl());
        mUrlEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mMovie.setUrl(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int before, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        // Hook up the movie watched check box
        CheckBox watchedCheckBox = (CheckBox) v.findViewById(R.id.watchedCheckBox);
        watchedCheckBox.setChecked(mMovie.isWatched());
        watchedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMovie.setWatched(isChecked);
            }
        });

        // Hook up the movie rating bar
        RatingBar ratingRatingBar = (RatingBar) v.findViewById(R.id.ratingRatingBar);
        ratingRatingBar.setRating(mMovie.getRating());
        ratingRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mMovie.setRating(rating);
            }
        });

        // Hook up the view movie web site button
        Button viewWebButton = (Button) v.findViewById(R.id.viewWebButton);
        viewWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MovieWebActivity.class);
                intent.putExtra(MovieWebFragment.EXTRA_URL, mMovie.getUrl());
                startActivity(intent);
            }
        });

        // Hook up the save button
        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHelper.updateMovie(mMovie);
                Toast.makeText(getActivity(), "Update complete!", Toast.LENGTH_LONG).show();
                mListener.onMovieUpdate();
            }
        });

        // Hook up the pick URL button
        Button pickUrlButton = (Button) v.findViewById(R.id.pickUrlButton);
        pickUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Show the pick url fragment
                PickUrlDialogFragment pickUrlDialogFragment = new PickUrlDialogFragment();
                pickUrlDialogFragment.show(getFragmentManager(), "dialog");
            }
        });

        return v;
    }

    // Set up a listener for the activity that contains this fragment to allow an interaction
    // in this fragment to be communicated to the activity; this method is called as soon as
    // the fragment is associated with the activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieDetailListener) {
            mListener = (OnMovieDetailListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieDetailListener");
        }
    }

    // Done with the mListener; method called immediately prior to the fragment no longer being
    // associated with its activity
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // This interface must be implemented by activities that contain this fragment
    // to allow an interaction in this fragment to be communicated to the activity.
    public interface OnMovieDetailListener {
        void onMovieUpdate();
    }

    // Set the url EditText to the passed-in url (from the pick url alert dialog)
    public void selectUrl(String url) {
        mUrlEditText.setText(url);
    }
}
