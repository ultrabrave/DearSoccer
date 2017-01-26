package cl.enexo.dearsoccer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import cl.enexo.dearsoccer.BD.Bd;
import cl.enexo.dearsoccer.models.Money;
import cl.enexo.dearsoccer.views.config.ConfigActivity;

/**
 * Created by Kevin on 11-01-2017.
 */

public class FirebaseHelper {
    DatabaseReference db;
    Context context;
    Boolean saved=null;
    String money = null;
    FirebaseRef ref;
    Bd usdbh;

    public FirebaseHelper(DatabaseReference db, Context context) {
        this.db = db;
        this.context =context;
    }

    public ArrayAdapter<String> getCountrys()
    {
        ArrayAdapter<String> adapter = null;
        ref = new FirebaseRef();
        usdbh = new Bd(context, "DB_DEARSOCCER", null, 1);

        DatabaseReference db = ref.Countrys();
        db.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            final List<String> areas = new ArrayList<String>();
            String areaName = "";

            for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                areaName = areaSnapshot.getKey().toString();
                areas.add(areaName);
                SQLiteDatabase db = usdbh.getWritableDatabase();
                usdbh.addCountry(db,areaName);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
                return adapter;
    }

    public Boolean save(Money money) {
        if (money == null)
        {
            saved = false;
        }
        else
        {
            try
            {

            }
            catch (Exception e)
            {
                e.getMessage();
            }
        }
        return saved;
    }

    public String retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                theData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return money;
    }

    private void theData(DataSnapshot snapshot)
    {
        money = snapshot.getValue().toString();
    }

}

