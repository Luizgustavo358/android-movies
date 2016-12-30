package com.rafaelguimas.movies.model;

import java.util.List;

/**
 * Created by Rafael on 29/12/2016.
 */
public class MovieList {

    private List<Movie> Search;
    private String totalResults;
    private String Response;

    public MovieList(List<Movie> search, String totalResults, String response) {
        this.Search = search;
        this.totalResults = totalResults;
        this.Response = response;
    }

    public List<Movie> getSearch() {
        return Search;
    }

    public void setSearch(List<Movie> search) {
        this.Search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        this.Response = response;
    }
}
