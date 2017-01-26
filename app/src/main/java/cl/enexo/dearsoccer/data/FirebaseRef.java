package cl.enexo.dearsoccer.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Kevin on 29-11-2016.
 */

public class FirebaseRef {
    private DatabaseReference root(){
        return FirebaseDatabase.getInstance().getReference();
    }
    public DatabaseReference userMatch()
    {
        return root().child("usermatch");
    }
    public DatabaseReference Group(){
        return root().child("usermatch").child("mymatch");
    }
    public DatabaseReference Match(){
        return root().child("match");
    }
    public DatabaseReference Players(){
        return root().child("match").child("Players");
    }
    public DatabaseReference Countrys(){
        return root().child("country");
    }

}
