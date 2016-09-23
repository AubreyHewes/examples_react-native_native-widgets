package nl.hewes.android.widget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import android.app.AlarmManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import android.app.PendingIntent;

/**
 *
 */
public abstract class TimeableUpdatingWidget extends AppWidgetProvider {
    static String LOG_TAG = "TimeableUpdatingWidget";
    static DateFormat df = new SimpleDateFormat("hh:mm:ss");
    static String WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"; // todo bc

    // 60 seconds is the minimum allowed from 5.1
    static int UPDATE_TIMEOUT = 60000;

    /**
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    public abstract void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                  int appWidgetId);

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.i(LOG_TAG,  "Updating widgets " + Arrays.asList(appWidgetIds));

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "Widget Provider enabled.  Starting timer to update widget every minute");
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);

        Log.v(LOG_TAG, "UPDATE_TIMEOUT: " + UPDATE_TIMEOUT);

        // 60 seconds is the minimum allowed from 5.1 onwards
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), UPDATE_TIMEOUT
                , createClockTickIntent(context));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "Widget Provider disabled. Turning off timer");
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(LOG_TAG, "Received intent " + intent);
        if (WIDGET_UPDATE.equals(intent.getAction())) {
            Log.d(LOG_TAG, "Clock update");
            // Get the widget manager and ids for this widget provider, then call the shared
            // clock update method.
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetID: ids) {
                updateAppWidget(context, appWidgetManager, appWidgetID);
            }
        }
    }

    private PendingIntent createClockTickIntent(Context context) {
        return PendingIntent.getBroadcast(context, 0, new Intent(WIDGET_UPDATE),
            PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

