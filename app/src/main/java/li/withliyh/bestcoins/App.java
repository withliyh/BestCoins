package li.withliyh.bestcoins;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Administrator on 2015/5/18.
 */
public class App extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
