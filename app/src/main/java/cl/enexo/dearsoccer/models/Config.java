package cl.enexo.dearsoccer.models;

/**
 * Created by Kevin on 24-01-2017.
 */

public class Config {
    public String country;
    public String city;
    public String locality;
    public String year;
    public String month;
    public String day;

    public Config(String country, String city, String locality, String month, String year, String day) {
        this.country = country;
        this.city = city;
        this.locality = locality;
        this.month = month;
        this.year = year;
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
