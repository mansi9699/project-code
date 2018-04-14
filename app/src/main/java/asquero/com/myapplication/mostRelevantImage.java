package asquero.com.myapplication;

/**
 * Created by srajan on 14/4/18.
 */

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class mostRelevantImage {

    public String url1 = "", url2 = "";
    public String curl = "";
    //public String csource = "";
    //private Context context;
    //private static final String TAG1 = "d1";
    //private static final String TAG2 = "d2";
    //private static final String TAG3 = "url";
    private static final String TAG4 = "curl";

    public String findMostPerfectImage(String cc, String cn, String cs, String ce, Context live, int a){

        String con = cc + " " + cn;

        try {

            JSONObject imagesObj = new JSONObject(loadJSONFromAsset(live));

            JSONArray imagesArray = imagesObj.getJSONArray("imagesJson");

            double d1 = 0,d2=0;

            JSONObject imagesListObj1 = imagesArray.getJSONObject(0);


            url1 = imagesListObj1.getString("Url");
            JSONArray keyList1 = (JSONArray) imagesListObj1.get("Keywords");

            for (int b = 0; b < keyList1.length(); b++) {

                String keyStr1 = keyList1.getString(b);
                d1 = d1 + distance(keyStr1, con);
            }

            curl = url1;
            for(int i = 1 ; i < imagesArray.length() ; i++) {

                    d2 = 0;

                    JSONObject imagesListObj2 = imagesArray.getJSONObject(i);

                    url2 = imagesListObj2.getString("Url");
                    JSONArray keyList2 = (JSONArray) imagesListObj2.get("Keywords");

                    for (int b = 0; b < keyList2.length(); b++) {

                        String keyStr2 = keyList2.getString(b);
                        d2 = d2 + distance(keyStr2, con);
                    }

                    if (d2 < d1) {
                        curl = url2;
                        d1 = d2;
                    }
                }


            Log.i(TAG4, ""+curl+" "+d1+" "+d2);
            return curl;

        }
        catch (Exception e){

            e.printStackTrace();

            return "https://blog.sqlauthority.com/i/a/errorstop.png";

        }
    }

    public static double distance(String a, String b)
    {
        a = a.toLowerCase();
        b = b.toLowerCase();
        double[] costs = new double[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++)
        {
            costs[0] = i;
            double nw = i - 1;
            for (int j = 1; j <= b.length(); j++)
            {
                double cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public String loadJSONFromAsset(Context live) {
        String json = null;
        try {
            InputStream is = live.getAssets().open("images.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
