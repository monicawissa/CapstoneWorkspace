package com.example.Workspace.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by iamcs on 2017-05-04.
 */

public class coworkerWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new coworkerWidgetAdapter(this);
    }
}
