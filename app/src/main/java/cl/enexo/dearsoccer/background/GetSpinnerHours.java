package cl.enexo.dearsoccer.background;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

import cl.enexo.dearsoccer.views.config.CallbackPlaces;
import cl.enexo.dearsoccer.views.createMatch.CallbackHour;

import static android.R.attr.country;

/**
 * Created by Kevin on 30-01-2017.
 */

public class GetSpinnerHours extends AsyncTask<Object, Void, Object[]> {
    CallbackHour callbackHour;

    public GetSpinnerHours(CallbackHour callbackHour) {
        this.callbackHour = callbackHour;
    }

    @Override
    protected Object[] doInBackground(Object... objects) {
        Resources re = (Resources) objects[0];
        String gethour = (String) objects[1];
        String[] hourSplit = gethour.split(":");
        String hour = hourSplit[0];
        String minute = hourSplit[1];
        Context context = (Context) objects[2];
        int resId = 0;
        String[] stringHours=null;
        resId=re.getIdentifier("hour_array", "array", context.getPackageName());
        stringHours = re.getStringArray(resId);
        String[] stringMinute=null;
        resId=re.getIdentifier("minute_array", "array", context.getPackageName());
        stringMinute = re.getStringArray(resId);
        Object[] obj = new Object[] { stringHours, hour, stringMinute, minute};
        return obj;
    }

    @Override
    protected void onPostExecute(Object[] objects) {
            String[] hours = (String[]) objects[0];
            List<String> listhours = null;
            if (hours != null)
            {
                listhours = Arrays.asList(hours);
            }

            String hour = (String) objects[1];

            String[] minutes = (String[]) objects[2];
            List<String> listminutes = null;
            if (minutes != null)
            {
                listminutes = Arrays.asList(minutes);
            }
            String minute = (String) objects[3];
            callbackHour.okHour(listhours,hour,listminutes,minute);
    }
}