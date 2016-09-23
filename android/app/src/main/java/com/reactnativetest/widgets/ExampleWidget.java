package com.reactnativetest.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;
import android.util.Log;
import android.content.Intent;
import android.app.PendingIntent;

import com.reactnativetest.MainActivity;
import com.reactnativetest.R;

import nl.hewes.android.widget.TimeableUpdatingWidget;

/**
 * Implementation of timed Widget functionality.
 *
 * This widget just displays the time... updated at every minute
 */
public class ExampleWidget extends TimeableUpdatingWidget {
    static String LOG_TAG = "ExampleWidget";
    static DateFormat df = new SimpleDateFormat("hh:mm:ss");

    @Override
    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        Log.v(LOG_TAG, "updateAppWidget " + appWidgetId);

        /////////////////////////
        // Construct the RemoteViews object for this widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_example);

        /////////////////////////
        // Get the layout for the App Widget and attach an on-click listener to
        // open the main activity when the widget is clicked,,.
        // @TODO should this be applied on each update?
        views.setOnClickPendingIntent(R.id.example_widget,
            PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));

        /////////////////////////
        // set the view...
        // change the following by your view...
        updateAppWidgetWithExternalData(views);

        /////////////////////////
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     *
     */
    protected void updateAppWidgetWithExternalData(RemoteViews views) {
        //http://www.timeapi.org/utc/now?format=%25H%3a%25M%3a%25S

        // set the view...
        // change the following by your view...
        views.setTextViewText(R.id.example_widget, df.format(new Date()));
    }

}
