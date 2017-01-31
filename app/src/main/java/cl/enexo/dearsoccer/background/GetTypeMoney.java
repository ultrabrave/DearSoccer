package cl.enexo.dearsoccer.background;
import android.os.AsyncTask;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import cl.enexo.dearsoccer.views.createMatch.CallbackTypeMoney;
/**
 * Created by Kevin on 30-01-2017.
 */

public class GetTypeMoney extends AsyncTask<String, Void, String> {
        DatabaseReference db;
        CallbackTypeMoney callbackTypeMoney;
        public GetTypeMoney(CallbackTypeMoney callbackTypeMoney) {
        this.callbackTypeMoney = callbackTypeMoney;
    }

    @Override
    protected String doInBackground(String... strings) {
        final String[] money = {""};
        String country = strings[0];
        db = FirebaseDatabase.getInstance().getReference().child("country").child(country).child("money");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                money[0] = dataSnapshot.getValue().toString();
                callbackTypeMoney.okMoney(money[0]);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                money[0] = dataSnapshot.getValue().toString();
                callbackTypeMoney.okMoney(money[0]);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                money[0] = dataSnapshot.getValue().toString();
                callbackTypeMoney.okMoney(money[0]);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                money[0] = dataSnapshot.getValue().toString();
                callbackTypeMoney.okMoney(money[0]);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return money[0];
    }
}
