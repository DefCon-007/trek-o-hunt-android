package trek.visdrotech.com.trek_o_hunt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trek.visdrotech.com.trek_o_hunt.utils.RecyclerImageAdapter;

public class TrekDescriptionFragment extends Fragment{

    public static Trek trek;

    public TrekDescriptionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trek_description, container, false);
        initViews(view);
        return view;
    }

    // Initiate Views
    private void initViews(View view) {
        RecyclerView rvTopPicks = (RecyclerView)  view.findViewById(R.id.rvTop);
        rvTopPicks.setHasFixedSize(true);
        LinearLayoutManager layoutManagerTop = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTopPicks.setLayoutManager(layoutManagerTop);
        rvTopPicks.setAdapter(new RecyclerImageAdapter(trek.getImages()));
    }

}