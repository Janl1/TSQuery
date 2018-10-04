package de.janl1.tsquery;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.janl1.tsquery.Fragments.ChannelsFragment;
import de.janl1.tsquery.Fragments.LoginFragment;
import de.janl1.tsquery.Fragments.ServerFragment;
import de.janl1.tsquery.Fragments.ServerinfoFragment;
import de.janl1.tsquery.Fragments.UsersFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FragmentManager fm;
    NavigationView navigationView;
    TextView currentServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                updateCurrentServer();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        currentServer = (TextView)header.findViewById(R.id.currentServer);

        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new ServerFragment()).commit();
        setTitle("TSQuery | Servers");

        pref = getSharedPreferences("TSQUERY", Context.MODE_PRIVATE);
        editor = pref.edit();
        updateCurrentServer();
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
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if(id == android.R.id.home) {
            updateCurrentServer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.app_users) {
            setTitle("TSQuery | Clients");
            fm.beginTransaction().replace(R.id.content_frame, new UsersFragment()).commit();
        } else if (id == R.id.app_channels) {
            setTitle("TSQuery | Channels");
            fm.beginTransaction().replace(R.id.content_frame, new ChannelsFragment()).commit();
            setTitle("TSQuery | Serverinfo");
        } else if (id == R.id.app_serverinfo) {
            fm.beginTransaction().replace(R.id.content_frame, new ServerinfoFragment()).commit();
        } else if (id == R.id.app_query_login) {
            setTitle("TSQuery | Add server");
            editor.putString("serveredit_mode", "ADD_SERVER");
            editor.commit();
            fm.beginTransaction().replace(R.id.content_frame, new LoginFragment()).commit();
        } else if(id == R.id.app_servers) {
            setTitle("TSQuery | Servers");
            fm.beginTransaction().replace(R.id.content_frame, new ServerFragment()).commit();
        }

        updateCurrentServer();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateCurrentServer()
    {
        if(pref.getString("selectedserver", "").equals("")) {
            currentServer.setText("Server: None");
            return;
        }

        try {
            JSONArray servers = new JSONArray(pref.getString("serverconfig", ""));

            for (int i = 0; i < servers.length(); i++)
            {
                JSONObject jsob = new JSONObject(servers.get(i).toString());
                if(jsob.getString("v_login_host").equals(pref.getString("selectedserver", "").split(":")[0]) && jsob.getString("v_login_port").equals(pref.getString("selectedserver", "").split(":")[1])) {
                    currentServer.setText("Server: " + jsob.getString("v_label"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            currentServer.setText("Server: None");
        }
    }
}
