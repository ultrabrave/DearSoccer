package cl.enexo.dearsoccer.views.createMatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cl.enexo.dearsoccer.BD.Bd;
import cl.enexo.dearsoccer.views.initPage.MainActivity;
import cl.enexo.dearsoccer.views.listMatchs.principal.PrincipalActivity;
import cl.enexo.dearsoccer.views.map.MapsActivity;
import cl.enexo.dearsoccer.R;

import static cl.enexo.dearsoccer.R.id.linear_config;

/**
 * Created by Kevin on 06-12-2016.
 */

public class NewMatchActivity extends Activity implements CallBackNewMatch{
    private Button btncalendar;
    private Dialog dialog;
    private CompactCalendarView calendarView;
    private TextView tvmonth;
    private TextView tvmonthdialog;
    private TextView tvdaydialog;
    private TextView tvyeardialog;
    private TextView btncancelcalendar;
    private TextView btnokcalendar;
    private GoogleMap mMap;
    private int year;
    private String yearString;
    private int month;
    private String monthString;
    private int day;
    private String dayString;
    private TextView tvdate;
    public TextView tvhournew;
    private Button btnmap;
    private EditText tvnamenew;
    private TextView tvplacenew;
    private TextView tvdurationnew;
    private TextView tvtypenewmatch;
    private TextView tvpricenew;
    private TextView tvtypenew;
    private EditText tvdescriptionnew;
    private Button btnduration;
    private Button btnprice;
    private TextView tvprice;
    EditText etokprice;
    DatabaseReference db;
    private Button btntypenewmatch;
    private Button btncreatenewmatch;
    private AlertDialog.Builder builder;
    private TextView tvlatnewmatch;
    private TextView tvlonnewmatch;
    private Double lat;
    private Double lon;
    Bd usdbh = new Bd(NewMatchActivity.this, "DB_DEARSOCCER", null, 1);
    private Spinner spcountry;
    private Spinner spcity;
    private Spinner splocality;
    String areaName = "";
    String areaName2 = "";
    String areaName3 = "";
    LinearLayout linear_new;
    LinearLayout l1;
    LinearLayout l2;
    private int ifconfig = 0;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NewMatchActivity.this,PrincipalActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);

        spcountry = (Spinner) findViewById(R.id.spcountrynewmatch);
        spcity = (Spinner) findViewById(R.id.spcitynewmatch);
        splocality = (Spinner) findViewById(R.id.splocalitynewmatch);

        linear_new = (LinearLayout) findViewById(R.id.linear_loading_new);
        l1 = (LinearLayout) findViewById(R.id.linear_screen_new);
        l2 = (LinearLayout) findViewById(R.id.linear_loading_new);

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


        tvnamenew = (EditText) findViewById(R.id.etnamenewmatch);
        tvhournew = (TextView) findViewById(R.id.tvhournew);
        tvdate = (TextView) findViewById(R.id.tvdate);
        tvplacenew = (TextView) findViewById(R.id.tvplacenewmatch);
        tvdurationnew = (TextView) findViewById(R.id.tvdurationnewmatch);
        tvtypenewmatch= (TextView) findViewById(R.id.tvtypenewmatch);
        tvpricenew = (TextView) findViewById(R.id.tvpricenewmatch);
        tvtypenew = (TextView) findViewById(R.id.tvtypenewmatch);
        tvdescriptionnew = (EditText) findViewById(R.id.etcomentnewmatch);
        btnduration = (Button) findViewById(R.id.btndurationnewmatch);
        btnprice = (Button) findViewById(R.id.btnpricenewmatch);
        btntypenewmatch = (Button) findViewById(R.id.btntypenewmatch);
        btncreatenewmatch =(Button) findViewById(R.id.btncreatenewmatch);
        tvlatnewmatch =(TextView) findViewById(R.id.tvlatnewmatch);
        tvlonnewmatch =(TextView) findViewById(R.id.tvlonnewmatch);

        btncreatenewmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new sendData().execute();
            }
        });

        btntypenewmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogType();
                dialog.show();
            }
        });

        btnprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogPrice();
                dialog.show();

            }
        });

        btnduration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogDuration();
                dialog.show();
            }
        });

        Intent intent = getIntent();
        String n = intent.getStringExtra("NAME");
        String d = "";
        d = intent.getStringExtra("DATE");
        String h = intent.getStringExtra("HOUR");
        String p = intent.getStringExtra("PLACE");
        String du = intent.getStringExtra("DURATION");
        String pr = intent.getStringExtra("PRICE");
        String t = intent.getStringExtra("TYPE");
        String de = intent.getStringExtra("DESCRIPTION");
        String la = intent.getStringExtra("LAT");
        String lo = intent.getStringExtra("LON");
        if (n == null) {
            tvnamenew.setText("");
        } else {
            tvnamenew.setText(n);
        }

        if (d == null || d.length() < 0) {
            tvdate.setText("");
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = (month + 1) % 12;
            if (month < 10) {
                monthString = "0" + month;
            } else {
                monthString = String.valueOf(month);
            }
            if (day < 10) {
                dayString = "0" + day;
            } else {
                dayString = String.valueOf(day);
            }
            yearString = String.valueOf(year);
            changeDate();
        } else {
            tvdate.setText(d);
        }

        if (h == null) {
            tvhournew.setText("");
        } else {
            tvhournew.setText(h);
        }

        if (p == null) {
            tvplacenew.setText("");
        } else {
            tvplacenew.setText(p);
        }

        if (du == null) {
            tvdurationnew.setText("");
        } else {
            tvdurationnew.setText(du);
        }

        if (pr == null) {
            tvpricenew.setText("");
        } else {
            tvpricenew.setText(pr);
        }

        if (t == null) {
            tvtypenew.setText("");
        } else {
            tvtypenew.setText(t);
        }

        if (de == null) {
            tvdescriptionnew.setText("");
        } else {
            tvdescriptionnew.setText(de);
        }

        if (la == null || la.equals("")) {
            lat = 0.0;
        } else {
            lat = Double.parseDouble(la);
        }

        if (lo == null || lo.equals("")) {
            lon = 0.0;
        } else {
            lon = Double.parseDouble(lo);
        }




        btncalendar = (Button) findViewById(R.id.btndate);
        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
                dialog.show();
            }
        });

        btnmap = (Button) findViewById(R.id.btnmapnewmatch);
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                String name = tvnamenew.getText().toString();
                String date = tvdate.getText().toString();
                String hour = tvhournew.getText().toString();
                String place = tvplacenew.getText().toString();
                String duration = tvdurationnew.getText().toString();
                String price = tvpricenew.getText().toString();
                String type = tvtypenew.getText().toString();
                String description = tvdescriptionnew.getText().toString();
                String lat = tvlatnewmatch.getText().toString();
                String lon = tvlonnewmatch.getText().toString();
                String country = spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString();
                String city = spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString();
                String locality = splocality.getItemAtPosition(splocality.getSelectedItemPosition()).toString();

                i.putExtra("NAME", name);
                i.putExtra("DATE", date);
                i.putExtra("HOUR", hour);
                i.putExtra("PLACE", place);
                i.putExtra("DURATION", duration);
                i.putExtra("PRICE", price);
                i.putExtra("TYPE", type);
                i.putExtra("DESCRIPTION", description);
                i.putExtra("LAT", lat);
                i.putExtra("LON", lon);
                i.putExtra("COUNTRY",country);
                i.putExtra("CITY",city);
                i.putExtra("LOCALITY",locality);
                startActivity(i);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ifconfig != 1) {
                    if (l2.getLayoutParams().width != 0) {
                        Toast.makeText(NewMatchActivity.this, "Sin acceso a red, favor reintentar", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(NewMatchActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            }
        },10000);

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



    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        private TextView tvhournewinhour;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), TimePickerDialog.THEME_TRADITIONAL, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hour;
            String minu;
            if (hourOfDay < 10) {
                hour = "0" + hourOfDay;
            } else {
                hour = String.valueOf(hourOfDay);
            }

            if (minute < 10) {
                minu = "0" + minute;
            } else {
                minu = String.valueOf(minute);
            }

            tvhournewinhour = (TextView) getActivity().findViewById(R.id.tvhournew);
            tvhournewinhour.setText(hour + ":" + minu);
            String hourr = tvhournewinhour.getText().toString();
            hourr = hourr.replace("-", ":");
            tvhournewinhour.setText(hourr);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }


    private void setDialogDuration() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_duration);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final Spinner spduration = (Spinner) dialog.findViewById(R.id.sp_duration_match);
        LinkedList listduration = new LinkedList();
        listduration.add(new MyObjectClass(1, "1 Hour"));
        listduration.add(new MyObjectClass(2, "2 Hours"));
        listduration.add(new MyObjectClass(3, "3 Hours"));
        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listduration);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spduration.setAdapter(spinner_adapter);
        spduration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button okduration = (Button) dialog.findViewById(R.id.btn_ok_duration);
        okduration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spduration.getItemAtPosition(spduration.getSelectedItemPosition());
                MyObjectClass s = (MyObjectClass) spduration.getItemAtPosition(spduration.getSelectedItemPosition());
                int duration = s.getId();
                tvdurationnew.setText(duration + " hour(s)");
                dialog.dismiss();
            }
        });
    }

    public class MyObjectClass {
        int id;
        String name;

        //Constructor
        public MyObjectClass(int id, String nombre) {
            super();
            this.id = id;
            this.name = nombre;
        }

        @Override
        public String toString() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    public void setDialogPrice() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_price);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        String thecountry = spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString();
        tvprice = (TextView) dialog.findViewById(R.id.tv_moneynew_match);
        etokprice = (EditText) dialog.findViewById(R.id.et_price_new_match);

        Button btnokdialogprice = (Button) dialog.findViewById(R.id.btn_ok_price);
        db = FirebaseDatabase.getInstance().getReference().child("country").child(thecountry).child("money");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String money = dataSnapshot.getValue().toString();
                tvprice.setText(money);
                dialog.show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String money = dataSnapshot.getValue().toString();
                tvprice.setText(money);
                dialog.show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String money = dataSnapshot.getValue().toString();
                tvprice.setText(money);
                dialog.show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnokdialogprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvpricenew.setText(etokprice.getText().toString() + " " + tvprice.getText().toString());
                dialog.dismiss();
            }
        });
    }

    private void setDialogType() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_type);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final Spinner sptype = (Spinner) dialog.findViewById(R.id.sp_type_match);
        LinkedList listduration = new LinkedList();
        listduration.add(new MyObjectClass(1, "Futbol"));
        listduration.add(new MyObjectClass(2, "Futbolito 8"));
        listduration.add(new MyObjectClass(3, "Futbolito 7"));
        listduration.add(new MyObjectClass(4, "Futbolito 6"));
        listduration.add(new MyObjectClass(5, "Baby futbol"));
        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listduration);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptype.setAdapter(spinner_adapter);
        sptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button oktype = (Button) dialog.findViewById(R.id.btn_ok_type);
        oktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sptype.getItemAtPosition(sptype.getSelectedItemPosition());
                MyObjectClass s = (MyObjectClass) sptype.getItemAtPosition(sptype.getSelectedItemPosition());
                String types = s.toString();
                tvtypenewmatch.setText(types);
                dialog.dismiss();
            }
        });
    }

    protected void muestramensaje(String Msg, final String Titulo){
        builder = new AlertDialog.Builder(this);
        builder.setMessage(Msg)
                .setTitle(Titulo)
                .setCancelable(false)
                .setIcon(R.drawable.logofinal)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Titulo.equals("EXITO"))
                                {
                                    dialog.cancel();
                                    Intent i = new Intent(NewMatchActivity.this,PrincipalActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else
                                {
                                    dialog.cancel();
                                }
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void ok() {
        muestramensaje("Partido creado con éxito","EXITO");
    }

    @Override
    public void errorData() {
        muestramensaje("Debe llenar todos los datos solicitados","ERROR");
    }

    @Override
    public void failNet() {
        muestramensaje("Error de conexión ,favor revisar internet","ERROR");
    }


    private class sendData extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            return 0;
        }

        protected void onPostExecute(Object result) {
            SQLiteDatabase db = usdbh.getWritableDatabase();
            String location = tvdate.getText().toString();
            String year = "";
            String month ="";
            String day = "";

            try {
                String[] loc = location.split("-");
                year = loc[2];
                month = loc[1];
                day = loc[0];
            }
            catch (Exception e)
            {
                year = "";
                month ="";
                day = "";
                e.getMessage();
            }
            String co = spcountry.getItemAtPosition(spcountry.getSelectedItemPosition()).toString();
            String ci = spcity.getItemAtPosition(spcity.getSelectedItemPosition()).toString();
            String lo = splocality.getItemAtPosition(splocality.getSelectedItemPosition()).toString();
            String durstring= "";
            try {
                String[] dur = tvdurationnew.getText().toString().split(" ");
                durstring = dur[0];
            }
            catch (Exception e)
            {
                durstring = "";
            }

            new ValidationNewMatch(NewMatchActivity.this).addMatch(tvnamenew.getText().toString(),tvdate.getText().toString(),tvhournew.getText().toString(),tvplacenew.getText().toString(),lat.toString(),lon.toString(),durstring,tvpricenew.getText().toString(),tvtypenew.getText().toString(),tvdescriptionnew.getText().toString(),year,month,day,co,ci,lo);
        }
    }

    private class SpinnerCountrys extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return 0;
        }

        protected void onPostExecute(Object result) {
            SQLiteDatabase db = usdbh.getWritableDatabase();
            ArrayAdapter<String> adapcli = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, usdbh.getCountrys(db));
            spcountry.setAdapter(adapcli);
            Intent i = getIntent();
            if (i.getStringExtra("COUNTRY").equals("") || i.getStringExtra("COUNTRY") == null)
            {
                String country = usdbh.getCountrySelected(db);
                int spinnerPosition = adapcli.getPosition(country);
                spcountry.setSelection(spinnerPosition);
            }
            else
            {
                int spinnerPosition = adapcli.getPosition(i.getStringExtra("COUNTRY"));
                spcountry.setSelection(spinnerPosition);
            }
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
            ArrayAdapter<String> adapcli = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, usdbh.getCities(db,dato));
            spcity.setAdapter(adapcli);

            Intent i = getIntent();
            if (i.getStringExtra("CITY").equals("") || i.getStringExtra("CITY") == null)
            {
                String city = usdbh.getCitySelected(db);
                int spinnerPosition = adapcli.getPosition(city);
                spcity.setSelection(spinnerPosition);
            }
            else
            {
                int spinnerPosition = adapcli.getPosition(i.getStringExtra("CITY"));
                spcity.setSelection(spinnerPosition);
            }

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
            ArrayAdapter<String> adapcli = new ArrayAdapter<String>(NewMatchActivity.this, android.R.layout.simple_list_item_checked, usdbh.getLocalities(db,dato));
            splocality.setAdapter(adapcli);

            Intent i = getIntent();
            if (i.getStringExtra("LOCALITY").equals("") || i.getStringExtra("LOCALITY") == null)
            {
                String locality = usdbh.getLocalitySelected(db);
                int spinnerPosition = adapcli.getPosition(locality);
                splocality.setSelection(spinnerPosition);
            }
            else
            {
                int spinnerPosition = adapcli.getPosition(i.getStringExtra("LOCALITY"));
                splocality.setSelection(spinnerPosition);
            }
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            l1.setLayoutParams(params1);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    0, 0);
            l2.setLayoutParams(params2);
        }
    }
}
