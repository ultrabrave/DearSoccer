package cl.enexo.dearsoccer.views.listMatchs.fragments.toplay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cl.enexo.dearsoccer.R;

public class ToPlayFragment extends Fragment {
    private View myview;

    public ToPlayFragment() {
    }

    public static ToPlayFragment newInstance() {
        return new ToPlayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_play, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myview = view;
        RecyclerView recycler = (RecyclerView) myview.findViewById(R.id.rv_toplay);
        new GetToPlay(recycler,getContext(),getActivity()).getInformation();
    }
}
