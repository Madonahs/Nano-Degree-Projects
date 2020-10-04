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

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViewsService
import com.madonasyombua.bakingapp.models.Recipe
import com.madonasyombua.bakingapp.utils.Prefs.saveRecipe
import com.madonasyombua.bakingapp.widget.AppWidget
import com.madonasyombua.bakingapp.widget.AppWidget.Companion.updateAppWidgets

class AppWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        return ListRemoteViewsFactory(applicationContext)
    }

    companion object {
        fun updateWidget(context: Context?, recipe: Recipe?) {
            context?.let { saveRecipe(it, recipe) }
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(context?.let { ComponentName(it, AppWidget::class.java) })
            context?.let { updateAppWidgets(it, appWidgetManager, appWidgetIds) }
        }
    }
}