package trek.visdrotech.com.trek_o_hunt.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import trek.visdrotech.com.trek_o_hunt.R;

public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final ImageView ivBackground;

        public ViewHolder(View view) {
            super(view);
            ivBackground = (ImageView) view.findViewById(R.id.ivBackground);
        }
        public ImageView getIvBackground()
        {
            return ivBackground;
        }
    }

    public RecyclerImageAdapter(ArrayList<String> mDataset) {
        this.mDataset = mDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_listitem, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.get()
                .load(mDataset.get(position))
                .placeholder(R.drawable.img_trek_1)
                .error(R.drawable.img_trek_2)
                .into(holder.getIvBackground());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}