package asquero.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Upcoming extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter upcomingListAdapter;

    private List<UpcomingList> listUpcoming;

    private Context context = this;

    private String JSON_Codechef_URL = "http://codersdiary-env.jrpma4ezhw.us-east-2.elasticbeanstalk.com/codechef/?cstatus=0&format=json";
    private String JSON_Spoj_URL = "http://codersdiary-env.jrpma4ezhw.us-east-2.elasticbeanstalk.com/spoj/?cstatus=0&format=json";
    private String JSON_Hackerrank_URL = "http://codersdiary-env.jrpma4ezhw.us-east-2.elasticbeanstalk.com/hackerrank/?cstatus=0&format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        getSupportActionBar().setTitle("Upcoming");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listUpcoming = new ArrayList<>();

        /*for (int i = 0; i< 20 ; i++){
            UpcomingList upcomingLists = new UpcomingList("DummyCode"+i,"DummyName"+i,"0","0","DummyName"+i);
            listUpcoming.add(upcomingLists);
        }

        upcomingListAdapter = new UpcomingListAdapter(listUpcoming,Upcoming.this);
        recyclerView.setAdapter(upcomingListAdapter);*/

        loadUpcomingData();
    }


    public void loadUpcomingData() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView searchingdata = (TextView) findViewById(R.id.searchingData);
        progressBar.setVisibility(View.VISIBLE);
        searchingdata.setVisibility(View.VISIBLE);
        int c = 0;
        //Codechef data request*************************************************************************************************

        dataRequest(JSON_Codechef_URL, "codechef", c++, progressBar, searchingdata);


        //Spoj data request*****************************************************************************************************

        dataRequest(JSON_Spoj_URL, "spoj", c++, progressBar, searchingdata);

        //Hackerrank data request***********************************************************************************************

        dataRequest(JSON_Hackerrank_URL, "hackerrank", c++, progressBar, searchingdata);

        //progressBar.setVisibility(View.INVISIBLE);

    }

    public void dataRequest(String JSON_URL, final String site, final int c, final ProgressBar progressBar, final TextView searchingData) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try {


                            for (int i = 0; i < response.length(); i++) {

                                JSONObject upcomingJsonObj = response.getJSONObject(i);

                                /*Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), liveJsonObj.toString(), Toast.LENGTH_SHORT).show();*/

                                String code = upcomingJsonObj.getString("ccode_" + site);
                                String name = upcomingJsonObj.getString("cname_" + site);
                                String startdate = upcomingJsonObj.getString("cstartdate_" + site);
                                String enddate = upcomingJsonObj.getString("cenddate_" + site);
                                //int status = liveJsonObj.getInt("codechef_cstatus");

                                /*Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), startdate, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), enddate, Toast.LENGTH_SHORT).show();*/

                                //String imageUrl = "https://edsurge.imgix.net/uploads/post/image/7747/Kids_coding-1456433921.jpg?auto=compress%2Cformat&w=2000&h=810&fit=crop";
                                mostRelevantImage mri = new mostRelevantImage();
                                String url = mri.findMostPerfectImage(code, name, startdate, enddate, context, i);

                                if (url.isEmpty()) {
                                    url = "https://www.computerhope.com/jargon/e/error.gif";
                                }

                                UpcomingList upcomingListObj = new UpcomingList(code, name, startdate, enddate, url, name);

                                listUpcoming.add(upcomingListObj);
                            }

                            upcomingListAdapter = new UpcomingListAdapter(listUpcoming, Upcoming.this);

                            Log.i("counter progressbar", ""+c);
                            if(c == 2) {
                                progressBar.setVisibility(View.INVISIBLE);
                                searchingData.setVisibility(View.INVISIBLE);
                            }
                            recyclerView.setAdapter(upcomingListAdapter);


                        } catch (Exception e) {

                            e.printStackTrace();

                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        if (!networkConnectivity()) {
                            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public boolean networkConnectivity() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());

    }
}
