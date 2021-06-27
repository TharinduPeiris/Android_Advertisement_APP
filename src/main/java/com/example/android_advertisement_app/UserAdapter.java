package com.example.android_advertisement_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<Advertisment> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameTxtV;
        public TextView priceTxtV;
        public ImageView imageView;


        public View layout1;

        public ViewHolder(View v) {
            super(v);
            layout1 = v;
            nameTxtV = (TextView) v.findViewById(R.id.textNameUp);
            priceTxtV = (TextView) v.findViewById(R.id.textPriceUp);
            imageView = (ImageView) v.findViewById(R.id.imageView3Up);


        }
    }

    public void add(int position, Advertisment person) {
        mPeopleList.add(position, person);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(List<Advertisment> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new cardviews (invoked by the layout manager)
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //user_cardview.xml
        View v = inflater.inflate(R.layout.user_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // user enter details card view show
        final Advertisment person = mPeopleList.get(position);
        holder.nameTxtV.setText(person.getName());
        holder.priceTxtV.setText(person.getPrice());
        byte[] adimage = person.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(adimage,0, adimage.length);
        holder.imageView.setImageBitmap(bitmap);



        //listen to single view layout click
        holder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("UPDATE or DELETE Advertisment ?");
                builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(person.getId(), person.getUser_id());

                    }
                });
                builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDBHelper dbHelper = new MyDBHelper(mContext);
                        dbHelper.deleteADRecord(person.getId(), mContext);

                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mPeopleList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }

    private void goToUpdateActivity(long ad_id, int user_id) {
        Intent goToUpdate = new Intent(mContext, UpdateAd.class);
        goToUpdate.putExtra("AD_ID", ad_id);
        goToUpdate.putExtra("USER_ID", user_id);
        mContext.startActivity(goToUpdate);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }
}
