package com.example.moetaz.backingapp.widgets;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.utilities.IngProivderConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moetaz on 10/25/2017.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory  {

    private ContentResolver contentResolver;
    private List<String> collection = new ArrayList<>();
    private Context context;
      Intent intent;

    private void InitiData(){
        collection.clear();
        contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(IngProivderConstants.CONTENT_URI_1,null,null,null,null);
        if(cursor != null && cursor.getCount() > 0){
            int i = 1;
            while (cursor.moveToNext()){
                collection.add(i+"- "+cursor.getString(2)+":  "+cursor.getString(0)+" "+cursor.getString(1));
                  i++;
            }
        }

        assert cursor != null;
        cursor.close();
    }



    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }
    @Override
    public void onCreate() {
        InitiData();
    }

    @Override
    public void onDataSetChanged() {
        InitiData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
         RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.widgettext,collection.get(position));
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
