package cl.enexo.dearsoccer.models;

import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import cl.enexo.dearsoccer.views.InformationMatch.CallbackValidationParticipe;

/**
 * Created by Kevin on 30-01-2017.
 */

public class DataIntentinfoMatch {
    Intent intent;
    TextView tvname,tvplace,tvcountintegrants,tvduration,tvintegrants,tvcreator,tvhour,tvprice,tvdate,tvdescription;
    CallbackValidationParticipe callbackValidationParticipe;

    public DataIntentinfoMatch(CallbackValidationParticipe callbackValidationParticipe,Intent intent, TextView tvname, TextView tvplace, TextView tvcountintegrants, TextView tvduration, TextView tvintegrants, TextView tvcreator, TextView tvhour, TextView tvprice, TextView tvdate, TextView tvdescription) {
        this.callbackValidationParticipe = callbackValidationParticipe;
        this.intent = intent;
        this.tvname = tvname;
        this.tvplace = tvplace;
        this.tvcountintegrants = tvcountintegrants;
        this.tvduration = tvduration;
        this.tvintegrants = tvintegrants;
        this.tvcreator = tvcreator;
        this.tvhour = tvhour;
        this.tvprice = tvprice;
        this.tvdate = tvdate;
        this.tvdescription = tvdescription;
    }

    public void assignData()
    {
        tvname.setText("Nombre: " + intent.getStringExtra("NAME"));
        tvplace.setText("Lugar: " + intent.getStringExtra("PLACE"));
        tvcountintegrants.setText(intent.getStringExtra("COUNT") + " Jugador(es)");
        tvduration.setText("Duración: " + intent.getStringExtra("DURATION") + " hrs");
        tvintegrants.setText("Integrantes \n " + intent.getStringExtra("INTEGRANTS"));
        tvcreator.setText("Creador: " + intent.getStringExtra("CREATOR"));
        tvhour.setText("Hora partido: " + intent.getStringExtra("HOUR"));
        tvprice.setText("Valor por jugador: " + intent.getStringExtra("PRICE"));
        tvdate.setText("Fecha partido: " + intent.getStringExtra("DATE"));
        tvdescription.setText("Descripción: " + intent.getStringExtra("DESCRIPTION"));
    }

    public void validateMail(){
        Boolean exists = false;
        String inte = intent.getStringExtra("INTEGRANTS");
        String[] integrantss =  inte.trim().split("\n");
        for (int i = 0; i < integrantss.length; i++) {
            String[] mails = integrantss[i].split("-");
            if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(mails[1]))
            {
                exists = true;
            }
        }
        if (exists ==  true)
        {
            callbackValidationParticipe.inMatch();
        }
        else
        {
            String name = intent.getStringExtra("NAME");
            String place = intent.getStringExtra("PLACE");
            String duration = intent.getStringExtra("DURATION");
            String count = intent.getStringExtra("COUNT");
            String integrants = intent.getStringExtra("INTEGRANTS");
            String creator = intent.getStringExtra("CREATOR");
            String idsintegrants =  intent.getStringExtra("IDSINTEGRANTS");
            integrants = integrants +FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "-" + FirebaseAuth.getInstance().getCurrentUser().getEmail() +  "\n";
            idsintegrants = idsintegrants + FirebaseAuth.getInstance().getCurrentUser().getUid() + ";";
            int countint = Integer.parseInt(count);
            countint = countint + 1;
            String timestamps = intent.getStringExtra("TIMESTAMPS");
            String[] ids = idsintegrants.split(";");
            String[] datearray = intent.getStringExtra("DATE").split("-");
            String year = datearray[2];
            String month = datearray[1];
            String day = datearray[0];
            Match match = new Match(intent.getStringExtra("IDMATCH"),name,place,duration,integrants,String.valueOf(countint),timestamps,creator,idsintegrants,intent.getStringExtra("LAT"),intent.getStringExtra("LON"),year,month,day,intent.getStringExtra("DATE"),intent.getStringExtra("HOUR"),intent.getStringExtra("COUNTRY"),intent.getStringExtra("CITY"),intent.getStringExtra("LOCALITY"),intent.getStringExtra("PRICE"),intent.getStringExtra("TYPE"),intent.getStringExtra("DESCRIPTION"));
            callbackValidationParticipe.toParticipe(match,ids);
        }
    }
}
