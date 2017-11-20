package com.example.geogr.sportevents;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by geogr on 15.11.2017.
 */

public class Application extends android.app.Application {
    VKAccessTokenTracker vkAccessToken=new VKAccessTokenTracker(){

        @Override
        public void onVKAccessTokenChanged(@Nullable VKAccessToken oldToken, @Nullable VKAccessToken newToken) {
                if(newToken==null){
                    Toast.makeText(Application.this, "Acces invalidate", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Application.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessToken.startTracking();
        VKSdk.initialize(this);
    }

}
