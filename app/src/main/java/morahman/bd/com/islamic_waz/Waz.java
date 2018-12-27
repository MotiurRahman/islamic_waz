package morahman.bd.com.islamic_waz;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Waz extends AppCompatActivity {


    private RecyclerView recyclerView_2;
    private RecyclerView.Adapter adapter;
    private List<ListWaz> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView_2 =(RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView_2.setHasFixedSize(true);
        recyclerView_2.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        loadRecyclerViewData();


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3F51B5"));
        }

        String speakerName = getIntent().getStringExtra("name");

        setTitle("বক্তাঃ "+speakerName);

    }

    private void loadRecyclerViewData() {

        String speakerName = getIntent().getStringExtra("name");



        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("islamicwaz.herokuapp.com")
                .appendPath("api")
                .appendPath("audioBySpeaker")
                .appendPath("speaker")
                .appendPath(speakerName);

        String myUrl = builder.build().toString();


        //String URL_DATA = "http://islamicwaz.org/api/speakerName";
        Log.d("URL Valu","URL:"+myUrl);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loding the data");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, myUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                try {

                    JSONArray array = new JSONArray(s);

                    Log.e("Data kength","Data Length:"+array.length());

                    if(array.length()==0){
                        Toast.makeText(getApplicationContext(), "Waz is not been inserted yet for this Speaker", Toast.LENGTH_LONG).show();
                    }


                    for (int i = 0; i < array.length(); i++) {
                         JSONObject jsonObject = array.getJSONObject(i);
                        //String data = array.getString(i);
                        Log.e("Was Title","Title:"+jsonObject.getString("name"));

                        // ListItem item = new ListItem(jsonObject.getString("name"),jsonObject.getString("bio"),jsonObject.getString("imageurl"));
                        ListWaz item = new ListWaz(jsonObject.getString("name"),jsonObject.getString("title"), jsonObject.getString("url"));

                        // listItems.add(item);

                        listItems.add(item);


                    }
                    adapter = new WazAdapter(listItems,getApplicationContext());
                    recyclerView_2.setAdapter(adapter);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
