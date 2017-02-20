package edu.seattleu.elarson.moviedatabase;

// Holds data about a web page
class WebPage {

    private String mUrl;
    private String mTitle;

    // Initialize variables
    WebPage(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    String getUrl() {
        return mUrl;
    }

    String getTitle() {
        return mTitle;
    }
}
