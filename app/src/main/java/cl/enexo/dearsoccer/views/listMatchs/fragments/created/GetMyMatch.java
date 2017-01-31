package cl.enexo.dearsoccer.views.listMatchs.fragments.created;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.adapters.AdapterMyMatch;
import cl.enexo.dearsoccer.adapters.CreatedHolder;
import cl.enexo.dearsoccer.models.Match;
/**
 * Created by Kevin on 31-01-2017.
 */
public class GetMyMatch {
    RecyclerView recyclerView;
    Context context;
    Activity activity;

    public GetMyMatch(RecyclerView recyclerView, Context context, Activity activity) {
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
        DatabaseReference reference  = database.getReference().child("match_creator").child(auth.getCurrentUser().getUid()+";");
        AdapterMyMatch adapter = new AdapterMyMatch(Match.class, R.layout.item_my_matches, CreatedHolder.class, reference,activity);
        recyclerView.setAdapter(adapter);
    }
}
