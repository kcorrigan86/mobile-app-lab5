package edu.seattleu.elarson.moviedatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    private static final class Movies implements BaseColumns {
        private Movies() {}
        public static final String TABLE_NAME = "movies";
        public static final String TITLE = "title";
        public static final String GENRE = "genre";
        public static final String URL = "url";
        public static final String RATING = "rating";
        public static final String WATCHED = "watched";
    }

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creates a database with a single table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Movies.TABLE_NAME + " ("
                + Movies._ID + " integer primary key autoincrement, "
                + Movies.TITLE + " text, "
                + Movies.GENRE + " text, "
                + Movies.URL + " text, "
                + Movies.RATING + " real, "
                + Movies.WATCHED + " integer"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not used but required to implement (SQLiteOpenHelper)
    }

    // Inserts a blank new movie into the database, returns the movie with
    // the id number set.
    public Movie insertMovie() {
        Movie movie = new Movie();
        ContentValues row = getMovieRow(movie);
        SQLiteDatabase db = getWritableDatabase();

        // Insert the movie into the database and set its ID
        long id = db.insert(Movies.TABLE_NAME, null, row);
        movie.setId(id);

        return movie;
    }

    // Updates the movie in the database.
    public void updateMovie(Movie movie) {
        ContentValues row = getMovieRow(movie);
        String whereClause = Movies._ID + "=" + String.valueOf(movie.getId());

        getWritableDatabase().update(Movies.TABLE_NAME, row, whereClause, null);
    }

    // Returns a cursor that has a list for all movies.
    public MovieCursor queryMovies() {
        // Get a cursor with a list for all movies
        @SuppressLint("Recycle") Cursor cursor = getReadableDatabase().query(
                Movies.TABLE_NAME,      // table name
                null,                   // columns (all)
                null,                   // where (all rows)
                null,                   // whereArgs
                null,                   // group by
                null,                   // having
                Movies.TITLE + " asc",  // order by
                null);                  // limit

        // Create a new MovieCursor from the cursor and return it
        return new MovieCursor(cursor);
    }

    // Queries the database for the movie with the corresponding id.  Returns the movie
    // or null if the movie does not exist in the database.
    public Movie getMovie(long id) {
        // Find the cursor with the corresponding id.
        String whereClause = Movies._ID + "=" + String.valueOf(id);
        Cursor cursor = getReadableDatabase().query(
                Movies.TABLE_NAME,      // table name
                null,                   // all columns
                whereClause,            // look for a run ID
                null,                   // whereArgs
                null,                   // group by
                null,                   // having
                null,                   // order by
                "1");                   // limit 1 row

        // Create a new MovieCursor from the cursor and get the movie from it
        MovieCursor movieCursor = new MovieCursor(cursor);
        return movieCursor.getMovie();
    }

    public static class MovieCursor extends CursorWrapper {

        public MovieCursor(Cursor cursor) {
            super(cursor);
        }

        // Returns the movie at the current cursor location.  Returns null
        // if the cursor is before the first record or after the last record.
        public Movie getMovie() {
            if (isBeforeFirst() || isAfterLast()) return null;
            Movie movie = new Movie();
            movie.setId(getLong(getColumnIndex(Movies._ID)));
            movie.setTitle(getString(getColumnIndex(Movies.TITLE)));
            movie.setGenre(getString(getColumnIndex(Movies.GENRE)));
            movie.setUrl(getString(getColumnIndex(Movies.URL)));
            movie.setRating(getFloat(getColumnIndex(Movies.RATING)));
            movie.setWatched(getInt(getColumnIndex(Movies.WATCHED)) != 0);

            return movie;
        }
    }

    // ContentValues structure represents a row in the database.
    // The ContentValues returned is used as a parameter for inserting and updating rows.
    private ContentValues getMovieRow(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(Movies.TITLE, movie.getTitle());
        cv.put(Movies.GENRE, movie.getGenre());
        cv.put(Movies.URL, movie.getUrl());
        cv.put(Movies.RATING, movie.getRating());
        cv.put(Movies.WATCHED, movie.isWatched() ? 1 : 0);
        return cv;
    }
}
