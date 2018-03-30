package com.example.user.tn_sqlitepagination;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomHolder> {

      private ArrayList<RowItem> mItems;
      private Context mContext;

      public CustomAdapter(Context context, ArrayList<RowItem> items) {
            mContext = context;
            mItems = items;
      }

      @Override
      public int getItemCount() {
            return mItems.size();
      }

      @Override
      public CustomHolder onCreateViewHolder(ViewGroup vg, int type) {
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_layout, vg, false);
            final CustomHolder vHolder = new CustomHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        int pos = vHolder.getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                              //...
                        }
                  }
            });

            return vHolder;
      }

      @Override
      public void onBindViewHolder(CustomHolder vHolder, int position) {
            RowItem row = mItems.get(position);
            vHolder.textView.setText(row.item);
      }

}


