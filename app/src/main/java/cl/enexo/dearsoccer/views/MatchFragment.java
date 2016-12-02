package cl.enexo.dearsoccer.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.data.FirebaseRef;
import cl.enexo.dearsoccer.models.Match;
import cl.enexo.dearsoccer.models.User;

import static android.R.attr.key;

public class MatchFragment extends Fragment {
    private Dialog dialog;
    private FloatingActionButton fab;
    private SeekBar sbduration;
    private TextView counter;
    private EditText etname;
    private EditText etplace;
    private Button btnadd;


    public MatchFragment() {
        // Required empty public constructor
    }

    public static MatchFragment newInstance() {
        return new MatchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDialog();
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view;
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                coordinatorLayout.removeView(progressBar);
            }
        },5000);


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.rv_match);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("public_match").child("match");
        FirebaseRecyclerAdapter adapter =
                new FirebaseRecyclerAdapter<Match, MatchHolder>
                        (Match.class, R.layout.item_match, MatchHolder.class, reference) {
                    @Override
                    protected void populateViewHolder(MatchHolder viewHolder, Match model, int position) {
                        final String name = model.name;
                        final String place = model.place;
                        final String duration = model.time;
                        final String integrants = model.integrants;
                        final String countintegrants = model.countintegrants;
                        final String timestamps = model.timestamp;
                        final String texto =  model.name + "," + model.place;
                        final String idmatch = model.idmatch;
                        final String creator = model.creator;
                        final String idsintegrants = model.idsintegrants;
                        viewHolder.setName(name);
                        viewHolder.setPlace(place);
                        viewHolder.setDuration(duration);
                        viewHolder.setIntegrants(integrants);
                        viewHolder.setCountintegrants(countintegrants+ " Integrants");
                        viewHolder.setTimestamps(timestamps);
                        viewHolder.setTexto(texto);
                        viewHolder.setIdMatch(idmatch);
                        viewHolder.setIdsIntegrants(idmatch);

                        viewHolder.btnaddplayer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(),InfoMatchActivity.class);
                                i.putExtra("NAME",name);
                                i.putExtra("PLACE",place);
                                i.putExtra("DURATION",duration);
                                i.putExtra("COUNT",countintegrants);
                                i.putExtra("INTEGRANTS",integrants);
                                i.putExtra("TIMESTAMPS",timestamps);
                                i.putExtra("IDMATCH",idmatch);
                                i.putExtra("CREATOR",creator);
                                i.putExtra("IDSINTEGRANTS",idsintegrants);
                                startActivity(i);
                            }
                        });
                    }
                };
        recycler.setAdapter(adapter);
    }

    private void setDialog(){
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_new_match);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        sbduration = (SeekBar) dialog.findViewById(R.id.sb_hour);
        counter = (TextView) dialog.findViewById(R.id.tv_counter);
        btnadd = (Button) dialog.findViewById(R.id.btn_add_match);
        etname = (EditText) dialog.findViewById(R.id.et_name_match);
        etplace = (EditText) dialog.findViewById(R.id.et_place_match);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMatch();
            }
        });

        sbduration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                counter.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void addMatch()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail();
        String nameuser = auth.getCurrentUser().getDisplayName();
        String iduser = auth.getCurrentUser().getUid();
        User user = new User(nameuser, email,iduser);
        try
        {
            String name = etname.getText().toString();
            String place = etplace.getText().toString();
            String time = counter.getText().toString();
            String integrants = nameuser+ "-" + email + "\n";
            String creator = nameuser;
            String countintegrants = "1";
            String timestamps = String.valueOf(System.currentTimeMillis());
            String idmatch = iduser+name+timestamps;
            String idsintegrans = iduser + ";";


            Match match = new Match(idmatch,name,place,time,integrants,countintegrants,timestamps,creator,idsintegrans);
            FirebaseRef ref = new FirebaseRef();
            FirebaseDatabase.getInstance().getReference().child("match_creator").child("user").child(iduser+";").child(iduser+name+timestamps).setValue(match);
            FirebaseDatabase.getInstance().getReference().child("public_match").child("match").child(iduser+name+timestamps).setValue(match).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Partido almacenado con éxito", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public static class MatchHolder extends RecyclerView.ViewHolder {
        private TextView tvname;
        private TextView tvduration;
        private TextView tvplace;
        private TextView tvintegrans;
        private TextView tvcountintegrants;
        private TextView tvtimestamps;
        private TextView tvtexto;
        private LinearLayout btnaddplayer;
        private TextView tvidmatch;
        private TextView tvcreator;
        private TextView tvidsintegrants;

        public MatchHolder(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.tv_name_item_match);
            tvplace = (TextView) itemView.findViewById(R.id.tv_place_item_match);
            tvduration = (TextView) itemView.findViewById(R.id.tv_duration_item_match);
            tvintegrans = (TextView) itemView.findViewById(R.id.tv_integrants_item_match);
            tvcountintegrants = (TextView) itemView.findViewById(R.id.tv_countintegrants_item_match);
            tvtimestamps = (TextView) itemView.findViewById(R.id.tv_timestamps_item_match);
            tvtexto = (TextView) itemView.findViewById(R.id.tv_texto_visible_match);
            tvidmatch = (TextView) itemView.findViewById(R.id.tv_idmatch_item_match);
            btnaddplayer = (LinearLayout) itemView.findViewById(R.id.btn_see_match);
            tvcreator = (TextView) itemView.findViewById(R.id.tv_creator_item_match);
            tvidsintegrants = (TextView) itemView.findViewById(R.id.tv_idsintegrants_item_match);
        }

        public void setName(String name) {
            tvname.setText(name);
        }

        public void setPlace(String place) {
            tvplace.setText(place);
        }

        public void setDuration(String duration) {
            tvduration.setText(duration);
        }

        public void setIntegrants(String integrants) {
            tvintegrans.setText(integrants);
        }

        public void setCountintegrants(String countintegrants) {
            tvcountintegrants.setText(countintegrants);
        }

        public void setTimestamps(String timestamps) {
            tvtimestamps.setText(timestamps);
        }

        public void setTexto(String texto) {
            tvtexto.setText(texto);
        }
        public void setIdMatch(String idmatch) {
            tvidmatch.setText(idmatch);
        }
        public void setCreator(String creator) {
            tvcreator.setText(creator);
        }
        public void setIdsIntegrants(String idsIntegrants) {
            tvcreator.setText(idsIntegrants);
        }
    }

}
