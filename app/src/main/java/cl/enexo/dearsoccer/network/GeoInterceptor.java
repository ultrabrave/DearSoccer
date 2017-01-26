package cl.enexo.dearsoccer.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kevin on 08-01-2017.
 */

public class GeoInterceptor {
    private static final String BASE_URL = "http://maps.googleapis.com/maps/";
    public GeoGet getInterceptor() {
        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GeoGet geoGet = interceptor.create(GeoGet.class);
        return geoGet;
    }
}
