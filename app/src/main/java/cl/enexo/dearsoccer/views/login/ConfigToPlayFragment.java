package cl.enexo.dearsoccer.views.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.enexo.dearsoccer.R;

/**
 * Created by Kevin on 21-01-2017.
 */

public class ConfigToPlayFragment extends Fragment {
    /*ArrayList<Item_Vehiculo> gridArray = new ArrayList<Item_Vehiculo>();
    CustomGridVehiculo customGridAdapter;
    RecyclerView recyclerView;
    GridView gridView;
    private ProgressDialog pd = null;
    BD usdbh;
    Ws_vehiculos ws_vehiculos = new Ws_vehiculos();*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_to_play, container, false );
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
