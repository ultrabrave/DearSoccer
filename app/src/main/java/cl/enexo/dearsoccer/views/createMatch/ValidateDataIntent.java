package cl.enexo.dearsoccer.views.createMatch;

/**
 * Created by Kevin on 25-01-2017.
 */

public class ValidateDataIntent {
    private CallBackDataIntent callBackDataIntent;

    public ValidateDataIntent(CallBackDataIntent callBackDataIntent) {
        this.callBackDataIntent = callBackDataIntent;
    }

    public void validateData(String name,String date,String hour,String place, String lat, String lon,String duration,String price,String type,String description) {
       /* String pr = intent.getStringExtra("PRICE");
        String t = intent.getStringExtra("TYPE");
        String de = intent.getStringExtra("DESCRIPTION");

        if (name == null || name.length() == 0) {
            error = 1;
        }
        if (date == null || date.length() == 0) {
            error = 1;
        }
        if (hour == null || hour.length() == 0) {
            error = 1;
        }
        if (place == null || place.length() == 0) {
            error = 1;
        }
        if (lat == null || lat.length() == 0) {
            error = 1;
        }
        if (lon == null || lon.length() == 0) {
            error = 1;
        }
        if (duration == null || duration.length() == 0) {
            error = 1;
        }
        if (price == null || price.length() == 0) {
            error = 1;
        }
        if (type == null || type.length() == 0) {
            error = 1;
        }*/
    }
}
