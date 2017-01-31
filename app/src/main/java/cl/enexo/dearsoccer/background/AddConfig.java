package cl.enexo.dearsoccer.background;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cl.enexo.dearsoccer.BD.Bd;
import cl.enexo.dearsoccer.views.config.CallbackAddConfig;
import cl.enexo.dearsoccer.views.config.CallbackPlaces;
import cl.enexo.dearsoccer.views.config.ConfigActivity;

import static android.R.string.ok;

/**
 * Created by Kevin on 28-01-2017.
 */

public class AddConfig extends AsyncTask<Object, Void, Boolean> {
    CallbackAddConfig callbackAddConfig;
    Context context;

    public AddConfig(CallbackAddConfig callbackAddConfig,Context context) {
        this.callbackAddConfig = callbackAddConfig;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        Boolean result = false;
        try {
            String country = (String) objects[0];
            String city = (String) objects[1];
            String locality = (String) objects[2];
            String dateIni = (String) objects[3];
            String datefin = (String) objects[4];
            String[] splitini = dateIni.split("-");
            String dayini = splitini[0];
            String monthini = splitini[1];
            String yearini = splitini[2];
            String[] splitfin = datefin.split("-");
            String dayfin = splitfin[0];
            String monthfin = splitfin[1];
            String yearfin = splitfin[2];
            String date = yearini+"-"+monthini+"-"+dayini;
            String datef = yearfin+"-"+monthfin+"-"+dayfin;
            Bd usdbh = new Bd(context, "DB_DEARSOCCER", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            usdbh.addConfiguration(db,country,city,locality,date,datef);
            result = true;
        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean == true)
        {
            callbackAddConfig.okAdd();
        }
        else
        {
            callbackAddConfig.failAdd();
        }
    }
}
