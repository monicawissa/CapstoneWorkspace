package com.example.Workspace.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;



public class coworkerWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new coworkerWidgetAdapter(this.getApplicationContext(), intent);
    }
}
