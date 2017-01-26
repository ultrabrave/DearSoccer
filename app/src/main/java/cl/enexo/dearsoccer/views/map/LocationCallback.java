package cl.enexo.dearsoccer.views.map;

/**
 * Created by Kevin on 08-01-2017.
 */

public interface LocationCallback {
    void noAddress();
    void fail();
    void success(String lat, String lng);
}
