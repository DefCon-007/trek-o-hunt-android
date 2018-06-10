package trek.visdrotech.com.trek_o_hunt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import trek.visdrotech.com.trek_o_hunt.utils.RecyclerImageAdapter;

public class TrekDescriptionFragment extends Fragment{

    public static Trek trek;
    public static boolean boughtTrek;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(trek.getName());

//        ((TextView)view.findViewById(R.id.tvName)).setText(trek.getName());
        ((TextView)view.findViewById(R.id.tvAbout)).setText(trek.getAbout());
        ((TextView)view.findViewById(R.id.tvChecklist)).setText(trek.getCheckList());
        ((TextView)view.findViewById(R.id.tvThingsTodo)).setText(trek.getThingsToNote());
        ((TextView)view.findViewById(R.id.tvDifficulty)).setText(trek.getDifficulty().toString());
        ((RatingBar)view.findViewById(R.id.rb)).setRating((float)trek.getRating());


        view.findViewById(R.id.bBuyNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boughtTrek) {
                    Toast.makeText(getActivity(), "Purchased successfully!", Toast.LENGTH_SHORT).show();
                    BoughtTrekListFragment.treks = new ArrayList<>();
                    BoughtTrekListFragment.treks.add(trek);
                }
            }
        });


        if(boughtTrek)
        {
            ((TextView)view.findViewById(R.id.bBuyNow)).setText("Start Trek");
            view.findViewById(R.id.tvMapHead).setVisibility(View.VISIBLE);
            view.findViewById(R.id.tvChecklistHead).setVisibility(View.VISIBLE);
            view.findViewById(R.id.tvChecklist).setVisibility(View.VISIBLE);
            view.findViewById(R.id.iv).setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(trek.getStaticImgUrl())
                    .placeholder(R.drawable.img_trek_1)
                    .error(R.drawable.img_trek_2)
                    .into((ImageView)view.findViewById(R.id.iv));
        }









    }

}