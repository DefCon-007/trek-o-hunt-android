package trek.visdrotech.com.trek_o_hunt.utils;


import android.app.Application;
import com.hypertrack.lib.HyperTrack;
import android.content.res.Configuration;

/**
 * Created by defcon on 09/06/18.
 */

public class Trekapplication extends Application {

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        HyperTrack.initialize(this, "pk_7bd35faaa9625b7638f0cb2f7b14d51a2d683243");
        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
