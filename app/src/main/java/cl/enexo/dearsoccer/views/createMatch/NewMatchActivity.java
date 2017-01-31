package cl.enexo.dearsoccer.views.createMatch;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import cl.enexo.dearsoccer.background.GetSpinnerHours;
import cl.enexo.dearsoccer.background.GetSpinnerTypes;
import cl.enexo.dearsoccer.background.GetSpinnersDurations;
import cl.enexo.dearsoccer.background.GetSpinnersPlaces;
import cl.enexo.dearsoccer.models.SetDialog;
import cl.enexo.dearsoccer.views.config.CallbackPlaces;
import cl.enexo.dearsoccer.views.listMatchs.principal.PrincipalActivity;
import cl.enexo.dearsoccer.views.map.MapsActivity;
import cl.enexo.dearsoccer.R;
/**
 * Created by Kevin on 06-12-2016.
 */
public class NewMatchActivity extends Activity implements CallBackNewMatch,CallbackPlaces,CallbackTypes,CallbackDuration,CallbackTypeMoney,CallbackHour,CallbackSetDate {
    private ProgressDialog dialogprogress;
    private TextView tvdate,tvplacenew,tvmoneynewmatch;
    private EditText etnamenew,tvdescriptionnew,etpricenewmatch;
    private Double lat,lon;
    private Spinner spcountry,spcity,splocality,sptypenewmatch,spdurationnewmatch,sphournewmatch,spminutenewmatch;
    private SetDialog setDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        dialogprogress = new ProgressDialog(this);
        dialogprogress.show();
        spcountry = (Spinner) findViewById(R.id.spcountrynewmatch);
        spcity = (Spinner) findViewById(R.id.spcitynewmatch);
        splocality = (Spinner) findViewById(R.id.splocalitynewmatch);
        spdurationnewmatch = (Spinner) findViewById(R.id.spdurationnewmatch);
        etpricenewmatch = (EditText) findViewById(R.id.etpricenewmatch);
        tvmoneynewmatch = (TextView) findViewById(R.id.tvmoneynewmatch);
        sphournewmatch = (Spinner) findViewById(R.id.sphournewmatch);
        spminutenewmatch = (Spinner) findViewById(R.id.spminutenewmatch);
        new GetSpinnersPlaces(NewMatchActivity.this).execute(getResources(),getIntent().getStringExtra("COUNTRY").toString() ,getIntent().getStringExtra("CITY").toString(), getIntent().getStringExtra("LOCALITY").toString(), getApplicationContext(),"loading");
        spcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new GetSpinnersPlaces(NewMatchActivity.this).execute(getResources(),spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString(),spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString(),splocality.getItemAtPosition(splocality.getSelectedItemPosition()).toString(),getApplicationContext(),"city");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        etnamenew = (EditText) findViewById(R.id.etnamenewmatch);
        tvdate = (TextView) findViewById(R.id.tvdate);
        tvplacenew = (TextView) findViewById(R.id.tvplacenewmatch);
        sptypenewmatch = (Spinner) findViewById(R.id.sptypenewmatch);
        tvdescriptionnew = (EditText) findViewById(R.id.etcomentnewmatch);
        Button btncreatenewmatch =(Button) findViewById(R.id.btncreatenewmatch);
        btncreatenewmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ValidationNewMatch(NewMatchActivity.this).addMatch(etnamenew.getText().toString(),tvdate.getText().toString(),sphournewmatch.getItemAtPosition(sphournewmatch.getSelectedItemPosition()).toString()+":"+spminutenewmatch.getItemAtPosition(spminutenewmatch.getSelectedItemPosition()).toString(),tvplacenew.getText().toString(),lat.toString(),lon.toString(),spdurationnewmatch.getItemAtPosition(spdurationnewmatch.getSelectedItemPosition()).toString(),etpricenewmatch.getText().toString(),sptypenewmatch.getItemAtPosition(sptypenewmatch.getSelectedItemPosition()).toString(),tvdescriptionnew.getText().toString(),tvdate.getText().toString(),spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString(),spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString(),splocality.getItemAtPosition(splocality.getSelectedItemPosition()).toString());
            }
        });
        if (getIntent().getStringExtra("NAME").length()>0)
            etnamenew.setText(getIntent().getStringExtra("NAME"));
        if (getIntent().getStringExtra("DATE").length()>0)
            tvdate.setText(getIntent().getStringExtra("DATE"));
        if (getIntent().getStringExtra("PLACE").length()>0)
            tvplacenew.setText(getIntent().getStringExtra("PLACE"));
        if (getIntent().getStringExtra("PRICE").length()>0)
            etpricenewmatch.setText(getIntent().getStringExtra("PRICE"));
        if (getIntent().getStringExtra("DESCRIPTION").length()>0)
            tvdescriptionnew.setText(getIntent().getStringExtra("DESCRIPTION"));
        if (getIntent().getStringExtra("LAT").length()>0)
            lat = Double.valueOf((getIntent().getStringExtra("LAT")));
        if (getIntent().getStringExtra("LON").length()>0)
            lon = Double.valueOf(getIntent().getStringExtra("LON"));
        Button btncalendar = (Button) findViewById(R.id.btndate);
        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(NewMatchActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_calendar);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                setDialog = new SetDialog(NewMatchActivity.this,dialog,tvdate,getApplicationContext(),"");
                setDialog.showDialog();
            }
        });
        Button btnmap = (Button) findViewById(R.id.btnmapnewmatch);
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("NAME", etnamenew.getText().toString());
                i.putExtra("DATE", tvdate.getText().toString());
                i.putExtra("HOUR", sphournewmatch.getItemAtPosition(sphournewmatch.getSelectedItemPosition()).toString() + ":" + spminutenewmatch.getItemAtPosition(spminutenewmatch.getSelectedItemPosition()).toString());
                i.putExtra("PLACE", tvplacenew.getText().toString());
                i.putExtra("DURATION", spdurationnewmatch.getItemAtPosition(spdurationnewmatch.getSelectedItemPosition()).toString());
                i.putExtra("PRICE", etpricenewmatch.getText().toString());
                i.putExtra("TYPE", sptypenewmatch.getItemAtPosition(sptypenewmatch.getSelectedItemPosition()).toString());
                i.putExtra("DESCRIPTION", tvdescriptionnew.getText().toString());
                i.putExtra("LAT", "");
                i.putExtra("LON", "");
                i.putExtra("COUNTRY",spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString());
                i.putExtra("CITY",spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString());
                i.putExtra("LOCALITY",splocality.getItemAtPosition(splocality.getSelectedItemPosition()).toString());
                startActivity(i);
            }
        });
    }

    @Override
    public void noPlaces() {
        Toast.makeText(this, "Lugar seleccionado no registrado", Toast.LENGTH_SHORT).show();
        dialogprogress.dismiss();
    }

    @Override
    public void okPlaces(List<String> countries, List<String> cities, List<String> localities, String country, String city, String locality, String from) {
        if (countries !=null) {
            ArrayAdapter<String> adapcountry = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, countries);
            spcountry.setAdapter(adapcountry);
            if (!country.equals(null)) {spcountry.setSelection(adapcountry.getPosition(country));}
        }
        if (from.equals("loading")) {
            if (cities != null) {
                ArrayAdapter<String> adapcity = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, cities);
                spcity.setAdapter(adapcity);
                if (!city.equals(null)) {spcity.setSelection(adapcity.getPosition(city));}
            }
        }
        ArrayAdapter<String> adaplocality = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, localities);
        splocality.setAdapter(adaplocality);
        if (!locality.equals(null)) {
            try { splocality.setSelection(adaplocality.getPosition(locality));}
            catch (Exception e) {e.getMessage();
            }
        }
        new GetSpinnerTypes(NewMatchActivity.this).execute(getResources(),getIntent().getStringExtra("TYPE").toString(),getApplicationContext());
    }

    @Override
    public void okTypes(List<String> types, String type) {
        if (types !=null) {
            ArrayAdapter<String> adapterType = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, types);
            sptypenewmatch.setAdapter(adapterType);
            if (!type.equals(null) && !type.equals("")) {sptypenewmatch.setSelection(adapterType.getPosition(type));}
        }
        new GetSpinnersDurations(NewMatchActivity.this).execute(getResources(),getIntent().getStringExtra("DURATION").toString(),getApplicationContext());
    }

    @Override
    public void okDuration(List<String> durations, String duration) {
        if (duration !=null) {
            ArrayAdapter<String> adapterType = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, durations);
            spdurationnewmatch.setAdapter(adapterType);
            if (!duration.equals(null) && !duration.equals("")) {spdurationnewmatch.setSelection(adapterType.getPosition(duration));}
        }
        new TypeMoney(this,spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString()).obtainTypeMoney();
    }

    @Override
    public void okMoney(String money) {
        tvmoneynewmatch.setText(money);
        new GetSpinnerHours(NewMatchActivity.this).execute(getResources(),getIntent().getStringExtra("HOUR").toString(),getApplicationContext());
    }

    @Override
    public void noMoney() {
        Toast.makeText(this, "Este país no tiene moneda local", Toast.LENGTH_SHORT).show();
        dialogprogress.dismiss();
    }

    @Override
    public void okHour(List<String> hours, String hour, List<String> minutes, String minute) {
        if (hours !=null) {
            ArrayAdapter<String> adapHours = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, hours);
            sphournewmatch.setAdapter(adapHours);
            if (!hour.equals(null)) {sphournewmatch.setSelection(adapHours.getPosition(hour));}
        }
        if (minutes !=null) {
            ArrayAdapter<String> adapMinutes = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, minutes);
            spminutenewmatch.setAdapter(adapMinutes);
            if (!minute.equals(null)) {spminutenewmatch.setSelection(adapMinutes.getPosition(minute));}
        }
        dialogprogress.dismiss();
    }

    @Override
    public void ok() {
        Toast.makeText(this, "Partido creado con éxito", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorData() {
        Toast.makeText(this, "Debe llenar todos los datos solicitados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failNet() {
        Toast.makeText(this, "Error de conexión ,favor revisar internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NewMatchActivity.this,PrincipalActivity.class);
        startActivity(i);
    }

    @Override
    public void changeDate(String date,String from) {
        tvdate.setText(date);
        setDialog.dismissDialog();
    }

    @Override
    public void cancelDate() {
        setDialog.dismissDialog();
    }
}