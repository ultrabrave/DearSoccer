package cl.enexo.dearsoccer.views.listMatchs.fragments.toplay;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.adapters.AdapterToPlay;
import cl.enexo.dearsoccer.adapters.ToPlayHolder;
import cl.enexo.dearsoccer.models.Match;
/**
 * Created by Kevin on 31-01-2017.
 */

public class GetToPlay {
    RecyclerView recyclerView;
    Context context;
    Activity activity;

    public GetToPlay(RecyclerView recyclerView, Context context, Activity activity) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.activity =  activity;
    }

    public void getInformation()
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference  = database.getReference().child("match_participer").child(auth.getCurrentUser().getUid()+";");
        AdapterToPlay adapter = new AdapterToPlay(Match.class, R.layout.item_toplay, ToPlayHolder.class, reference,activity);
        recyclerView.setAdapter(adapter);

    }

}
