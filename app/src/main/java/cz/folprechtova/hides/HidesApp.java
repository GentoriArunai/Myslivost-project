package cz.folprechtova.hides;


import android.app.Application;
import android.content.Context;

public class HidesApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext(); //uložíme si aplikační kontext do singletonu HidesApp
    }
}
