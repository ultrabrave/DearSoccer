package cl.enexo.dearsoccer.background;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

import cl.enexo.dearsoccer.views.config.CallbackPlaces;
import cl.enexo.dearsoccer.views.createMatch.CallbackDuration;

/**
 * Created by Kevin on 30-01-2017.
 */

public class GetSpinnersDurations extends AsyncTask<Object, Void, Object[]> {
    CallbackDuration callbackDuration;

    public GetSpinnersDurations(CallbackDuration callbackDuration) {
        this.callbackDuration = callbackDuration;
    }

    @Override
    protected Object[] doInBackground(Object... objects) {
        Resources re = (Resources) objects[0];
        String duration = (String) objects[1];
        Context context = (Context) objects[2];
        String[] stringDuration=null;
        int resId = 0;
        resId=re.getIdentifier("duration_array", "array", context.getPackageName());
        stringDuration = re.getStringArray(resId);
        Object[] obj = new Object[] { stringDuration,duration};
        return obj;
    }

    @Override
    protected void onPostExecute(Object[] objects) {
        String[] durations = (String[]) objects[0];
        String duration = (String) objects[1];
        List<String> listDuration = null;
        if (durations != null)
        {
            listDuration = Arrays.asList(durations);
        }
        callbackDuration.okDuration(listDuration,duration);
    }
}
