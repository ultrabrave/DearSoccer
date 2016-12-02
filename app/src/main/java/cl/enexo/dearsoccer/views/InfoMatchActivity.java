package cl.enexo.dearsoccer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.models.Match;

import static android.R.attr.duration;
import static android.os.Build.VERSION_CODES.M;
import static java.security.AccessController.getContext;

public class InfoMatchActivity extends AppCompatActivity {
    private TextView tvname;
    private TextView tvplace;
    private TextView tvcountintegrants;
    private TextView tvintegrants;
    private TextView tvduration;
    private TextView tvcreator;
    private Button btnunion;
    private String mymail;
    private String myname;
    private String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_match);
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String place = intent.getStringExtra("PLACE");
        String duration = intent.getStringExtra("DURATION");
        String count = intent.getStringExtra("COUNT");
        String integrants = intent.getStringExtra("INTEGRANTS");
        String timestamps = intent.getStringExtra("TIMESTAMPS");
        String creator = intent.getStringExtra("CREATOR");
        final String idmatch = intent.getStringExtra("IDMATCH");

        tvname = (TextView) findViewById(R.id.tv_name_match_info);
        tvplace = (TextView) findViewById(R.id.tv_place_match_info);
        tvduration = (TextView) findViewById(R.id.tv_duration_match_info);
        tvcountintegrants = (TextView) findViewById(R.id.tv_countintegrants_match_info);
        tvintegrants = (TextView) findViewById(R.id.tv_integrants_match_info);
        tvcreator = (TextView) findViewById(R.id.tv_creator_match_info);
        btnunion = (Button) findViewById(R.id.btn_add_match_info);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        mymail = auth.getCurrentUser().getEmail();
        iduser = auth.getCurrentUser().getUid();
        myname =auth.getCurrentUser().getDisplayName();

        tvname.setText("Match: " + name);
        tvplace.setText("Place: " + place);
        tvcountintegrants.setText(count + " Player(s)");
        tvduration.setText("Duration: " + duration + " hrs");
        tvintegrants.setText("Integrants \n " + integrants);
        tvcreator.setText("Create by: " + creator);

        btnunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inte = getIntent().getStringExtra("INTEGRANTS");
                String[] integrantss =  inte.trim().split("\n");
                int exist = 0;
                for (int i = 0; i < integrantss.length; i++) {
                    String[] mails = integrantss[i].split("-");
                    if (mymail.equals(mails[1]))
                    {
                        exist = 1;
                    }
                }
                if (exist == 1)
                {
                    Toast.makeText(InfoMatchActivity.this, "This email exists in the match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = getIntent();
                    String name = intent.getStringExtra("NAME");
                    String place = intent.getStringExtra("PLACE");
                    String duration = intent.getStringExtra("DURATION");
                    String count = intent.getStringExtra("COUNT");
                    String integrants = intent.getStringExtra("INTEGRANTS");
                    String creator = intent.getStringExtra("CREATOR");
                    String idsintegrants =  intent.getStringExtra("IDSINTEGRANTS");
                    integrants = integrants + myname + "-" + mymail +  "\n";
                    idsintegrants = idsintegrants + iduser + ";";
                    int countint = Integer.parseInt(count);
                    countint = countint + 1;
                    String timestamps = intent.getStringExtra("TIMESTAMPS");
                    String[] ids = idsintegrants.split(";");
                    Match match = new Match(idmatch,name,place,duration,integrants,String.valueOf(countint),timestamps,creator,idsintegrants);
                    FirebaseDatabase.getInstance().getReference().child("match_creator").child("user").child(ids[0]+ ";").child(idmatch).setValue(match);

                    for (int i = 1; i < ids.length; i++) {
                        FirebaseDatabase.getInstance().getReference().child("match_participer").child("user").child(ids[i]+";").child("match").child(idmatch).setValue(match);
                    }

                    FirebaseDatabase.getInstance().getReference().child("public_match").child("match").child(idmatch).setValue(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(InfoMatchActivity.this, "Congratulations, you is in this match", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                }
            }
        });
    }
}
