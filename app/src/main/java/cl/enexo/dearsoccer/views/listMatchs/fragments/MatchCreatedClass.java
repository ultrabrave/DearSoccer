package cl.enexo.dearsoccer.views.listMatchs.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cl.enexo.dearsoccer.R;

/**
 * Created by Kevin on 04-01-2017.
 */

public class MatchCreatedClass extends RecyclerView.ViewHolder{
        private TextView tvname;
        private TextView tvduration;
        private TextView tvplace;
        private TextView tvintegrans;
        private TextView tvcountintegrants;
        private TextView tvtimestamps;
        private TextView tvtexto;
        public LinearLayout btnaddplayer;
        private TextView tvidmatch;
        private TextView tvcreator;
        private TextView tvidsintegrants;

        public MatchCreatedClass(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.tv_name_item_mymatches);
            tvplace = (TextView) itemView.findViewById(R.id.tv_place_item_mymatches);
            tvduration = (TextView) itemView.findViewById(R.id.tv_duration_item_mymatches);
            tvintegrans = (TextView) itemView.findViewById(R.id.tv_integrants_item_mymatches);
            tvcountintegrants = (TextView) itemView.findViewById(R.id.tv_countintegrants_item_mymatches);
            tvtimestamps = (TextView) itemView.findViewById(R.id.tv_timestamps_item_mymatches);
            tvtexto = (TextView) itemView.findViewById(R.id.tv_texto_visible_mymatches);
            tvidmatch = (TextView) itemView.findViewById(R.id.tv_idmatch_item_mymatches);
            btnaddplayer = (LinearLayout) itemView.findViewById(R.id.btn_see_mymatches);
            tvcreator = (TextView) itemView.findViewById(R.id.tv_creator_item_mymatches);
            tvidsintegrants = (TextView) itemView.findViewById(R.id.tv_idsintegrants_item_mymatches);
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

