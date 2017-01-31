package cl.enexo.dearsoccer.views.config;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.background.AddConfig;
import cl.enexo.dearsoccer.background.ConfigSelected;
import cl.enexo.dearsoccer.background.GetSpinnersPlaces;
import cl.enexo.dearsoccer.models.Config;
import cl.enexo.dearsoccer.models.SetDialog;
import cl.enexo.dearsoccer.views.createMatch.CallbackSetDate;
import cl.enexo.dearsoccer.views.listMatchs.principal.PrincipalActivity;

public class ConfigActivity extends Activity implements ConfigSelectedCallback,CallbackPlaces,CallbackAddConfig,CallbackSetDate {
    private Spinner spcountry,spcity,splocality;
    private ProgressDialog dialogprogress;
    private TextView tvmydateini,tvmydatefin;
    private SetDialog setDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        dialogprogress = new ProgressDialog(this);
        dialogprogress.show();
        new GetSpinnersPlaces(ConfigActivity.this).execute(getResources(),"","","",getApplicationContext(),"loading");
        spcountry = (Spinner) findViewById(R.id.sp_country_config);
        spcity = (Spinner) findViewById(R.id.sp_city_config);
        splocality = (Spinner) findViewById(R.id.sp_locality_config);
        Button okbtn = (Button) findViewById(R.id.btn_ok_config);
        tvmydateini = (TextView) findViewById(R.id.tvdateconfig);
        tvmydatefin = (TextView) findViewById(R.id.tvdatefinconfig);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogprogress.show();
                new AddConfig(ConfigActivity.this,ConfigActivity.this).execute(spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()),spcity.getItemAtPosition(spcity.getSelectedItemPosition()),splocality.getItemAtPosition(splocality.getSelectedItemPosition()),tvmydateini.getText().toString(),tvmydatefin.getText().toString());
            }
        });
        Button btndateini = (Button) findViewById(R.id.btn_sel_date_config);
        btndateini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ConfigActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_calendar);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                setDialog = new SetDialog(ConfigActivity.this,dialog,tvmydateini,getApplicationContext(),"init");
                setDialog.showDialog();
            }
        });
        Button btndatefin = (Button) findViewById(R.id.btn_sel_datefin_config);
        btndatefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ConfigActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_calendar);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                setDialog = new SetDialog(ConfigActivity.this,dialog,tvmydatefin,getApplicationContext(),"");
                setDialog.showDialog();
            }
        });
        if (getIntent().getStringExtra("WHERE").equals("INIT")) {
            new ConfigSelected(ConfigActivity.this).execute(getApplicationContext());}
        spcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new GetSpinnersPlaces(ConfigActivity.this).execute(getResources(),spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString(),spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString(),"",getApplicationContext(),"city");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    @Override
    public void ok(Config config) {
        Intent i = new Intent(ConfigActivity.this,PrincipalActivity.class);
        startActivity(i);
    }
    @Override
    public void noPlaces() {
        Toast.makeText(this, "Lugar seleccionado no registrado", Toast.LENGTH_SHORT).show();
        dialogprogress.dismiss();
    }

    @Override
    public void okPlaces(List<String> countries, List<String> cities, List<String> localities, String country, String city, String locality, String from) {
        if (countries !=null) {
            ArrayAdapter<String> adapcountry = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_checked, countries);
            spcountry.setAdapter(adapcountry);
            if (!country.equals(null)) {
                spcountry.setSelection(adapcountry.getPosition(country));}
        }
        if (from.equals("loading")) {
            if (cities != null) {
                ArrayAdapter<String> adapcity = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_checked, cities);
                spcity.setAdapter(adapcity);
                if (!city.equals(null)) {
                    spcity.setSelection(adapcity.getPosition(city));
                }
            }
        }
        ArrayAdapter<String> adaplocality = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_checked, localities);
        splocality.setAdapter(adaplocality);
        if (!locality.equals(null)) {
            splocality.setSelection(adaplocality.getPosition(locality));}
        dialogprogress.dismiss();
    }

    @Override
    public void okAdd() {
        Intent i = new Intent(ConfigActivity.this,PrincipalActivity.class);
        startActivity(i);
        dialogprogress.dismiss();
    }
    @Override
    public void failAdd() {
        dialogprogress.dismiss();
        Toast.makeText(this, "Favor llenar todos los datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeDate(String date,String from) {
        if (from.equals("init"))    tvmydateini.setText(date);
        else tvmydatefin.setText(date);
        setDialog.dismissDialog();
    }

    @Override
    public void cancelDate() {
        setDialog.dismissDialog();
    }
}