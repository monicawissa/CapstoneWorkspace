package com.example.Workspace.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.Workspace.R;
import com.example.Workspace.network.Workspace;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

public class coworkerWidgetAdapter implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int appWidgetId;

    private List<Workspace> mFavouriteWorkspaceArray ;

    //Constructor
    public coworkerWidgetAdapter(Context context, Intent intent)
    {
        mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        getFavouritePlaceListData_firebase();
    }

    @Override
    public void onDataSetChanged() {
         //getFavouritePlaceListData_firebase();
    }

    @Override
    public void onDestroy() {
        mFavouriteWorkspaceArray.clear();

    }

    @Override
    public int getCount() {
        return mFavouriteWorkspaceArray.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_widget);
        remoteViews.setTextViewText(R.id.place_name, mFavouriteWorkspaceArray.get(position).getPlaceName());
        remoteViews.setTextViewText(R.id.place_adress, mFavouriteWorkspaceArray.get(position)
                .getPlaceAddress());
        remoteViews.setTextViewText(R.id.place_phone, mFavouriteWorkspaceArray.get(position)
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


    private void getFavouritePlaceListData_firebase() {
        mFavouriteWorkspaceArray=new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference("favorites")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFavouriteWorkspaceArray.clear();
                for ( DataSnapshot  snapshot:dataSnapshot.getChildren()) {
                    Workspace w=snapshot.getValue(Workspace.class);
                    Log.d("taggg","name widget = "+w.getPlaceName());
                    mFavouriteWorkspaceArray.add(w);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("taggg", "Failed to read fovorites.", databaseError.toException());

            }
        });

    }
}
