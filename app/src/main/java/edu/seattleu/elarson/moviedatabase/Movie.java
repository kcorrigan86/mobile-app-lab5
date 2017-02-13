package edu.seattleu.elarson.moviedatabase;

class Movie {

    private long mId;
    private String mTitle;
    private String mGenre;
    private String mUrl;
    private float mRating;
    private boolean mWatched;

    Movie() {
        mRating = 0.0f;
        mWatched = false;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    String getGenre() {
        return mGenre;
    }

    String getUrl() {
        return mUrl;
    }

    float getRating() {
        return mRating;
    }

    boolean isWatched() {
        return mWatched;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    void setGenre(String genre) {
        mGenre = genre;
    }

    void setUrl(String url) {
        mUrl = url;
    }

    void setRating(float rating) {
        mRating = rating;
    }

    void setWatched(boolean watched) {
        mWatched = watched;
    }
}
