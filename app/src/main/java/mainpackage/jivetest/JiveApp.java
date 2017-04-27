package mainpackage.jivetest;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orm.SugarContext;

import mainpackage.jivetest.support.Scopes;
import mortar.MortarScope;

public class JiveApp extends Application {

    private MortarScope rootScope;

    @Override public Object getSystemService(String name) {
        if (rootScope == null) rootScope = MortarScope.buildRootScope().build(Scopes.APP);
        return rootScope.hasService(name) ? rootScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
