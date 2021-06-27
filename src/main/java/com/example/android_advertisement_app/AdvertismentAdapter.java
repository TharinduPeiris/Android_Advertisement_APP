package com.example.android_advertisement_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdvertismentAdapter extends RecyclerView.Adapter<AdvertismentAdapter.ViewHolder> {
    private ArrayList<Advertisment> mADList;
    private Context mContext;
    private RecyclerView mRecyclerV;

// new addd
    public AdvertismentAdapter(ArrayList<Advertisment> list,Context mContext){
        this.mADList = list;
        this.mContext=mContext;

    }

    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textName;
        public TextView textPrice;
        public ImageView imageView3;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageView3 = (ImageView) v.findViewById(R.id.imageView3);
            textName = (TextView) v.findViewById(R.id.textName);
            textPrice = (TextView) v.findViewById(R.id.textPrice);
        }
    }

    public void add(int position, Advertisment advertisment) {
        mADList.add(position, advertisment);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mADList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdvertismentAdapter(ArrayList<Advertisment> myDataset, Context context, RecyclerView recyclerView) {
        mADList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdvertismentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Advertisment advertisment = mADList.get(position);

        holder.textName.setText(advertisment.getName());
        holder.textPrice.setText(advertisment.getPrice());

        byte[] adimage = advertisment.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(adimage,0, adimage.length);
        holder.imageView3.setImageBitmap(bitmap);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //go to showAd activity
                showAdActivity(advertisment.getUser_id(), advertisment.getId());

                    }

        });

    }

    private void showAdActivity(int user_id, long ad_id){
        Intent itemShow = new Intent(mContext, DetailsOfSelectedAd.class);
        itemShow.putExtra("AD_ID", ad_id);
        itemShow.putExtra("USER_ID", user_id);
        mContext.startActivity(itemShow);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mADList.size();
    }

    // new addd
    public void updatelist(List<Advertisment> newlist){

        mADList = new ArrayList<>();
        mADList.addAll(newlist);
        notifyDataSetChanged();



    }

}