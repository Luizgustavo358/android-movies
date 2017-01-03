package com.rafaelguimas.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 29/12/2016.
 */

public class Movie implements Parcelable{

    /* Exemplo de retorno da API

   "Title":"Pirates",
   "Year":"1986",
   "Rated":"PG-13",
   "Released":"18 Jul 1986",
   "Runtime":"121 min",
   "Genre":"Adventure, Comedy, Family",
   "Director":"Roman Polanski",
   "Writer":"John Brownjohn (screenplay), Gérard Brach, Roman Polanski",
   "Actors":"Walter Matthau, Cris Campion, Damien Thomas, Olu Jacobs",
   "Plot":"The adventures of pirate Captain Red and his first mate Frog.",
   "Language":"English, French, Spanish",
   "Country":"France, Tunisia",
   "Awards":"Nominated for 1 Oscar. Another 2 wins & 1 nomination.",
   "Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BNzBmNWNkYTAtYTNhZi00ZjAxLTkzNGQtODk1OTkwZDRmMTJmL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg",
   "Metascore":"N/A",
   "imdbRating":"6.1",
   "imdbVotes":"6,235",
   "imdbID":"tt0091757",
   "Type":"movie",
   "Response":"True"

   */

    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String Type;
    private String Response;

    public Movie() {}

    protected Movie(Parcel in) {
        Title = in.readString();
        Year = in.readString();
        Rated = in.readString();
        Released = in.readString();
        Runtime = in.readString();
        Genre = in.readString();
        Director = in.readString();
        Writer = in.readString();
        Actors = in.readString();
        Plot = in.readString();
        Language = in.readString();
        Country = in.readString();
        Awards = in.readString();
        Poster = in.readString();
        Metascore = in.readString();
        imdbRating = in.readString();
        imdbVotes = in.readString();
        imdbID = in.readString();
        Type = in.readString();
        Response = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Title);
        parcel.writeString(Year);
        parcel.writeString(Rated);
        parcel.writeString(Released);
        parcel.writeString(Runtime);
        parcel.writeString(Genre);
        parcel.writeString(Director);
        parcel.writeString(Writer);
        parcel.writeString(Actors);
        parcel.writeString(Plot);
        parcel.writeString(Language);
        parcel.writeString(Country);
        parcel.writeString(Awards);
        parcel.writeString(Poster);
        parcel.writeString(Metascore);
        parcel.writeString(imdbRating);
        parcel.writeString(imdbVotes);
        parcel.writeString(imdbID);
        parcel.writeString(Type);
        parcel.writeString(Response);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String metascore) {
        Metascore = metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
