package cl.enexo.dearsoccer.views.listMatchs.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cl.enexo.dearsoccer.BD.Bd;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.models.Match;
import cl.enexo.dearsoccer.views.InformationMatch.InfoMatchActivity;
import cl.enexo.dearsoccer.views.login.ValidateLogin;

public class CreatedFragment extends Fragment{
    private SeekBar sbduration;
    private TextView counter;
    private EditText etname;
    private EditText etplace;
    private Button btnadd;
    private View myview;
    private String mycountry;
    private String mycity;
    private String mylocality;
    private String myyear;
    private String mymonth;
    private String myday;
    Bd usdbh;
    private String iduser;

    public CreatedFragment() {
    }

    public static CreatedFragment newInstance() {
        return new CreatedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_created, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myview = view;
        usdbh = new Bd(getContext(), "DB_DEARSOCCER", null, 1);
        new getConfig().execute();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        iduser = auth.getCurrentUser().getUid();
    }

    public static class MatchHolder extends RecyclerView.ViewHolder {
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
            tvname = (TextView) itemView.findViewById(R.id.tv_name_item_mymatches);
            tvplace = (TextView) itemView.findViewById(R.id.tv_place_item_mymatches);
            tvduration = (TextView) itemView.findViewById(R.id.tv_duration_item_mymatches);
            tvintegrans = (TextView) itemView.findViewById(R.id.tv_integrants_item_mymatches);
            tvcountintegrants = (TextView) itemView.findViewById(R.id.tv_countintegrants_item_mymatches);
            tvtimestamps = (TextView) itemView.findViewById(R.id.tv_timestamps_item_mymatches);
            tvtexto = (TextView) itemView.findViewById(R.id.tv_texto_visible_mymatches);
            tvidmatch = (TextView) itemView.findViewById(R.id.tv_idmatch_item_mymatches);
            btnaddplayer = (LinearLayout) itemView.findViewById(R.id.btn_see_mymatches);
            tvcreator = (TextView) itemView.findViewById(R.id.tv_creator_item_mymatches);
            tvidsintegrants = (TextView) itemView.findViewById(R.id.tv_idsintegrants_item_mymatches);
            tvdate = (TextView) itemView.findViewById(R.id.tv_date_item_mytches);
            tvhour = (TextView) itemView.findViewById(R.id.tv_hour_item_mytches);
            tvcountry = (TextView) itemView.findViewById(R.id.tv_country_item_mytches);
            tvcity = (TextView) itemView.findViewById(R.id.tv_city_item_mytches);
            tvlocality = (TextView) itemView.findViewById(R.id.tv_locality_item_mytches);
            tvprice = (TextView) itemView.findViewById(R.id.tv_price_item_mytches);
            tvtype = (TextView) itemView.findViewById(R.id.tv_type_item_mytches);
            tvdescription = (TextView) itemView.findViewById(R.id.tv_description_item_mytches);
            tvlat = (TextView) itemView.findViewById(R.id.tv_lat_item_mytches);
            tvlon = (TextView) itemView.findViewById(R.id.tv_lon_item_mytches);
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
    }

    private class getConfig extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return 0;
        }

        protected void onPostExecute(Object result) {
            SQLiteDatabase db = usdbh.getWritableDatabase();
            mycountry = usdbh.getCountrySelected(db);
            mycity = usdbh.getCitySelected(db);
            mylocality = usdbh.getLocalitySelected(db);
            String mydate = usdbh.getDateSelected(db);
            String[] stringdate = mydate.split("-");
            myyear = stringdate[0];
            mymonth = stringdate[1];
            myday = stringdate[2];
            getMatches();
        }
    }


    private void getMatches()
    {
        RecyclerView recycler = (RecyclerView) myview.findViewById(R.id.rv_mymatches);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("match_creator").child("user").child(iduser+";");
        FirebaseRecyclerAdapter adapter =
                new FirebaseRecyclerAdapter<Match, MatchHolder>
                        (Match.class, R.layout.item_my_matches, MatchHolder.class, reference) {
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
                        viewHolder.setCountintegrants(countintegrants+ " Integrants");
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

                        viewHolder.btnaddplayer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(),InfoMatchActivity.class);
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
                                startActivity(i);
                            }
                        });
                    }
                };
        recycler.setAdapter(adapter);
    }

}
