package li.withliyh.bestcoins;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.TitlePageIndicator;
import java.util.ArrayList;

import li.withliyh.bestcoins.adapter.TabsAdapter;
import li.withliyh.bestcoins.animate.ColorAnimationDrawable;
import li.withliyh.bestcoins.fragment.main.HomeFragment;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;

    private TitlePageIndicator mTitleIndicator;


    private final Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawerMenu();
        initPageIndicator();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initDrawerMenu() {
        mDrawer = (LinearLayout)findViewById(R.id.my_awesome_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open,R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle("Home");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Nav");
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        TextView menuTitle = new TextView(this);
        menuTitle.setText("Menu");

        mDrawer.addView(menuTitle);
    }


    private void initPageIndicator() {
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        TabsAdapter adapter = new TabsAdapter(this, getSupportFragmentManager());
        Bundle args1 = new Bundle();
        args1.putString("key", "a");

        Bundle args2 = new Bundle();
        args2.putString("key", "b");

        Bundle args3 = new Bundle();
        args3.putString("key", "c");

        Bundle args4 = new Bundle();
        args4.putString("key", "d");

        adapter.addTab(HomeFragment.class, args1);
        adapter.addTab(HomeFragment.class, args2);
        adapter.addTab(HomeFragment.class, args3);
        adapter.addTab(HomeFragment.class, args4);
        mViewPager.setAdapter(adapter);


        mTitleIndicator = (TitlePageIndicator)findViewById(R.id.pageTitle);
        mTitleIndicator.setViewPager(mViewPager);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean open = mDrawerLayout.isDrawerOpen(mDrawer);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) return true;
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            mHandler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            mHandler.removeCallbacks(null);
        }
    };

    public static class TabsAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();


        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _clss, Bundle _args) {
                clss = _clss;
                args = _args;
            }
        }

        public TabsAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        public void addTab(Class<?> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            mTabs.add(info);
            notifyDataSetChanged();
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }





        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return mTabs.size();
        }
    }
}
