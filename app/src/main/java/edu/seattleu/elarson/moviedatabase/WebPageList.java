package edu.seattleu.elarson.moviedatabase;

import java.util.ArrayList;

// Singleton class--only allows for one instance of itself to be created, but
// permits global access using the get method
class WebPageList {
    private static WebPageList sWebPageList = null;
    private ArrayList<WebPage> mWebPages;

    // Create an ArrayList of URLs
    private WebPageList() {
        mWebPages = new ArrayList<>();
        mWebPages.add(new WebPage(
                "http://frozen.disney.com/",
                "Frozen | Official Disney Site"));
        mWebPages.add(new WebPage(
                "http://www.cnn.com/",
                "CNN - Breaking News, Latest News and Videos"));
        mWebPages.add(new WebPage(
                "http://www.imdb.com/title/tt3783958/",
                "La La Land (2016) - IMDb"));
        mWebPages.add(new WebPage(
                "http://www.imdb.com/title/tt0068646/",
                "The Godfather (1972) - IMDb"));
        mWebPages.add(new WebPage(
                "http://www.starwars.com/",
                "StarWars.com | The Official Star Wars Website"));
        mWebPages.add(new WebPage(
                "http://www.imdb.com/title/tt0059742/",
                "The Sound of Music (1965) - IMDb"));
        mWebPages.add(new WebPage(
                "http://www.imdb.com/title/tt0088763/",
                "Back to the Future (1985) - IMDb"));
        mWebPages.add(new WebPage(
                "http://www.imdb.com/title/tt0137523/",
                "Fight Club (1999) - IMDb"));
        mWebPages.add(new WebPage(
                "http://www.imdb.com/title/tt0081505/",
                "The Shining (1980) - IMDb"));
    }

    // Allow lazy instantiation of a WebPageList - object is not created until get() is called
    static WebPageList get() {
        if (sWebPageList == null) {
            sWebPageList = new WebPageList();
        }
        return sWebPageList;
    }

    // Retrieve the ArrayList of WebPages (access using WebPageList.get().getMovies())
    ArrayList<WebPage> getWebPages() {
        return mWebPages;
    }

    // Retrieve an array holding just the titles in the WebPageList
    String[] getTitles() {
        String[] titles = new String[mWebPages.size()];
        for (int i = 0; i < mWebPages.size(); i++) {
            titles[i] = mWebPages.get(i).getTitle();
        }

        return titles;
    }
}
