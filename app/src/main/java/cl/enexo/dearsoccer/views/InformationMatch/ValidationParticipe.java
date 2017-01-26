package cl.enexo.dearsoccer.views.InformationMatch;

import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import cl.enexo.dearsoccer.models.Match;

/**
 * Created by Kevin on 25-01-2017.
 */

public class ValidationParticipe {
    private int error = 0;
    private ParticipeCallback callback;

    public ValidationParticipe(ParticipeCallback callback) {
        this.callback = callback;
    }

    public void participeMatch(Match mymatch,String[] ids)
    {
        try {
            FirebaseDatabase.getInstance().getReference().child("match_creator").child("user").child(ids[0] + ";").child(mymatch.idmatch).setValue(mymatch);

            for (int i = 1; i < ids.length; i++) {
                FirebaseDatabase.getInstance().getReference().child("match_participer").child("user").child(ids[i] + ";").child("match").child(mymatch.idmatch).setValue(mymatch);
            }

            FirebaseDatabase.getInstance().getReference().child("match").child("country").child(mymatch.country).child("city").child(mymatch.city).child("locality").child(mymatch.locality).child("year").child(mymatch.year).child("month").child(mymatch.month).child("day").child(mymatch.day).child("public").child("match").child(mymatch.idmatch).setValue(mymatch).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    callback.ok();
                }
            });
        }
        catch (Exception e)
        {
            e.getMessage();
            callback.failNetwork();
        }
    }


}
