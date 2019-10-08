package com.example.Workspace.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.Workspace.R;
import com.example.Workspace.network.Workspace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;


public class coworkerWidgetAdapter implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int appWidgetId;
    private ArrayList<Workspace> mFavouritePlaceArrayList = new ArrayList<>();

    //Constructor
    public coworkerWidgetAdapter(Context context, Intent intent) {
        this.mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
       //getFavouritePlaceListData();

    }

    @Override
    public void onDataSetChanged() {
        //getFavouritePlaceListData();
        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference("favorites")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for ( DataSnapshot  snapshot:dataSnapshot.getChildren()) {
                    Workspace w=snapshot.getValue(Workspace.class);
                    Log.d("taggg","name widgettt = "+w.getPlaceName());
                    mFavouritePlaceArrayList.add(w);
                }
                Log.d("taggg","size Favouritea:" +String.valueOf(mFavouritePlaceArrayList.size()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("taggg", "Failed to read fovorites.", databaseError.toException());

            }
        });
    }

    @Override
    public void onDestroy() {
        mFavouritePlaceArrayList.clear();
    }

    @Override
    public int getCount() {
        Log.d("taggg","size  getCount:" +String.valueOf(mFavouritePlaceArrayList.size()));
        return mFavouritePlaceArrayList.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_widget);
        remoteViews.setTextViewText(R.id.place_name, mFavouritePlaceArrayList.get(position).getPlaceName());
        remoteViews.setTextViewText(R.id.place_adress, mFavouritePlaceArrayList.get(position)
                .getPlaceAddress());
        remoteViews.setTextViewText(R.id.place_phone, mFavouritePlaceArrayList.get(position)
                .getPlacePhoneNumber());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
