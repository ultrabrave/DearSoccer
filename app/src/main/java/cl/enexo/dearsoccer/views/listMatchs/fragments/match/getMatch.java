package cl.enexo.dearsoccer.views.listMatchs.fragments.match;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.adapters.AdapterMatch;
import cl.enexo.dearsoccer.adapters.MatchHolder;
import cl.enexo.dearsoccer.models.Config;
import cl.enexo.dearsoccer.models.Match;
/**
 * Created by Kevin on 30-01-2017.
 */

public class GetMatch {
    RecyclerView recyclerView;
    Context context;
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;
    Activity activity;

    public GetMatch(RecyclerView recyclerView, Context context, CoordinatorLayout coordinatorLayout, ProgressBar progressBar, Activity activity) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
        this.progressBar = progressBar;
        this.activity =  activity;
    }

    public void getInformation(Config config)
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference  = database.getReference().child(config.getLocality());
        Query queryref = reference.startAt(config.getDay()+"-"+config.getMonth()+"-"+config.getYear()).endAt(config.getDayfinal()+"-"+config.getMonthfinal()+"-"+config.getYearfinal()).orderByChild("date");
        AdapterMatch adapter = new AdapterMatch(Match.class, R.layout.item_match, MatchHolder.class, queryref,activity);
        recyclerView.setAdapter(adapter);
        coordinatorLayout.removeView(progressBar);
     
    }
}
