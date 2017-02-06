package edu.seattleu.elarson.moviedatabase;

import android.app.Fragment;
import android.content.Intent;


public class MovieListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MovieListFragment.newInstance();
    }

}
