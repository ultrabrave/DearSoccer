package cl.enexo.dearsoccer.views.createMatch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import cl.enexo.dearsoccer.models.Match;

/**
 * Created by Kevin on 14-01-2017.
 */

public class ValidationNewMatch {

    private CallBackNewMatch callBackNewMatch;

    public ValidationNewMatch(CallBackNewMatch callBackNewMatch) {
        this.callBackNewMatch = callBackNewMatch;
    }

    public void addMatch(String name,String date,String hour,String place, String lat, String lon,String duration,String price,String type,String description,String dateToValidate,String country,String city,String locality)
    {
        String year = "";
        String month = "";
        String day = "";
        try {
            String[] datesplit = dateToValidate.split("-");
            year =  datesplit[2];
            month =  datesplit[1];
            day =  datesplit[0];
        }
        catch (Exception e)
        {
            year = "";
            month = "";
            day = "";
        }
        String dur = "";
        try {
            String[] durationsplit = duration.split(" ");
            dur = durationsplit[0];
        }
        catch (Exception e)
        {
            dur = "";
        }

        int error = 0;

        if (name == null || name.length() == 0)
        {
            error = 1;
        }
        if (date == null || date.length() == 0)
        {
            error = 1;
        }
        if (hour == null || hour.length() == 0)
        {
            error = 1;
        }
        if (place == null || place.length() == 0)
        {
            error = 1;
        }
        if (lat == null || lat.length() == 0)
        {
            error = 1;
        }
        if (lon == null || lon.length() == 0)
        {
        error = 1;
        }
        if (dur == null || dur.length() == 0)
        {
            error = 1;
        }
        if (price == null || price.length() == 0)
        {
            error = 1;
        }
        if (type == null || type.length() == 0)
        {
            error = 1;
        }
        if (description == null || description.length() == 0)
        {
            error = 1;
        }
        if (year == null || year.length() == 0)
        {
            error = 1;
        }
        if (month == null || month.length() == 0)
        {
            error = 1;
        }
        if (day == null || day.length() == 0)
        {
            error = 1;
        }
        if (country == null || country.length() == 0)
        {
            error = 1;
        }
        if (city == null || city.length() == 0)
        {
            error = 1;
        }
        if (locality == null || locality.length() == 0)
        {
            error = 1;
        }
        if (error == 1)
        {
            callBackNewMatch.errorData();
        }
        else
        {
            try
            {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String email = auth.getCurrentUser().getEmail();
                String nameuser = auth.getCurrentUser().getDisplayName();
                String iduser = auth.getCurrentUser().getUid();
                String integrants = nameuser+ "-" + email + "\n";
                String creator = nameuser;
                String countintegrants = "1";
                String timestamps = String.valueOf(System.currentTimeMillis());
                String idmatch = iduser+name+timestamps;
                String idsintegrans = iduser + ";";
                Match match = new Match(idmatch,name,place,dur,integrants,countintegrants,timestamps,creator,idsintegrans,lat,lon,year,month,day,date,hour,country,city,locality,price,type,description);
                FirebaseDatabase.getInstance().getReference().child(locality).child(iduser+name+timestamps).setValue(match);
                FirebaseDatabase.getInstance().getReference().child("match_creator").child(iduser+";").child(iduser+name+timestamps).setValue(match);
                callBackNewMatch.ok();
            }
            catch (Exception e)
            {
                callBackNewMatch.failNet();
            }
        }
    }
}
