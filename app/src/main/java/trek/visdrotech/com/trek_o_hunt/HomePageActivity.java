package trek.visdrotech.com.trek_o_hunt;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import android.os.Bundle;
import com.hypertrack.lib.HyperTrack;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import trek.visdrotech.com.trek_o_hunt.utils.RecyclerAdapter;
import trek.visdrotech.com.trek_o_hunt.utils.serverRestClient;

import trek.visdrotech.com.trek_o_hunt.utils.Utils;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab1, fab2, fab3;

    private RecyclerView rvTopPicks, rvRegion;
    private RecyclerView.Adapter adapterTop, adapterRegion;
    private RecyclerView.LayoutManager layoutManagerTop, layoutManagerRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fab1 = (FloatingActionButton) findViewById(R.id.fabStartTrek);
        fab2 = (FloatingActionButton) findViewById(R.id.fabPauseTrek);
        fab3 = (FloatingActionButton) findViewById(R.id.fabStopTrek);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        HyperTrack.getCurrentLocation(new HyperTrackCallback() {
//            @Override
//            public void onSuccess(@NonNull SuccessResponse response) {
//                response.get
//                Log.d("Home",response.getResponseObject(). .toString());
//            }
//
//            @Override
//            public void onError(@NonNull ErrorResponse errorResponse) {
//                Log.d("Home",errorResponse.getErrorMessage());
//            }
//        });
        AsyncHttpResponseHandler handlerTopPicks = new JsonHttpResponseHandler()
        {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Trek> treks = new ArrayList<>();
                for(int i  = 0 ; i < response.length(); i++)
                {
                    try{
                        treks.add(new Trek(response.getJSONObject(i)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                adapterTop = new RecyclerAdapter(treks);
                rvTopPicks.setAdapter(adapterTop);
            }
        };

        AsyncHttpResponseHandler handlerArea = new JsonHttpResponseHandler()
        {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Trek> treks = new ArrayList<>();
                for(int i  = 0 ; i < response.length(); i++)
                {
                    try{
                        treks.add(new Trek(response.getJSONObject(i)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                adapterRegion = new RecyclerAdapter(treks);
                rvRegion.setAdapter(adapterRegion);
            }
        };
        rvTopPicks = (RecyclerView) findViewById(R.id.rvTop);
        rvRegion = (RecyclerView) findViewById(R.id.rvTopInRegion);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvTopPicks.setHasFixedSize(true);
        rvRegion.setHasFixedSize(true);

        // use a linear layout manager
        layoutManagerTop = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerRegion = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTopPicks.setLayoutManager(layoutManagerTop);
        rvRegion.setLayoutManager(layoutManagerRegion);

        RequestParams paramsTop = new RequestParams();
        RequestParams paramsArea = new RequestParams();

        serverRestClient.post("",paramsTop, handlerTopPicks);
        serverRestClient.post("",paramsArea, handlerArea);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
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
            return true;
        }
        else if (id == R.id.action_logout) {
            Utils.logout(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
