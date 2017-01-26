package cl.enexo.dearsoccer.views.InformationMatch;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.models.Match;
import cl.enexo.dearsoccer.views.createMatch.NewMatchActivity;
import cl.enexo.dearsoccer.views.createMatch.ValidationNewMatch;


public class InfoMatchActivity extends AppCompatActivity implements OnMapReadyCallback,ParticipeCallback {
    private TextView tvname;
    private TextView tvplace;
    private TextView tvcountintegrants;
    private TextView tvintegrants;
    private TextView tvduration;
    private TextView tvcreator;
    private TextView tvhour;
    private TextView tvprice;
    private TextView tvdate;
    private TextView tvdescription;
    private Button btnunion;
    private String mymail;
    private String myname;
    private String iduser;
    private GoogleMap mMap;
    private Double latdouble;
    private Double londouble;
    private int inicialize = 0;
    private String country;
    private String city;
    private String locality;
    private String date;
    private String lat;
    private String lon;
    private String hour;
    private String price;
    private String type;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_match);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapinfo);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String place = intent.getStringExtra("PLACE");
        String duration = intent.getStringExtra("DURATION");
        String count = intent.getStringExtra("COUNT");
        String integrants = intent.getStringExtra("INTEGRANTS");
        String timestamps = intent.getStringExtra("TIMESTAMPS");
        String creator = intent.getStringExtra("CREATOR");
        hour = intent.getStringExtra("HOUR");
        price = intent.getStringExtra("PRICE");
        date = intent.getStringExtra("DATE");
        lat = intent.getStringExtra("LAT");
        lon = intent.getStringExtra("LON");
        country = intent.getStringExtra("COUNTRY");
        city = intent.getStringExtra("CITY");
        locality = intent.getStringExtra("LOCALITY");
        type = intent.getStringExtra("TYPE");
        description = intent.getStringExtra("DESCRIPTION");
        latdouble = Double.parseDouble(lat);
        londouble = Double.parseDouble(lon);
        final String idmatch = intent.getStringExtra("IDMATCH");
        tvname = (TextView) findViewById(R.id.tv_name_match_info);
        tvplace = (TextView) findViewById(R.id.tv_place_match_info);
        tvduration = (TextView) findViewById(R.id.tv_duration_match_info);
        tvcountintegrants = (TextView) findViewById(R.id.tv_countintegrants_match_info);
        tvintegrants = (TextView) findViewById(R.id.tv_integrants_match_info);
        tvcreator = (TextView) findViewById(R.id.tv_creator_match_info);
        tvhour = (TextView) findViewById(R.id.tv_hour_match_info);
        tvprice = (TextView) findViewById(R.id.tv_price_match_info);
        tvdate = (TextView) findViewById(R.id.tv_date_match_info);
        tvdescription = (TextView) findViewById(R.id.tv_description_match_info);
        btnunion = (Button) findViewById(R.id.btn_add_match_info);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mymail = auth.getCurrentUser().getEmail();
        iduser = auth.getCurrentUser().getUid();
        myname =auth.getCurrentUser().getDisplayName();

        tvname.setText("Nombre: " + name);
        tvplace.setText("Lugar: " + place);
        tvcountintegrants.setText(count + " Jugador(es)");
        tvduration.setText("Duración: " + duration + " hrs");
        tvintegrants.setText("Integrantes \n " + integrants);
        tvcreator.setText("Creador: " + creator);
        tvhour.setText("Hora partido: " + hour);
        tvprice.setText("Valor por jugador: " + price);
        tvdate.setText("Fecha partido: " + date);
        tvdescription.setText("Descripción: " + description);

        btnunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inte = getIntent().getStringExtra("INTEGRANTS");
                String[] integrantss =  inte.trim().split("\n");
                int exist = 0;
                for (int i = 0; i < integrantss.length; i++) {
                    String[] mails = integrantss[i].split("-");
                    if (mymail.equals(mails[1]))
                    {
                        exist = 1;
                    }
                }
                if (exist == 1)
                {
                    Toast.makeText(InfoMatchActivity.this, "Este correo ya se encuentra participando en el partido", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = getIntent();
                    String name = intent.getStringExtra("NAME");
                    String place = intent.getStringExtra("PLACE");
                    String duration = intent.getStringExtra("DURATION");
                    String count = intent.getStringExtra("COUNT");
                    String integrants = intent.getStringExtra("INTEGRANTS");
                    String creator = intent.getStringExtra("CREATOR");
                    String idsintegrants =  intent.getStringExtra("IDSINTEGRANTS");
                    integrants = integrants + myname + "-" + mymail +  "\n";
                    idsintegrants = idsintegrants + iduser + ";";
                    int countint = Integer.parseInt(count);
                    countint = countint + 1;
                    String timestamps = intent.getStringExtra("TIMESTAMPS");
                    String[] ids = idsintegrants.split(";");
                    String[] datearray = date.split("-");
                    String year = datearray[2];
                    String month = datearray[1];
                    String day = datearray[0];

                    Match match = new Match(idmatch,name,place,duration,integrants,String.valueOf(countint),timestamps,creator,idsintegrants,lat,lon,year,month,day,date,hour,country,city,locality,price,type,description);
                    new ValidationParticipe(InfoMatchActivity.this).participeMatch(match,ids);
                    /*FirebaseDatabase.getInstance().getReference().child("match_creator").child("user").child(ids[0]+ ";").child(idmatch).setValue(match);

                    for (int i = 1; i < ids.length; i++) {
                        FirebaseDatabase.getInstance().getReference().child("match_participer").child("user").child(ids[i]+";").child("match").child(idmatch).setValue(match);
                    }

                    FirebaseDatabase.getInstance().getReference().child("match").child("country").child(country).child("city").child(city).child("locality").child(locality).child("year").child(year).child("month").child(month).child("day").child(day).child("public").child("match").child(idmatch).setValue(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(InfoMatchActivity.this, "Congratulations, you is in this match", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });*/
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (inicialize == 0) {
            inicialize = 1;
            mMap = googleMap;
            LatLng mylocation = new LatLng(latdouble, londouble);
            mMap.addMarker(new MarkerOptions().position(mylocation).title("Lugar del partido"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latdouble,londouble) , 14.0f) );
        }
    }

    @Override
    public void failNetwork() {
        Toast.makeText(InfoMatchActivity.this, "Problemas con su conexión , favore reintentar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ok() {
        Toast.makeText(InfoMatchActivity.this, "Felicidades, usted está participando en el partido", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
