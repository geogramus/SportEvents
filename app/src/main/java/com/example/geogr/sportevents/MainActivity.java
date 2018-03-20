package com.example.geogr.sportevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.geogr.sportevents.api.Controller;
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

    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
       prefs=this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);


        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                switch (res){
                    case LoggedIn:  list();break;
                    case LoggedOut: showLogin(); break;


                }
            }

            @Override
            public void onError(VKError error) {

            }
        });

    }


    private void showLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commitAllowingStateLoss();
    }
    private  void cityselect(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SelectCityFragment(MainActivity.this))
                .commitAllowingStateLoss();
    }
    private  void list(){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SportEventsListFragment())
                .commitAllowingStateLoss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                logoutMI.setVisible(true);
                cityselect();
            }
            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static class LoginFragment extends android.support.v4.app.Fragment {
        public LoginFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.login_fragment, container, false);
            v.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VKSdk.login(getActivity(), myscope);
                }
            });
            return v;
        }

    }
    static  MenuItem logoutMI;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        logoutMI = menu.findItem(R.id.city_change);
        logoutMI.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            VKSdk.logout();
            Intent intent=new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.city_change){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new SelectCityFragment(MainActivity.this))
                    .commitAllowingStateLoss();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String aa = prefs.getString("url", "http://sportevents.getsandbox.com/");
                Controller.setBaaseUrl(aa);
    }

    @Override
    protected void onStop() {
        super.onStop();
        String aa=Controller.getBaaseUrl();
        prefs.edit().putString("url", aa).apply();
    }
}
