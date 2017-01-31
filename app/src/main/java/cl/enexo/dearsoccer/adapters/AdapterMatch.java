package cl.enexo.dearsoccer.adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import cl.enexo.dearsoccer.models.Match;
import cl.enexo.dearsoccer.views.InformationMatch.InfoMatchActivity;

/**
 * Created by Kevin on 29-01-2017.
 */

public class AdapterMatch extends FirebaseRecyclerAdapter<Match, MatchHolder>{
    Activity activity;
    public AdapterMatch(Class<Match> modelClass, int modelLayout, Class<MatchHolder> viewHolderClass, Query ref, Activity activity) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.activity =  activity;
    }

    @Override
    protected void populateViewHolder(MatchHolder viewHolder, Match model, int position) {
        final String name = model.name;
        final String place = model.place;
        final String lat = model.lat;
        final String lon = model.lon;
        final String duration = model.time;
        final String integrants = model.integrants;
        final String countintegrants = model.countintegrants;
        final String timestamps = model.timestamp;
        final String texto =  model.name + "," + model.place;
        final String idmatch = model.idmatch;
        final String creator = model.creator;
        final String idsintegrants = model.idsintegrants;
        final String date = model.date;
        final String hour = model.hour;
        final String country = model.country;
        final String city = model.city;
        final String locality = model.locality;
        final String price = model.price;
        final String type = model.type;
        final String description = model.description;
        viewHolder.setName(name);
        viewHolder.setPlace(place);
        viewHolder.setDuration(duration);
        viewHolder.setIntegrants(integrants);
        viewHolder.setCountintegrants(countintegrants+ " Integrantes");
        viewHolder.setTimestamps(timestamps);
        viewHolder.setTexto(texto);
        viewHolder.setIdMatch(idmatch);
        viewHolder.setIdsIntegrants(idmatch);
        viewHolder.setDate(date);
        viewHolder.setHour(hour);
        viewHolder.setCountry(country);
        viewHolder.setCity(city);
        viewHolder.setLocality(locality);
        viewHolder.setPrice(price);
        viewHolder.setType(type);
        viewHolder.setLat(lat);
        viewHolder.setLon(lon);
        viewHolder.setDescription(description);
        viewHolder.getBtnaddplayer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity,InfoMatchActivity.class);
                i.putExtra("NAME",name);
                i.putExtra("PLACE",place);
                i.putExtra("DURATION",duration);
                i.putExtra("COUNT",countintegrants);
                i.putExtra("INTEGRANTS",integrants);
                i.putExtra("TIMESTAMPS",timestamps);
                i.putExtra("IDMATCH",idmatch);
                i.putExtra("CREATOR",creator);
                i.putExtra("IDSINTEGRANTS",idsintegrants);
                i.putExtra("DATE",date);
                i.putExtra("HOUR",hour);
                i.putExtra("COUNTRY",country);
                i.putExtra("CITY",city);
                i.putExtra("LOCALITY",locality);
                i.putExtra("PRICE",price);
                i.putExtra("TYPE",type);
                i.putExtra("DESCRIPTION",description);
                i.putExtra("LAT",lat);
                i.putExtra("LON",lon);
                activity.startActivity(i);
            }
        });



    }
}