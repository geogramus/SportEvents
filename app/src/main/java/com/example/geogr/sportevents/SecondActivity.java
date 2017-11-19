package com.example.geogr.sportevents;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vk.sdk.VKSdk;

public class SecondActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
       // toolbar=(Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

    }
    private ViewPager pages;
    @Override
    protected void onResume() {
        super.onResume();
        pages=(ViewPager) findViewById(R.id.pages);
        pages.setAdapter(new SecondActivityPagerAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            VKSdk.logout();
            Intent intent=new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        };
        return super.onOptionsItemSelected(item);
    }

    private class SecondActivityPagerAdapter extends FragmentPagerAdapter {
        private final String[] types={"list"};
        private final String[]   titles={"SportList", "AddNewEventItems"};
        SecondActivityPagerAdapter(){
            super(getSupportFragmentManager());

        }
        @Override
        public Fragment getItem(int position) {

                return new SportEventsListFragment();

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
