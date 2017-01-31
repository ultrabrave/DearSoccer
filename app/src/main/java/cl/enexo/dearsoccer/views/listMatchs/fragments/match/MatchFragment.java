package cl.enexo.dearsoccer.views.listMatchs.fragments.match;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.background.ConfigSelected;
import cl.enexo.dearsoccer.models.Config;
import cl.enexo.dearsoccer.views.config.ConfigSelectedCallback;

public class MatchFragment extends Fragment implements ConfigSelectedCallback {
    private View myview;
    CoordinatorLayout coordinatorLayout;
    RecyclerView recycler;
    public MatchFragment() {
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
        myview = view;
        coordinatorLayout = (CoordinatorLayout) view;
        recycler = (RecyclerView) myview.findViewById(R.id.rv_match);
        new ConfigSelected(this).execute(getContext());
    }

    @Override
    public void ok(Config config) {
        try {
            ProgressBar progressBar = (ProgressBar)myview.findViewById(R.id.spin_kit);
            new GetMatch(recycler,getContext(),coordinatorLayout,progressBar,getActivity()).getInformation(config);
        }catch(Exception e)
        {
            e.getMessage();
        }
    }
}