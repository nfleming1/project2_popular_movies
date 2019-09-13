package com.example.project2_popular_movies;

public class Movie {

    private String title;
    private String poster_path;
    private String synopsis;
    private String rating;
    private String release_date;

    public Movie(){}

    public Movie(String newTitle, String newPoster_path, String newSynopsis, String newRating, String newRelease_date){
        this.title = newTitle;
        this.poster_path = newPoster_path;
        this.synopsis = newSynopsis;
        this.rating = newRating;
        this.release_date = newRelease_date;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }
    public String getTitle() {
        return title;
    }

    public void setPoster_path(String newPoster_path){
        poster_path = newPoster_path;
    }
    public String getPoster_path(){ return poster_path; }

    public void setSynopsis(String newSynopsis){
        synopsis = newSynopsis;
    }
    public String getSynopsis(){
        return synopsis;
    }

    public void setRating(String newRating){
        rating = newRating;
    }
    public String getRating(){
        return rating;
    }

    public void setRelease_date(String newRelease_date){
        release_date = newRelease_date;
    }
    public String getRelease_date(){
        return release_date;
    }

    public static String buildPosterPath(String newPath, char size){
        String poster_base = "https://image.tmdb.org/t/p/";
        String poster_size = "";
        if(size == 's')
            poster_size = "w342";
        else if(size == 'l')
            poster_size = "w780";

        String path = poster_base.concat(poster_size);
        path = path.concat(newPath);

        return path;
    }
}
