package cl.enexo.dearsoccer.background;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

import cl.enexo.dearsoccer.views.config.CallbackPlaces;
import cl.enexo.dearsoccer.views.createMatch.CallbackTypes;

import static android.R.attr.country;

/**
 * Created by Kevin on 30-01-2017.
 */

public class GetSpinnerTypes extends AsyncTask<Object, Void, Object[]> {
    CallbackTypes callbackTypes;

    public GetSpinnerTypes(CallbackTypes callbackTypes) {
        this.callbackTypes = callbackTypes;
    }

    @Override
    protected Object[] doInBackground(Object... objects) {
        Resources re = (Resources) objects[0];
        String type = (String) objects[1];
        Context context = (Context) objects[2];
        String[] stringTypes=null;
        int resId = 0;
        resId=re.getIdentifier("types_array", "array", context.getPackageName());
        stringTypes = re.getStringArray(resId);
        Object[] obj = new Object[] { stringTypes,type};
        return obj;
    }

    @Override
    protected void onPostExecute(Object[] objects) {
        String[] types = (String[]) objects[0];
        String type = (String) objects[1];
        List<String> listTypes = null;
        if (types != null)
        {
            listTypes = Arrays.asList(types);
        }
        callbackTypes.okTypes(listTypes,type);
    }
}