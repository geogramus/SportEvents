package com.example.geogr.sportevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

public class MainActivity extends AppCompatActivity {
    private static final String[] myscope= new String[]{

            VKScope.NOHTTPS,
            VKScope.GROUPS

    };
    private Button enter;
    Boolean isResumed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        isResumed=false;
        enter=(Button) findViewById(R.id.enter);
        enter.setVisibility(View.GONE);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(MainActivity.this, myscope);
            }
        });
        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                switch (res){
                    case LoggedIn:  StartSecondActivity();break;
                    case LoggedOut: enter.setVisibility(View.VISIBLE); break;

                }
            }

            @Override
            public void onError(VKError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                StartSecondActivity();
            }
            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void StartSecondActivity(){
        Intent intent= new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }


}
