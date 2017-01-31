package cl.enexo.dearsoccer.views.createMatch;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Kevin on 30-01-2017.
 */

public class TypeMoney {
    CallbackTypeMoney callbackTypeMoney;
    String country;

    public TypeMoney(CallbackTypeMoney callbackTypeMoney, String country) {
        this.callbackTypeMoney = callbackTypeMoney;
        this.country = country;
    }

    public void obtainTypeMoney()
    {
        final String[] money = {""};
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("country").child(country).child("money");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                money[0] = dataSnapshot.getValue().toString();
                callbackTypeMoney.okMoney(money[0]);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
