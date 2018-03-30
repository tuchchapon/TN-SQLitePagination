package com.example.user.tn_sqlitepagination;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CustomHolder extends RecyclerView.ViewHolder {
      public TextView textView;

      public CustomHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.text_view);
      }
}
