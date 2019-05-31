package com.cmexpertise.beautyapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmexpertise.beautyapp.R;
import com.cmexpertise.beautyapp.databinding.RowStorelistBinding;
import com.cmexpertise.beautyapp.fragment.StoreListFragment;
import com.cmexpertise.beautyapp.model.storeListmodel.StoreResponse;
import com.cmexpertise.beautyapp.util.Constans;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class StoreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<StoreResponse> productModelList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private StoreListFragment productListFragment;
    private boolean isLoading;
    private int lastPosition = -1;
    private RowStorelistBinding binding;
    private DecimalFormat df;


    public StoreListAdapter(StoreListFragment productListFragment, Context context, List<StoreResponse> items) {
        this.productModelList = items;
        this.productListFragment = productListFragment;
        this.mContext = context;
        df = new DecimalFormat("#.0");
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_storelist, parent, false);
        View v = binding.getRoot();
        v.setOnClickListener(this);
        return new ViewHolderData(binding);

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        lastPosition = position;
        ((ViewHolderData) holder).bindData(productModelList.get(position), position);


    }

    public void addRecord(ArrayList<StoreResponse> sleeptipsModelArrayList) {
        productModelList = sleeptipsModelArrayList;
    }


    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (StoreResponse) v.getTag());
                }
            }, 200);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, StoreResponse viewModel);

    }


    protected class ViewHolderData extends RecyclerView.ViewHolder {


        private RowStorelistBinding binding;


        public ViewHolderData(RowStorelistBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }

        public void bindData(StoreResponse item, int position) {

            itemView.setTag(item);
            binding.fragmentStoreRowTxtStoreName.setText("" + item.getName());

            Picasso.with(mContext).load(item.getImage())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.drawable.placeholder_thumb)
                    .resize(400, 300)
                    .into(binding.rowStoreIvStore);


            if (item.getAvgRate() != null) {
                binding.fragmentStoreRowTxtStoreRbStore.setRating(Float.parseFloat(item.getAvgRate()));
            } else {
                binding.fragmentStoreRowTxtStoreRbStore.setRating(0);
            }

            binding.fragmentStoreRowTxtStoreDistance.setText(String.valueOf(item.getAvgRate()));
            binding.fragmentStoreRowTxtStoreName.setTag(item);


            try {
                double distance = distance(Constans.CURRENT_LATITUDE, Constans.CURRENT_LONGITUDE, Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
                String dis=df.format((distance));
                binding.fragmentStoreRowTxtStoreDistance.setText( dis+ " " + mContext.getString(R.string.kmaway));
            } catch (NumberFormatException e) {
                e.printStackTrace();

            }



        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setLoading() {
        isLoading = true;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {

        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);
        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lon2);
        return (locationA.distanceTo(locationB) / 1000);

    }
}
