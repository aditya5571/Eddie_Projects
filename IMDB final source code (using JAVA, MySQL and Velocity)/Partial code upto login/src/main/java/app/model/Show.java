package app.model;


public class Show {

    public final String id;
    public final String showTitle;
    public final String genre;
    public final String length;
    public final String movie;
    public final String series;
    public final String procoID;
    public final String year;

    public Show(String id, String showTitle, String genre, String length, String movie, String series, String procoID,
                String year) {
        this.id = id;
        this.showTitle = showTitle;
        this.genre = genre;
        this.length = length;
        this.movie = movie;
        this.series = series;
        this.procoID = procoID;
        this.year = year;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.showTitle;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getLength() {
        return this.length;
    }

    public String getMovie() {
        return this.movie;
    }

    public String getSeries() {
        return this.series;
    }

    public String getProcoID() {
        return this.procoID;
    }

    public String getYear() {
        return this.year;
    }
}
