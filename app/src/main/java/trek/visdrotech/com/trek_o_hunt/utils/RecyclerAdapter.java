package trek.visdrotech.com.trek_o_hunt.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import trek.visdrotech.com.trek_o_hunt.R;
import trek.visdrotech.com.trek_o_hunt.Trek;
import trek.visdrotech.com.trek_o_hunt.TrekDescriptionFragment;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Trek> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final CardView cv;
        private final TextView tvName, tvRating, tvDistance, tvTime, tvDifficulty;
        private final ImageView ivBackground;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv);
            tvName = (TextView) view.findViewById(R.id.tvTrekName);
            ivBackground = (ImageView) view.findViewById(R.id.ivBackground);
            tvDifficulty = (TextView)view.findViewById(R.id.tvDifficulty);
            tvRating = (TextView)view.findViewById(R.id.tvRating);
            tvDistance = (TextView)view.findViewById(R.id.tvDistance);
            tvTime = (TextView)view.findViewById(R.id.tvTime);

        }

        public TextView getTvName()
        {
            return tvName;
        }

        public TextView getTvRating() {
            return tvRating;
        }

        public TextView getTvDistance() {
            return tvDistance;
        }

        public TextView getTvTime() {
            return tvTime;
        }

        public TextView getTvDifficulty() {
            return tvDifficulty;
        }

        public ImageView getIvBackground()
        {
            return ivBackground;
        }
        public CardView getCv() {
            return cv;
        }
    }

    public RecyclerAdapter(ArrayList<Trek> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trek_listitem, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getTvName().setText(mDataset.get(position).getName());
        holder.getTvDifficulty().setText(mDataset.get(position).getDifficultyStr() + "");
        holder.getTvRating().setText(mDataset.get(position).getRating() + "/5.0");
        holder.getTvDistance().setText(mDataset.get(position).getDistance()+" KM");
        holder.getTvTime().setText(mDataset.get(position).getTimeStr());

        Picasso.get()
                .load(mDataset.get(position).getImgUrl())
                .placeholder(R.drawable.img_trek_1)
                .error(R.drawable.img_trek_2)
                .into(holder.getIvBackground());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrekDescriptionFragment.trek = mDataset.get(position);
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.layout, new TrekDescriptionFragment()).addToBackStack("").commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}