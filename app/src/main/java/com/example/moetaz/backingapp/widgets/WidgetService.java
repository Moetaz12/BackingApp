package com.example.moetaz.backingapp.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Moetaz on 10/25/2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this,intent);
    }
}
