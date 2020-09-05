package com.inspirecoding.roombasics.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.inspirecoding.roombasics.MainActivity
import com.inspirecoding.roombasics.R
import com.inspirecoding.roombasics.enums.Prioirities
import com.inspirecoding.roombasics.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Implementation of App Widget functionality.
 */
@AndroidEntryPoint
class AppWidget : AppWidgetProvider()
{
    private val job = SupervisorJob()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    @Inject lateinit var toDoRepository: ToDoRepository

    override fun onReceive(context: Context, intent: Intent?)
    {
        super.onReceive(context, intent)

        coroutineScope.launch {
            toDoRepository.getFirstToDoItem.collect { _toDo ->
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val man = AppWidgetManager.getInstance(context)
                val ids = man.getAppWidgetIds(ComponentName(context, AppWidget::class.java))

                if (_toDo != null)
                {
                    for (appWidgetId in ids)
                    {
                        updateAppWidget(
                            context, appWidgetManager, appWidgetId,
                            _toDo.title, _toDo.dueDate, _toDo.description, _toDo.priority
                        )
                    }
                }
            }
        }
    }

    override fun onDisabled(context: Context)
    {
        job.cancel()
    }
}

internal fun updateAppWidget(
    context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int,
    title: String?, dueDate: String?, description: String?, priority: String?)
{
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.app_widget)

    if (title != null) {
        views.setTextViewText(R.id.tv_widget_title, title)
    } else {
        views.setTextViewText(R.id.tv_widget_title, "")
    }
    if (dueDate != null) {
        views.setTextViewText(R.id.tv_widget_dueDate, dueDate)
    } else {
        views.setTextViewText(R.id.tv_widget_dueDate, "")
    }
    if (description != null) {
        views.setTextViewText(R.id.tv_widget_description, description)
    } else {
        views.setTextViewText(R.id.tv_widget_title, "")
    }
    if (description != null) {
        when (priority)
        {
            Prioirities.LOW.name -> views.setImageViewResource(R.id.iv_widget_priority, R.drawable.prio_green)
            Prioirities.MEDIUM.name -> views.setImageViewResource(R.id.iv_widget_priority, R.drawable.prio_orange)
            Prioirities.HIGH.name -> views.setImageViewResource(R.id.iv_widget_priority, R.drawable.prio_red)
        }
    }

    views.setOnClickPendingIntent(R.id.widget_layout,
        getPendingIntentActivity(context))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
private fun getPendingIntentActivity(context: Context): PendingIntent
{
    // Construct an Intent which is pointing this class.
    val intent = Intent(context, MainActivity::class.java)
    // And this time we are sending a broadcast with getBroadcast
    return PendingIntent.getActivity(context, 0, intent, 0)
}