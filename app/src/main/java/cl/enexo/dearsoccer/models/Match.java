package cl.enexo.dearsoccer.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Date;

/**
 * Created by Kevin on 29-11-2016.
 */

public class Match {
    public String idmatch;
    public String name;
    public String place;
    public String time;
    public String integrants;
    public String countintegrants;
    public String timestamp;
    public String creator;
    public String idsintegrants;
    public String lat;
    public String lon;
    public String year;
    public String month;
    public String day;
    public String date;
    public String hour;
    public String country;
    public String city;
    public String locality;
    public String price;
    public String type;
    public String description;
    /*
    private Date date;
    private String lat;
    private String lon;*/

    public Match() {
    }

    public Match(String idmatch,String name, String place, String time,String integrants,String countintegrants,String timestamps,String creator,String idsintegrants,String lat,String lon,String year,String month,String day,String date,String hour,String country,String city, String locality,String price,String type,String description) {
        this.idmatch = idmatch;
        this.name = name;
        this.place = place;
        this.time = time;
        this.integrants = integrants;
        this.countintegrants = countintegrants;
        this.timestamp = timestamps;
        this.creator = creator;
        this.idsintegrants = idsintegrants;
        this.year = year;
        this.month = month;
        this.day = day;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
        this.hour = hour;
        this.country = country;
        this.city = city;
        this.locality = locality;
        this.price = price;
        this.type = type;
        this.description = description;
    }
}
