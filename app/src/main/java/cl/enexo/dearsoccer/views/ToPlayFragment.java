package cl.enexo.dearsoccer.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.models.Match;

public class ToPlayFragment extends Fragment {

    public ToPlayFragment() {
        // Required empty public constructor
    }

    public static ToPlayFragment newInstance() {
        return new ToPlayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_play, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String iduser = auth.getCurrentUser().getUid();
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.rv_toplay);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("match_participer").child("user").child(iduser+";").child("match");
        FirebaseRecyclerAdapter adapter =
                new FirebaseRecyclerAdapter<Match, ToPlayFragment.toPlayHolder>
                        (Match.class, R.layout.item_toplay, ToPlayFragment.toPlayHolder.class, reference) {
                    @Override
                    protected void populateViewHolder(ToPlayFragment.toPlayHolder viewHolder, Match model, int position) {
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
                        viewHolder.setIdsIntegrants(idsintegrants);

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


    public static class toPlayHolder extends RecyclerView.ViewHolder {
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

        public toPlayHolder(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.tv_name_item_toplay);
            tvplace = (TextView) itemView.findViewById(R.id.tv_place_item_toplay);
            tvduration = (TextView) itemView.findViewById(R.id.tv_duration_item_toplay);
            tvintegrans = (TextView) itemView.findViewById(R.id.tv_integrants_item_toplay);
            tvcountintegrants = (TextView) itemView.findViewById(R.id.tv_countintegrants_item_toplay);
            tvtimestamps = (TextView) itemView.findViewById(R.id.tv_timestamps_item_toplay);
            tvtexto = (TextView) itemView.findViewById(R.id.tv_texto_visible_toplay);
            tvidmatch = (TextView) itemView.findViewById(R.id.tv_idmatch_item_toplay);
            btnaddplayer = (LinearLayout) itemView.findViewById(R.id.btn_see_toplay);
            tvcreator = (TextView) itemView.findViewById(R.id.tv_creator_item_toplay);
            tvidsintegrants = (TextView) itemView.findViewById(R.id.tv_idsintegrants_item_toplay);
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
