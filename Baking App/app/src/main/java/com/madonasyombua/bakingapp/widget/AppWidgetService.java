package com.madonasyombua.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.madonasyombua.bakingapp.utils.Prefs;
import com.madonasyombua.bakingapp.models.Recipe;

public class AppWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context, Recipe recipe) {
        Prefs.saveRecipe(context, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, AppWidget.class));
        AppWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListRemoteViewsFactory(getApplicationContext());
    }

}