package trek.visdrotech.com.trek_o_hunt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import trek.visdrotech.com.trek_o_hunt.utils.RecyclerAdapter;

public class BoughtTrekListFragment extends Fragment{

    public static ArrayList<Trek> treks;

    public BoughtTrekListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bought_treklist, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Treks");
        initViews(view);
        return view;
    }

    // Initiate Views
    private void initViews(View view) {
        RecyclerView rvTopPicks = (RecyclerView)  view.findViewById(R.id.rv);
        rvTopPicks.setHasFixedSize(true);
        LinearLayoutManager layoutManagerTop = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTopPicks.setLayoutManager(layoutManagerTop);
        rvTopPicks.setAdapter(new RecyclerAdapter(treks, getActivity()));
        TrekDescriptionFragment.boughtTrek = true;
        TrekDescriptionFragment.createdTrek = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
    }
}