package cl.enexo.dearsoccer.views.InformationMatch;
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
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.models.DataIntentinfoMatch;
import cl.enexo.dearsoccer.models.Match;

public class InfoMatchActivity extends AppCompatActivity implements OnMapReadyCallback,ParticipeCallback,CallbackValidationParticipe {
    private int inicialize = 0;
    DataIntentinfoMatch dataIntentinfoMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_match);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapinfo);
        mapFragment.getMapAsync(this);
        TextView tvname = (TextView) findViewById(R.id.tv_name_match_info);
        TextView tvplace = (TextView) findViewById(R.id.tv_place_match_info);
        TextView tvcountintegrants = (TextView) findViewById(R.id.tv_countintegrants_match_info);
        TextView tvduration = (TextView) findViewById(R.id.tv_duration_match_info);
        TextView tvintegrants = (TextView) findViewById(R.id.tv_integrants_match_info);
        TextView tvcreator = (TextView) findViewById(R.id.tv_creator_match_info);
        TextView tvhour = (TextView) findViewById(R.id.tv_hour_match_info);
        TextView tvprice = (TextView) findViewById(R.id.tv_price_match_info);
        TextView tvdate = (TextView) findViewById(R.id.tv_date_match_info);
        TextView tvdescription = (TextView) findViewById(R.id.tv_description_match_info);
        dataIntentinfoMatch = new DataIntentinfoMatch(this,getIntent(),tvname,tvplace,tvcountintegrants,tvduration,tvintegrants,tvcreator,tvhour,tvprice,tvdate,tvdescription);
        dataIntentinfoMatch.assignData();
        Button btnunion = (Button) findViewById(R.id.btn_add_match_info);
        btnunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataIntentinfoMatch.validateMail();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (inicialize == 0) {
            inicialize = 1;
            GoogleMap mMap = googleMap;
            LatLng mylocation = new LatLng(Double.parseDouble(getIntent().getStringExtra("LAT")), Double.parseDouble(getIntent().getStringExtra("LON")));
            mMap.addMarker(new MarkerOptions().position(mylocation).title("Lugar del partido"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(getIntent().getStringExtra("LAT")),Double.parseDouble(getIntent().getStringExtra("LON"))) , 14.0f) );
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

    @Override
    public void toParticipe(Match match, String[] ids) {
        new ValidationParticipe(InfoMatchActivity.this).participeMatch(match,ids);
    }

    @Override
    public void inMatch() {
        Toast.makeText(InfoMatchActivity.this, "Esta correo ya se encuentra registrado en el partido", Toast.LENGTH_SHORT).show();
    }
}