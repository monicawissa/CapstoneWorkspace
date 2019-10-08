package com.example.Workspace.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.Workspace.R;


public class coworkerWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            final RemoteViews remoteViews = new RemoteViews(
                    context.getPackageName(), R.layout.coworkspace__widget);

            Intent serviceIntent = new Intent(context, coworkerWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    id);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

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
