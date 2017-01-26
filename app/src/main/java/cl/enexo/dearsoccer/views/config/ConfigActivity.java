package cl.enexo.dearsoccer.views.config;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.enexo.dearsoccer.BD.Bd;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.background.ConfigSelected;
import cl.enexo.dearsoccer.data.FirebaseRef;
import cl.enexo.dearsoccer.models.Config;
import cl.enexo.dearsoccer.views.listMatchs.principal.PrincipalActivity;
import cl.enexo.dearsoccer.views.initPage.MainActivity;

public class ConfigActivity extends Activity implements ConfigSelectedCallback {
    private Spinner spcountry;
    private Spinner spcity;
    private Spinner splocality;
    Bd usdbh = new Bd(ConfigActivity.this, "DB_DEARSOCCER", null, 1);
    String areaName = "";
    String areaName2 = "";
    String areaName3 = "";
    private Button btnselfecha;
    private Dialog dialog;
    private CompactCalendarView calendarView;
    private TextView tvmonth;
    private TextView tvmonthdialog;
    private TextView tvdaydialog;
    private TextView tvyeardialog;
    private TextView btncancelcalendar;
    private TextView btnokcalendar;
    private int year;
    private String yearString;
    private int month;
    private String monthString;
    private int day;
    private String dayString;
    private TextView tvdate;
    private Button okbtn;
    private ProgressDialog pd = null;
    private ProgressDialog dialogprogress;
    LinearLayout linear_config;
    LinearLayout l1;
    LinearLayout l2;
    private String mycountry;
    private String mycity;
    private String mylocality;
    private int ifconfig = 0;
    private FirebaseRef ref = new FirebaseRef();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent x = getIntent();
        String where = x.getStringExtra("WHERE");
        linear_config = (LinearLayout) findViewById(R.id.linear_config);
        l1 = (LinearLayout) findViewById(R.id.linear_screen_config);
        l2 = (LinearLayout) findViewById(R.id.linear_loading_config);
        ImageView gyroView = (ImageView) findViewById(R.id.img_loading_config);
        gyroView.setBackgroundResource(R.drawable.loading_gif);
        AnimationDrawable gyroAnimation = (AnimationDrawable) gyroView.getBackground();
        gyroAnimation.start();
        dialogprogress = new ProgressDialog(ConfigActivity.this);
        spcountry = (Spinner) findViewById(R.id.sp_country_config);
        spcity = (Spinner) findViewById(R.id.sp_city_config);
        splocality = (Spinner) findViewById(R.id.sp_locality_config);
        btnselfecha = (Button) findViewById(R.id.btn_sel_fecha_config);
        tvdate = (TextView) findViewById(R.id.tvdateconfig);
        okbtn = (Button) findViewById(R.id.btn_ok_config);


        btnselfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
                dialog.show();
            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogprogress.show();
                new addConfig().execute();
            }
        });

        if (where.equals("INIT")) {
            new Configselect(ConfigActivity.this).execute(getApplicationContext());
        }

        spcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String country = spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString();
                FirebaseDatabase.getInstance().getReference().child("country").child(country).child("city").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> areas = new ArrayList<String>();
                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                            areaName2 = areaSnapshot.getKey().toString();
                            areas.add(areaName2);
                            SQLiteDatabase db = usdbh.getWritableDatabase();
                            usdbh.addCity(db,country,areaName2);
                        }
                        new SpinnerCities().execute();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String country = spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString();
                final String city = spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString();
                        FirebaseDatabase.getInstance().getReference().child("country").child(country).child("city").child(city).child("locality").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final List<String> areas = new ArrayList<String>();

                                    for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                                        areaName3 = areaSnapshot.getKey().toString();
                                        areas.add(areaName3);
                                        SQLiteDatabase db = usdbh.getWritableDatabase();
                                        usdbh.addLocality(db,city,areaName3);
                                    }
                                    new SpinnerLocalities().execute();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("country").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    areaName = areaSnapshot.getKey().toString();
                    areas.add(areaName);
                    SQLiteDatabase db = usdbh.getWritableDatabase();
                    usdbh.addCountry(db,areaName);
                }
                new SpinnerCountrys().execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ifconfig != 1) {
                    if (l2.getLayoutParams().width != 0) {
                        Toast.makeText(ConfigActivity.this, "Sin acceso a red, favor reintentar", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ConfigActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            }
        },10000);

    }

    @Override
    public void noConfig() {
        SQLiteDatabase db = usdbh.getWritableDatabase();
        usdbh.BorrarTablas(db);
        usdbh.CrearTablas(db);
        ifconfig = 0;
    }

    @Override
    public void ok(Config config) {
      Intent i = new Intent(ConfigActivity.this,PrincipalActivity.class);
        startActivity(i);
        ifconfig =1;
    }

    private class SpinnerCountrys extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        protected void onPostExecute(Object result) {
            SQLiteDatabase db = usdbh.getWritableDatabase();
            ArrayAdapter<String> adapcli = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_checked, usdbh.getCountrys(db));
            spcountry.setAdapter(adapcli);
        }
    }


    private class SpinnerCities extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return 0;
        }

        protected void onPostExecute(Object result) {
           String dato = spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString();
            SQLiteDatabase db = usdbh.getWritableDatabase();
            ArrayAdapter<String> adapcli = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_checked, usdbh.getCities(db,dato));
            spcity.setAdapter(adapcli);
        }
    }


    private class SpinnerLocalities extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return 0;
        }

        protected void onPostExecute(Object result) {
            SQLiteDatabase db = usdbh.getWritableDatabase();
            String dato = spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString();
            ArrayAdapter<String> adapcli = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_checked, usdbh.getLocalities(db,dato));
            splocality.setAdapter(adapcli);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            l1.setLayoutParams(params1);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    0, 0);
            l2.setLayoutParams(params2);
        }
    }

    private void setDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_calendar);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        tvmonth = (TextView) dialog.findViewById(R.id.tvmonthcalendar);
        tvmonthdialog = (TextView) dialog.findViewById(R.id.tvmonthdialog);
        tvdaydialog = (TextView) dialog.findViewById(R.id.tvdaydialog);
        tvyeardialog = (TextView) dialog.findViewById(R.id.tvyeardialog);
        btnokcalendar = (Button) dialog.findViewById(R.id.btnokcalendar);
        btncancelcalendar = (Button) dialog.findViewById(R.id.btncancelcalendar);
        final SimpleDateFormat formatter = new SimpleDateFormat("MMM");
        final SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
        final SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy");
        final SimpleDateFormat formatter4 = new SimpleDateFormat("MM");
        final Date cDate = new Date();
        tvmonth.setText(formatter.format(cDate).toUpperCase().replace(".", ""));
        tvmonthdialog.setText(formatter.format(cDate).toUpperCase().replace(".", ""));
        tvdaydialog.setText(formatter2.format(cDate).toUpperCase().replace(".", "").replace(":", ""));
        tvyeardialog.setText(formatter3.format(cDate).toUpperCase().replace(".", "").replace(":", ""));
        calendarView = (CompactCalendarView) dialog.findViewById(R.id.mycalendar);

        btncancelcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnokcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                changeDate();
            }
        });

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                tvdaydialog.setText(formatter2.format(dateClicked).toUpperCase().replace(".", "").replace(":", ""));
                tvyeardialog.setText(formatter3.format(dateClicked).toUpperCase().replace(".", "").replace(":", ""));
                yearString = tvyeardialog.getText().toString();
                monthString = formatter4.format(dateClicked).toUpperCase().replace(".", "").replace(":", "");
                dayString = tvdaydialog.getText().toString();
                if (dateFormat.format(dateClicked).equals(dateFormat.format(cDate))) {
                    calendarView.setCurrentDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black_overlay));
                } else {
                    calendarView.setCurrentDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.greydark));
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tvmonth.setText(formatter.format(firstDayOfNewMonth).toUpperCase().replace(".", ""));
                tvmonthdialog.setText(formatter.format(firstDayOfNewMonth).toUpperCase().replace(".", ""));
                tvdaydialog.setText(formatter2.format(firstDayOfNewMonth).toUpperCase().replace(".", "").replace(":", ""));
                tvyeardialog.setText(formatter3.format(firstDayOfNewMonth).toUpperCase().replace(".", "").replace(":", ""));
                yearString = tvyeardialog.getText().toString();
                monthString = formatter4.format(firstDayOfNewMonth).toUpperCase().replace(".", "").replace(":", "");
                dayString = tvdaydialog.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (dateFormat.format(firstDayOfNewMonth).equals(dateFormat.format(cDate))) {
                    calendarView.setCurrentDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black_overlay));
                } else {
                    calendarView.setCurrentDayBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.greydark));
                }
            }
        });
    }

    private void changeDate() {
        tvdate.setText(dayString + "-" + monthString + "-" + yearString);
    }

    private class addConfig extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            return 0;
        }

        protected void onPostExecute(Object result) {
            SQLiteDatabase db = usdbh.getWritableDatabase();
            String country = spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString();
            String city = spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString();
            String locality = splocality.getItemAtPosition(splocality.getSelectedItemPosition()).toString();
            String date = tvdate.getText().toString().trim();
            usdbh.addConfiguration(db,country,city,locality,date);
            dialogprogress.dismiss();
            Intent i = new Intent(ConfigActivity.this,PrincipalActivity.class);
            i.putExtra("COUNTRY",country);
            i.putExtra("CITY",city);
            i.putExtra("LOCALITY",locality);
            i.putExtra("DATE",date);
            startActivity(i);
        }
    }

    private class Configselect extends ConfigSelected
    {
        private ProgressDialog progressDialog = new ProgressDialog(ConfigActivity.this);

        public Configselect(ConfigSelectedCallback callback) {
            super(callback);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Config config) {
            super.onPostExecute(config);
            progressDialog.dismiss();
        }
    }
}
