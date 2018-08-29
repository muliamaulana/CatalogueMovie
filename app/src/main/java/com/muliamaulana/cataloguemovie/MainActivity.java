package com.muliamaulana.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.muliamaulana.cataloguemovie.adapter.TabPagerAdapter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupTab();
        setTab(1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ((!searchView.isIconified())) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.label_search_movie));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String querySearch) {
                Intent intentSearch = new Intent(getApplicationContext(), SearchActivity.class);
                intentSearch.putExtra(SearchActivity.MOVIE_TITLE, querySearch);
                startActivity(intentSearch);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upcoming) setTab(0);
        if (id == R.id.nav_nowplaying) setTab(1);
        if (id == R.id.nav_popular) setTab(2);

        if (id == R.id.nav_favorite){
            Intent intentFavorite = new Intent(MainActivity.this,FavoriteActivity.class);
            startActivity(intentFavorite);
        }
        if (id == R.id.nav_settings) {
            Intent intentSettings = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intentSettings);
        }
        if (id == R.id.nav_share) {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");
            intentShare.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_msg_app)+"\n\n"+getResources().getString(R.string.referal_link));
            startActivity(Intent.createChooser(intentShare,getResources().getString(R.string.label_share)));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupTab() {
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(R.string.upcoming);
        tabLayout.getTabAt(1).setText(R.string.now_playing);
        tabLayout.getTabAt(2).setText(R.string.popular);
        tabLayout.setOnTabSelectedListener(this);
    }

    public void setTab(int tabNumb) {
        tabLayout.getTabAt(tabNumb).select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        selectNav(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void selectNav(int navNumber) {
        navigationView.getMenu().getItem(navNumber).setChecked(true);
    }

}
