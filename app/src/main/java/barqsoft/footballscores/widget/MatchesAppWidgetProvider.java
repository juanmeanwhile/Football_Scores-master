package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.service.AppWidgetService;
import barqsoft.footballscores.service.myFetchService;

/**
 * Created by Juan on 20/10/2015.
 */
public class MatchesAppWidgetProvider  extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent){
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            rv.setOnClickPendingIntent(R.id.root, pendingIntent);

            Intent intent = new Intent(context, AppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            rv.setRemoteAdapter(appWidgetId,R.id.list, intent);

            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews
            // object above.
            rv.setEmptyView(R.id.list, R.id.empty);

            Intent service_start = new Intent(context, myFetchService.class);
            context.startService(service_start);


            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }
}