package morahman.bd.com.islamicWazPro;

import com.google.android.material.navigation.NavigationView;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String URL_DATA = "https://islamicwaz.herokuapp.com/api/speakerName";
    public static View.OnClickListener myOnClickListner;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // listItems = new ArrayList<>();
        loadRecyclerViewData();

        myOnClickListner = new MyonclickListner(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (isNetworkConnected()) {

        } else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }

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

    //For internet connection

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    //End internet connection

    private void loadRecyclerViewData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Data is Loadding");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                try {

                    JSONArray jsonArr = new JSONArray(s);

                    List<String> jsonValues = new ArrayList<String>();
                    for (int i = 0; i < jsonArr.length(); i++) {
                        jsonValues.add(jsonArr.getString(i));
                    }

                    Collections.sort(jsonValues, Collections.<String>reverseOrder());

                    if (jsonValues != null) {

                        adapter = new MyAdapter(jsonValues, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    } else {
                        loadRecyclerViewData();
                    }


                    //Log.e("Fetch URL Data Test","DataLength:"+array.length());
                    //Toast.makeText(MainActivity.this, +array.length(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error Message", error.getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // Open webview

    public void openWeb(View view) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://islamicwaz.herokuapp.com"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.refrash) {

            if (isNetworkConnected()) {
                loadRecyclerViewData();
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }


        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            if (isNetworkConnected()) {
                Intent devAccount = new Intent(Intent.ACTION_VIEW);
                devAccount.setData(Uri.parse("market://details?id=morahman.bd.com.islamicWazPro"));
                startActivity(devAccount);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }

        }

        if (id == R.id.action_ratings) {

            if (isNetworkConnected()) {
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.setData(Uri.parse("market://details?id=morahman.bd.com.islamicWazPro"));
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }


        }

        if (id == R.id.action_more) {

            if (isNetworkConnected()) {
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.setData(Uri.parse("https://play.google.com/store/apps/dev?id=7955052953183373879"));
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }


        }


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ageCal) {
            // Handle the camera action
            if (isNetworkConnected()) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("market://details?id=com.bd.agecalculatorPro"));
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.bdResult) {
            if (isNetworkConnected()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=motiur_bdresult.bd.com.bdresultpro"));
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.pass) {
            if (isNetworkConnected()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.bd.PasswordManagerPro"));
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyonclickListner implements View.OnClickListener {

        public MyonclickListner(MainActivity mainActivity) {
        }

        @Override
        public void onClick(View v) {

            int selectedItemPosition = recyclerView.getChildAdapterPosition(v);

            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.speakerName);
            LinearLayout layout = (LinearLayout) viewHolder.itemView.findViewById(R.id.linearLayout);
            String selectName = (String) textView.getText();
            Intent intent = new Intent(v.getContext(), Waz.class);
            intent.putExtra("name", selectName);
            v.getContext().startActivity(intent);
        }
    }
}
