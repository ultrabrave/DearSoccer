package cl.enexo.dearsoccer.models;

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
    /*
    private Date date;
    private String lat;
    private String lon;*/

    public Match() {
    }

    public Match(String idmatch,String name, String place, String time,String integrants,String countintegrants,String timestamps,String creator,String idsintegrants) {
        this.idmatch = idmatch;
        this.name = name;
        this.place = place;
        this.time = time;
        this.integrants = integrants;
        this.countintegrants = countintegrants;
        this.timestamp = timestamps;
        this.creator = creator;
        this.idsintegrants = idsintegrants;
    }
}
