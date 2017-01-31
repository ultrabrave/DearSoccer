package cl.enexo.dearsoccer.views.map;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.background.LocationByAddress;
import cl.enexo.dearsoccer.views.createMatch.NewMatchActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationCallback {
    private GoogleMap mMap;
    private static int PETICION_PERMISO_LOCALIZACION = 1;
    private Button btn;
    private EditText etdirection;
    private Double latdouble;
    private Double londouble;
    private GoogleMap mapInicialize;
    private int inicialize = 0;
    private Button btnokmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latdouble = Double.parseDouble("0.0");
        londouble = Double.parseDouble("0.0");
        etdirection = (EditText) findViewById(R.id.etdirectionInMap);
        btnokmap = (Button) findViewById(R.id.btn_ok_map);
        btnokmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, NewMatchActivity.class);
                Intent intent = getIntent();
                i.putExtra("NAME",intent.getStringExtra("NAME"));
                i.putExtra("DATE",intent.getStringExtra("DATE"));
                i.putExtra("HOUR",intent.getStringExtra("HOUR"));
                i.putExtra("PLACE",etdirection.getText().toString());
                i.putExtra("LAT",latdouble.toString());
                i.putExtra("LON",londouble.toString());
                i.putExtra("DURATION",intent.getStringExtra("DURATION"));
                i.putExtra("PRICE",intent.getStringExtra("PRICE"));
                i.putExtra("TYPE",intent.getStringExtra("TYPE"));
                i.putExtra("DESCRIPTION",intent.getStringExtra("DESCRIPTION"));
                i.putExtra("COUNTRY",intent.getStringExtra("COUNTRY"));
                i.putExtra("CITY",intent.getStringExtra("CITY"));
                i.putExtra("LOCALITY",intent.getStringExtra("LOCALITY"));
                i.putExtra("FROM",intent.getStringExtra("Map"));
                startActivity(i);
            }
        });


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        }

        btn = (Button) findViewById(R.id.btnsenddirection);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etdirection.getText().toString();
                new GetLocation(MapsActivity.this).execute(text);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (inicialize == 0) {
            inicialize = 1;
            mMap = googleMap;
            mapInicialize = mMap;
        }
    }

    @Override
    public void noAddress() {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {
                mensaje("Dirección inválida");
            }
        });
    }

    @Override
    public void fail() {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {
                mensaje("Sin acceso a la red, favor vuelva a intentar");
            }
        });
    }

    @Override
    public void success(String lat, String lng) {
        latdouble = Double.parseDouble(lat);
        londouble = Double.parseDouble(lng);
        markePoint(latdouble,londouble);
    }

    private class GetLocation extends LocationByAddress {
        private ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);

        public GetLocation(LocationCallback callback) {
            super(callback);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            progressDialog.dismiss();
        }
    }

    private void mensaje(String mensaje) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setTitle("Atención:");
        alertDialog.setMessage(mensaje);
        alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setIcon(R.drawable.common_google_signin_btn_text_dark_normal);
        alertDialog.show();
    }

    private void markePoint(Double lat, Double lon)
    {
        mMap.clear();
        mMap = mapInicialize;
        LatLng mylocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(mylocation).title("Marker in my location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12.0f));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
