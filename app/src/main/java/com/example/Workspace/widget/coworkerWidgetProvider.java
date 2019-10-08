package com.example.Workspace.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.Workspace.R;


/**
 * Created by iamcs on 2017-05-04.
 */

public class coworkerWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(
                    context.getPackageName(), R.layout.coworkspace__widget);

            Intent serviceIntent = new Intent(context, coworkerWidgetService.class);
            remoteViews.setRemoteAdapter(R.id.list_view, serviceIntent);

            Intent intent = new Intent(context, coworkerWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);


            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            remoteViews.setOnClickPendingIntent(R.id.frame_Layout, pendingIntent);

            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }
}
