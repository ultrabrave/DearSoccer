package cl.enexo.dearsoccer.network;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Kevin on 08-01-2017.
 */

public interface GeoGet {
    @GET("api/geocode/json")
    Call<JsonObject> get(@QueryMap Map<String, String> map);
}
