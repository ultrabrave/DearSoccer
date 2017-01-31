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
    public String dayfinal;
    public String monthfinal;
    public String yearfinal;

    public Config(String country, String city, String locality, String year, String month, String day, String yearfinal, String monthfinal, String dayfinal) {
        this.country = country;
        this.city = city;
        this.locality = locality;
        this.year = year;
        this.day = day;
        this.month = month;
        this.dayfinal = dayfinal;
        this.monthfinal = monthfinal;
        this.yearfinal = yearfinal;
    }

    public String getDayfinal() {
        return dayfinal;
    }

    public String getMonthfinal() {
        return monthfinal;
    }

    public String getYearfinal() {
        return yearfinal;
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
