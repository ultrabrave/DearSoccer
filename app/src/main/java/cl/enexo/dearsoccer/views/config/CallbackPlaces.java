package cl.enexo.dearsoccer.views.config;

import java.util.List;

import cl.enexo.dearsoccer.models.Config;

import static android.R.attr.country;

/**
 * Created by Kevin on 27-01-2017.
 */

public interface CallbackPlaces {
    void noPlaces();
    void okPlaces(List<String> countries, List<String> cities, List<String> localities,String country,String city,String locality,String from);
}
