package org.ncrmnt.hhsw;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import android.appwidget.AppWidgetManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class sWitcherWidgetProvider extends AppWidgetProvider {

public void RunAsRoot(String[] cmds){
    try {
    Process p = Runtime.getRuntime().exec("su");
    DataOutputStream os = new DataOutputStream(p.getOutputStream());            
    for (String tmpCmd : cmds) {
            os.writeBytes(tmpCmd+"\n");
    }           
    os.writeBytes("exit\n");  
		os.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


String[] insmod = {"insmod /vendor/modules/sw-switch.ko"};


public void onEnabled(Context context) {
    // Honestly, I can't tell you how much this shit sucks
    RunAsRoot(insmod);
    File file = new File("/sys/module/sw_switch/parameters/", "power");
    if(!file.exists()) {
    	Log.d("swidget", "Could't insert switcher modules. This sucks");
    }else
    {
    	Log.d("swidget", "Everything looks just fine");
    }
    Log.d("swidget", "Lol!");
}


public static class UpdateService extends Service {
    @Override
    public void onStart(Intent intent, int startId) {
    Log.d("swidget", "Updating...");
    
    RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(), R.layout.main);
    views.setString(R.id.button1, "setText", "Koo");
    
    }
    @Override
    public IBinder onBind(Intent intent) {
        // We don't need to bind to this service
        return null;
    }

}

@Override
public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    final int N = appWidgetIds.length;
    
        Intent intent = new Intent(context, UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
        
        views.setOnClickPendingIntent(R.id.button1, pendingIntent);
        
        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


@Override
public void onReceive(Context context, Intent intent) {
	super.onReceive(context, intent);
    Log.i("Custom", "Recieve");
}

}
        
        
