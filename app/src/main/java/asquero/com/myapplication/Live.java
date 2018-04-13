package asquero.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
/*import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.github.silvestrpredko.dotprogressbar.DotProgressBarBuilder;*/
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Live extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter liveListAdapter;

    private List<LiveList> listLive;
    private String JSON_URL = "http://codersdiary-env.jrpma4ezhw.us-east-2.elasticbeanstalk.com/codechef/?cstatus=1&format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        getSupportActionBar().setTitle("Live");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listLive = new ArrayList<>();

        /*for (int i = 0; i< 20 ; i++){
            LiveList liveLists = new LiveList("DummyCode"+i,"DummyName"+i,"0","0","DummyName"+i);
            listLive.add(liveLists);
        }*/

        /*liveListAdapter = new LiveListAdapter(listLive,Live.this);
        recyclerView.setAdapter(liveListAdapter);*/
        loadLiveData();

        /*try {
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").placeholder(R.drawable.upcoming).error(R.drawable.ended).into((ImageView) findViewById(R.id.imageView));
        }
        catch (java.lang.RuntimeException e){
            Toast.makeText(getApplicationContext(), "Picasso Failed", Toast.LENGTH_SHORT).show();
        }*/

    }


    public void loadLiveData(){

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //progressBar.setVisibility(View.INVISIBLE);

                        try {

                            //JSONObject jsonObj = new JSONObject(response);

                            //JSONArray jsonArray = new JSONArray(response);

                            //if(jsonArray.length() <= 0)
                            //{
                            //Toast.makeText(getApplicationContext(), "before for", Toast.LENGTH_SHORT).show();
                            //}

                            for(int i = 0 ; i < response.length() ; i++) {

                                JSONObject liveJsonObj = response.getJSONObject(i);

                                /*Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), liveJsonObj.toString(), Toast.LENGTH_SHORT).show();*/

                                String code = liveJsonObj.getString("ccode_codechef");
                                String name = liveJsonObj.getString("cname_codechef");
                                String startdate = liveJsonObj.getString("cstartdate_codechef");
                                String enddate = liveJsonObj.getString("cenddate_codechef");
                                //int status = liveJsonObj.getInt("codechef_cstatus");

                                /*Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), startdate, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), enddate, Toast.LENGTH_SHORT).show();*/

                                LiveList liveListObj = new LiveList(code, name, startdate, enddate, name);

                                listLive.add(liveListObj);
                            }

                            liveListAdapter = new LiveListAdapter(listLive,Live.this);
                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setAdapter(liveListAdapter);

                            //Picasso.get().load("https://www.teachermagazine.com.au/files/ce-image/cache/1c03ffc10fd4ef6a/Computing-programming-and-coding-in-schools_855_513_48.jpg").into((ImageView) findViewById(R.id.imageView));

                        } catch (Exception e) {

                            e.printStackTrace();

                        }
                    }
                },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        if(!networkConnectivity()){
                            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);


        /*liveListAdapter = new LiveListAdapter(listLive,Live.this);
        //progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(liveListAdapter);*/

    }

    public boolean networkConnectivity(){

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());

    }

    /*public void doProgressBar(){

        dotProgressBar.setStartColor(startColor);
        dotProgressBar.setEndColor(endColor);
        dotProgressBar.setDotAmount(amount);
        dotProgressBar.setAnimationTime(time);

// or you can use builder

        DotProgressBarBuilder dpbb = new DotProgressBarBuilder(findViewById(R.list_layout.dot_progress_bar))
                .setDotAmount(5)
                .setStartColor(Color.BLACK)
                .setAnimationDirection(DotProgressBar.LEFT_DIRECTION)
                .build();
    }*/
}
