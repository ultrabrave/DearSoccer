package cl.enexo.dearsoccer.background;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import java.util.Arrays;
import java.util.List;
import cl.enexo.dearsoccer.views.config.CallbackPlaces;

/**
 * Created by Kevin on 27-01-2017.
 */
    public class GetSpinnersPlaces extends AsyncTask<Object, Void, Object[]> {
    CallbackPlaces callbackPlaces;

    public GetSpinnersPlaces(CallbackPlaces callbackPlaces) {
        this.callbackPlaces = callbackPlaces;
    }

    @Override
    protected Object[] doInBackground(Object... objects) {
        Resources re = (Resources) objects[0];
        String country = (String) objects[1];
        String city = (String) objects[2];
        String locality = (String) objects[3];
        Context context = (Context) objects[4];
        String from = (String) objects[5];
        String searchCountry = "";
        String searchCity = "";

        int resId = 0;
        String[] stringCountries=null;
        if(country.equals(""))
        {
        resId=re.getIdentifier("countries_array", "array", context.getPackageName());
        stringCountries = re.getStringArray(resId);
            country = stringCountries[0];
        }
        else
        {
            resId=re.getIdentifier("countries_array", "array", context.getPackageName());
            stringCountries = re.getStringArray(resId);
            country = stringCountries[0];
        }

        String[] stringCities = null;
        if(city.equals("")) {
            searchCountry = country.replace(" ", "_").trim() + "_array";
            resId = re.getIdentifier(searchCountry, "array", context.getPackageName());
            stringCities = re.getStringArray(resId);
        }
        else
        {
            searchCountry = country.replace(" ", "_").trim() + "_array";
            resId = re.getIdentifier(searchCountry, "array", context.getPackageName());
            stringCities = re.getStringArray(resId);
        }

        if (city.equals(""))
        {
            city = stringCities[0];
        }
        searchCity =  city.replace(" ","_").trim() + "_array";
        resId=re.getIdentifier(searchCity, "array", context.getPackageName());
        String[] stringLocalities = re.getStringArray(resId);
        Object[] obj = new Object[] { stringCountries, stringCities, stringLocalities, country,city,locality, from};
        return obj;
    }

    @Override
    protected void onPostExecute(Object[] objects) {
        try {
            String[] countries = (String[]) objects[0];
            List<String> listcountries = null;
            if (countries != null)
            {
                listcountries = Arrays.asList(countries);
            }

            String[] cities = (String[]) objects[1];
            List<String> listcities = null;
            if (cities != null)
            {
                listcities = Arrays.asList(cities);
            }
            String[] localities = (String[]) objects[2];
            List<String> listlocalities = Arrays.asList(localities);
            String country = (String) objects[3];
            String city = (String) objects[4];
            String locality = (String) objects[5];
            String from = (String) objects[6];
            callbackPlaces.okPlaces(listcountries, listcities, listlocalities, country, city, locality,from);
        }
        catch (Exception e)
        {
            callbackPlaces.noPlaces();
        }
    }
}
