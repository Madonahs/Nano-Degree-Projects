/*
 * Copyright (C) 2018 Madonah Syombua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.madonasyombua.bakingapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.madonasyombua.bakingapp.R
import com.madonasyombua.bakingapp.activities.MainActivity
import com.madonasyombua.bakingapp.utils.Prefs.loadRecipe

/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                            appWidgetId: Int) {
            val recipe = loadRecipe(context)
            if (recipe != null) {
                val pendingIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
                // Construct the RemoteViews object
                val views = RemoteViews(context.packageName, R.layout.baking_recipes_app_widget)
                views.setTextViewText(R.id.recipe_widget_name_text, recipe.name)
                // Widgets allow click handlers to only launch pending intents
                views.setOnClickPendingIntent(R.id.recipe_widget_name_text, pendingIntent)

                // Initialize the list view
                val intent = Intent(context, AppWidgetService::class.java)
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                // Bind the remote adapter
                views.setRemoteAdapter(R.id.recipe_widget_listview, intent)
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views)
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_widget_listview)
            }
        }

        @JvmStatic
        fun updateAppWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }
}