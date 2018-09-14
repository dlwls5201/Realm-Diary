package com.tistory.black_jin0427.realmmvp;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.tistory.black_jin0427.realmmvp.model.Person;

import java.util.jar.Attributes;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by ifamily on 2018-01-15.
 */
public class MyApplication extends Application {

    public static boolean DEBUG = false;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(3)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

                        RealmSchema schema = realm.getSchema();

                        if(oldVersion == 1) {

                            RealmObjectSchema mPersonSchema = schema.get("Person");
                            mPersonSchema.addField("job", String.class);

                            oldVersion++;
                        }

                        if(oldVersion == 2) {

                            schema.create("DiaryDetail")
                                    .addField("date", String.class, FieldAttribute.REQUIRED)
                                    .addField("title", String.class, FieldAttribute.REQUIRED)
                                    .addField("description", String.class)
                                    .addField("number", int.class);

                        }

                    }
                })
                .build();

        Realm.setDefaultConfiguration(config);

        this.DEBUG = isDebuggable(this);

    }

    /**
     * get Debug Mode
     *
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
			/* debuggable variable will remain false */
        }

        return debuggable;
    }
}
