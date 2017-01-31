package cl.enexo.dearsoccer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cl.enexo.dearsoccer.R;

/**
 * Created by Kevin on 29-01-2017.
 */

public class MatchHolder extends RecyclerView.ViewHolder{
    private TextView tvname;
    private TextView tvduration;
    private TextView tvplace;
    private TextView tvintegrans;
    private TextView tvcountintegrants;
    private TextView tvtimestamps;
    private TextView tvtexto;
    private LinearLayout btnaddplayer;
    private TextView tvidmatch;
    private TextView tvcreator;
    private TextView tvidsintegrants;
    private TextView tvdate;
    private TextView tvhour;
    private TextView tvcountry;
    private TextView tvcity;
    private TextView tvlocality;
    private TextView tvprice;
    private TextView tvtype;
    private TextView tvdescription;
    private TextView tvlat;
    private TextView tvlon;

    public MatchHolder(View itemView) {
        super(itemView);
        tvname = (TextView) itemView.findViewById(R.id.tv_name_item_match);
        tvplace = (TextView) itemView.findViewById(R.id.tv_place_item_match);
        tvduration = (TextView) itemView.findViewById(R.id.tv_duration_item_match);
        tvintegrans = (TextView) itemView.findViewById(R.id.tv_integrants_item_match);
        tvcountintegrants = (TextView) itemView.findViewById(R.id.tv_countintegrants_item_match);
        tvtimestamps = (TextView) itemView.findViewById(R.id.tv_timestamps_item_match);
        tvtexto = (TextView) itemView.findViewById(R.id.tv_texto_visible_match);
        tvidmatch = (TextView) itemView.findViewById(R.id.tv_idmatch_item_match);
        btnaddplayer = (LinearLayout) itemView.findViewById(R.id.btn_see_match);
        tvcreator = (TextView) itemView.findViewById(R.id.tv_creator_item_match);
        tvidsintegrants = (TextView) itemView.findViewById(R.id.tv_idsintegrants_item_match);
        tvdate = (TextView) itemView.findViewById(R.id.tv_date_item_match);
        tvhour = (TextView) itemView.findViewById(R.id.tv_hour_item_match);
        tvcountry = (TextView) itemView.findViewById(R.id.tv_country_item_match);
        tvcity = (TextView) itemView.findViewById(R.id.tv_city_item_match);
        tvlocality = (TextView) itemView.findViewById(R.id.tv_locality_item_match);
        tvprice = (TextView) itemView.findViewById(R.id.tv_price_item_match);
        tvtype = (TextView) itemView.findViewById(R.id.tv_type_item_match);
        tvdescription = (TextView) itemView.findViewById(R.id.tv_description_item_match);
        tvlat = (TextView) itemView.findViewById(R.id.tv_lat_item_match);
        tvlon = (TextView) itemView.findViewById(R.id.tv_lon_item_match);
    }

    public void setDescription(String description)
    {
        tvdescription.setText(description);
    }
    public void setLat(String lat)
    {
        tvlat.setText(lat);
    }

    public void setLon(String lon)
    {
        tvlat.setText(lon);
    }

    public void setType(String type)
    {
        tvtype.setText(type);
    }

    public void setPrice(String price)
    {
        tvprice.setText(price);
    }

    public void setLocality(String locality)
    {
        tvlocality.setText(locality);
    }

    public void setCity(String city)
    {
        tvcity.setText(city);
    }

    public void setCountry(String country)
    {
        tvcountry.setText(country);
    }

    public void setHour(String hour)
    {
        tvhour.setText(hour);
    }

    public void setDate(String date)
    {
        tvdate.setText(date);
    }

    public void setName(String name) {
        tvname.setText(name);
    }

    public void setPlace(String place) {
        tvplace.setText(place);
    }

    public void setDuration(String duration) {
        tvduration.setText(duration);
    }

    public void setIntegrants(String integrants) {
        tvintegrans.setText(integrants);
    }

    public void setCountintegrants(String countintegrants) {
        tvcountintegrants.setText(countintegrants);
    }

    public void setTimestamps(String timestamps) {
        tvtimestamps.setText(timestamps);
    }

    public void setTexto(String texto) {
        tvtexto.setText(texto);
    }
    public void setIdMatch(String idmatch) {
        tvidmatch.setText(idmatch);
    }
    public void setCreator(String creator) {
        tvcreator.setText(creator);
    }
    public void setIdsIntegrants(String idsIntegrants) {
        tvcreator.setText(idsIntegrants);
    }
    public LinearLayout getBtnaddplayer()
    {
        return btnaddplayer;
    }
}
