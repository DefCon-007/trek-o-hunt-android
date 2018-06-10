package trek.visdrotech.com.trek_o_hunt;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import trek.visdrotech.com.trek_o_hunt.utils.RecyclerAdapter;
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
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        TrekDescriptionFragment.boughtTrek = false;


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
            }
        };

        AsyncHttpResponseHandler handlerArea = new JsonHttpResponseHandler()
        {
            @Override
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

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TEST", "" + statusCode + " "+responseString);

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

//        RequestParams paramsTop = new RequestParams();
//        RequestParams paramsArea = new RequestParams();
//
//        serverRestClient.get("http://104.199.139.155:8000//trekohunt/get_top_treks",paramsTop, handlerTopPicks);
//        serverRestClient.get("http://104.199.139.155:8000/trekohunt/get_local_treks",paramsArea, handlerArea);

        ArrayList<Trek> treks = generateData();
        adapterRegion = new RecyclerAdapter(treks, this);
        adapterTop = new RecyclerAdapter(treks, this);
        rvTopPicks.setAdapter(adapterTop);
        rvRegion.setAdapter(adapterRegion);

    }


    ArrayList<Trek> generateData()
    {
        ArrayList<Trek> treks = new ArrayList<>();
        for(int i = 0; i < 5 ; i++) {
            Trek trek = new Trek();
            trek.setImgUrl(getResources().getStringArray(R.array.images)[i]);
            trek.setName(getResources().getStringArray(R.array.names)[i]);
            trek.setAbout("Mullayanagiri at 6,330 feet is the highest peak in Karnataka and one of the most beautiful too. Lying in the middle of Bhadra Wildlife Sanctuary and just 20 kms from Chickmagaluru, the Mullayanagiri Trek has amazing vistas and panoramas to feast your eyes upon.\n" +
                    "On this trip you will complete not one but two treks – Mullayanagiri Trek (Sarpadhari to Mullayanagiri) & Baba Budangiri Trek (Baba Budangiri to Kavikal Gandi). The climax for the whole trip is the Blade Ridge where you walk across the ridge with the mountain falling down sharply on both the sides. With a cliff on one side and a steep hillside on the other, it feels like walking along a knife’s edge.\n" +
                    "Sitting pretty inside the forest itself, the campsite is as beautiful as they come. With a couple of waterfalls flowing inside the coffee estate itself, you will not feel like coming back from this amazing place.");
            trek.setDifficulty(Trek.TrekDifficulty.MEDIUM);
            trek.setCheckList("- Sunscreen, hat\n" +
                    "- Trekking Shoes/ Decent Sports Shoes\n" +
                    "- Torch with extra batteries (Please avoid using your phones as torches, they drain very fast and then you have neither a torch nor a phone)\n" +
                    "- A warm jacket in case it gets cold\n" +
                    "- Rainproof clothing in the rainy season\n" +
                    "- Insect repellent if you are prone to mosquito bites\n" +
                    "- Camera and batteries / charger\n" +
                    "- Prescription medicines, if any (And no, ganja is  not a medicine :P)");
            trek.setThingsToNote("- This is a no frills trip. You will love us if you are looking for an adventurous weekend and making new friends. People looking for a luxury holiday for relaxation, you can give this one a miss.\n" +
                    "- All aMadNomad trips follow a strict Leave No Trace policy. Please do not throw any plastic covers, bottles or any other garbage when you are travelling.\n" +
                    "- Our bus starts from Kalamandir Bus Stop, Marathahalli and travels via the Outer Ring Road so you can hop on anywhere on the route. You can see the route map by clicking here.\n" +
                    "- Cash and ATMs: The nearest city to Attigundi is Chickmagaluru. Although there are ATMs in Chickmagaluru, please do carry adequate cash for emergencies.\n" +
                    "- Leave your pre-conceived notions behind, travel with an open mind, and you’ll be pleasantly surprised with what you find.");
            trek.setPrice(500);
            trek.addImages(getResources().getStringArray(R.array.images)[i]);
            trek.addImages(getResources().getStringArray(R.array.images)[6]);
            trek.addImages(getResources().getStringArray(R.array.images)[7]);
            trek.setRating(3.5);
            trek.setStaticImgUrl("http://www.team-bhp.com/forum/attachments/route-travel-queries/615356d1317042469-weekend-trip-chikmagalur-ckm.png");
            treks.add(trek);
        }
        return treks;
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

        if (id == R.id.action_logout) {
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
            getSupportFragmentManager().beginTransaction().replace(R.id.layout, new BoughtTrekListFragment()).addToBackStack("").commit();
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
