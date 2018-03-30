package com.example.user.tn_sqlitepagination;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.Toast;

import com.paginate.Paginate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
      private RecyclerView mRcv;
      private LinearLayoutManager mLayoutMan;
      private ArrayList<RowItem> mItemArray;
      private CustomAdapter mAdapter;
      private boolean mIsLoading = false;
      private int mCreatedItems;

      private final int ITEMS_PER_PAGE = 5;
      private SQLiteHelper mSQLiteHelper;
      private SQLiteDatabase mDb;
      private int mTotalRows;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                  }
            });
            fab.hide();
      }

      @Override
      public void onStart() {
            super.onStart();

            mSQLiteHelper = SQLiteHelper.getInstance(getBaseContext());
            mDb = mSQLiteHelper.getReadableDatabase();
            String sql = "SELECT * FROM list LIMIT " + ITEMS_PER_PAGE;
            Cursor cursor = mDb.rawQuery(sql, null);

            mItemArray = new ArrayList<>();
            RowItem row;
            while(cursor.moveToNext()) {
                  row = new RowItem();
                  row.item = cursor.getString(1);
                  mItemArray.add(row);
            }

            mRcv = (RecyclerView)findViewById(R.id.recycler_view);
            mAdapter = new CustomAdapter(this, mItemArray);
            mRcv.setAdapter(mAdapter);

            mLayoutMan = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            mRcv.setLayoutManager(mLayoutMan);

            sql = "SELECT COUNT(*) FROM list";
            Cursor cursorCount = mDb.rawQuery(sql, null);
            cursorCount.moveToFirst();
            mTotalRows = cursorCount.getInt(0);
            cursorCount.close();

            onScrollRecyclerView();
      }

      private void onScrollRecyclerView() {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                  @Override
                  public boolean isLoading() {
                        return mIsLoading;
                  }

                  @Override
                  public boolean hasLoadedAllItems() {
                        mCreatedItems = mLayoutMan.getItemCount();
                        if(mCreatedItems >= mTotalRows) {
                              return true;
                        } else {
                              return false;
                        }
                  }

                  @Override
                  public void onLoadMore() {
                        mIsLoading = true;
                        new Handler().postDelayed(new Runnable() {
                              @Override
                              public void run() {
                                    mCreatedItems = mLayoutMan.getItemCount();
                                    int startPosition = mCreatedItems - 1;
                                    String sql = "SELECT * FROM list LIMIT ?, ?";
                                    String[] args = {startPosition + "",  ITEMS_PER_PAGE + ""};
                                    Cursor cursor = mDb.rawQuery(sql, args);

                                    RowItem row;
                                    while(cursor.moveToNext()) {
                                          row = new RowItem();
                                          row.item = cursor.getString(1);
                                          mItemArray.add(row);
                                    }

                                    mAdapter.notifyItemRangeInserted(startPosition, ITEMS_PER_PAGE);
                                    mIsLoading = false;
                              }
                        }, 4000);
                  }
            };

            Paginate.with(mRcv, callbacks)
                    .addLoadingListItem(true)
                    .build();
      }

      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if(id == R.id.action_settings) {
                  return true;
            }

            return super.onOptionsItemSelected(item);
      }
}
