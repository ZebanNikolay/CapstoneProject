package com.zebannikolay.capstone.presentation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.zebannikolay.capstone.R;
import com.zebannikolay.capstone.domain.models.BoardGame;


public final class BoardGameWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, BoardGame game) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.board_game_widget);

        if (game != null) {
            views.setTextViewText(R.id.title, game.getTitle());
            views.setTextViewText(R.id.summary, game.getSummary());

            Intent intent = new Intent(context, GameDetailsActivity.class);
            intent.putExtra(GameDetailsActivity.EXTRA_GAME_ID, game.getId());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null);
        }
    }

    public static void updateBoardGameWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, BoardGame game) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, game);
        }
    }

}
