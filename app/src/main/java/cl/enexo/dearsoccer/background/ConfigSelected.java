package cl.enexo.dearsoccer.background;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import cl.enexo.dearsoccer.BD.Bd;
import cl.enexo.dearsoccer.views.config.ConfigSelectedCallback;
import cl.enexo.dearsoccer.models.Config;

/**
 * Created by Kevin on 24-01-2017.
 */

public class ConfigSelected extends AsyncTask<Context, Void, Config> {
    private ConfigSelectedCallback callback;
    public ConfigSelected(ConfigSelectedCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Config doInBackground(Context... contexts) {
        Context mycontext = null;
        Config config = null;
        try {

            for (Context context : contexts) {
                mycontext = context;
            }
            Bd usdbh = new Bd(mycontext, "DB_DEARSOCCER", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            String country = usdbh.getCountrySelected(db);
            String city = usdbh.getCitySelected(db);
            String locality = usdbh.getLocalitySelected(db);
            String date = usdbh.getDateSelected(db);
            String[] datesplit = date.split("-");
            String year = datesplit[0];
            String month = datesplit[1];
            String day = datesplit[2];
            config = new Config(country, city, locality, year, month, day);
        }
        catch (Exception e)
        {
            config = null;
            e.getMessage();
        }
        return config;
    }

    @Override
    protected void onPostExecute(Config config) {
        if (config != null) {
            callback.ok(config);
        }
        else
        {
            callback.noConfig();
        }
    }
}
