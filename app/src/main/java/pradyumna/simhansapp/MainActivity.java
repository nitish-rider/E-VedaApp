package pradyumna.simhansapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import pradyumna.simhansapp.about.About;
import pradyumna.simhansapp.contact.Contact;
import pradyumna.simhansapp.developers.Developers;
import pradyumna.simhansapp.feedback.Feedback;
import pradyumna.simhansapp.instructions.Instructions;
import pradyumna.simhansapp.learn_veda.Learn_Veda;
import pradyumna.simhansapp.prabandham.Prabandham;
import pradyumna.simhansapp.prateekas.Prateekas;
import pradyumna.simhansapp.profile.Profile;
import pradyumna.simhansapp.sthotas.Sthotras;
import pradyumna.simhansapp.vedas.Vedas;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Status bar Color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.orangeMain));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///Setting home page default
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("vedas"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_veda) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, Learn_Veda.class);
            startActivity(intent);

        } else if (id == R.id.nav_abt) {

            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);

        } else if (id == R.id.nav_prab) {

            Intent intent = new Intent(MainActivity.this, Prabandham.class);
            startActivity(intent);

        } else if (id == R.id.nav_stho) {

            Intent intent = new Intent(MainActivity.this, Sthotras.class);
            startActivity(intent);

        } else if (id == R.id.nav_pra) {

            Intent intent = new Intent(MainActivity.this, Prateekas.class);
            startActivity(intent);

        } else if (id == R.id.nav_con) {

            Intent intent = new Intent(MainActivity.this, Contact.class);
            startActivity(intent);

        } else if (id == R.id.nav_feed) {

            Intent intent = new Intent(MainActivity.this, Feedback.class);
            startActivity(intent);

        } else if (id == R.id.nav_ins) {

            Intent intent = new Intent(MainActivity.this, Instructions.class);
            startActivity(intent);

        } else if (id == R.id.nav_dev) {

            Intent intent = new Intent(MainActivity.this, Developers.class);
            startActivity(intent);

        } else if (id == R.id.nav_privacy_policy) {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/simhansevedashree/home"));
            startActivity(browserIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        int mTabs;

        public ViewPagerAdapter(@NonNull FragmentManager fm, int mTabs) {
            super(fm);
            this.mTabs = mTabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Profile();
                case 1:
                    return new Vedas();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mTabs;
        }
    }
}