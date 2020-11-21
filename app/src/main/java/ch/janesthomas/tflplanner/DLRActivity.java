package ch.janesthomas.tflplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ch.janesthomas.tflplanner.adapters.DLRAdapter;
import ch.janesthomas.tflplanner.models.DLRModel;
import ch.janesthomas.tflplanner.models.StopModel;
import ch.janesthomas.tflplanner.rest.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class DLRActivity extends AppCompatActivity {

    ListView list_dlr;
    ListView list_stops;
    TextView text_warning;

    ArrayList<DLRModel> dlrModels;
    private static DLRAdapter dlrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tube);

        list_dlr = (ListView) findViewById(R.id.list_tubes);
        list_stops = (ListView) findViewById(R.id.list_stops);
        text_warning = (TextView) findViewById(R.id.text_warning);
        text_warning.setText("");
        list_dlr.setBackgroundColor(getResources().getColor(R.color.colorCorporateWhite));

        list_stops.setVisibility(View.GONE);
        dlrModels = setDLRList();

        if (dlrModels != null) {
            if (!dlrModels.isEmpty()) {
                dlrAdapter = new DLRAdapter(dlrModels, getApplicationContext());

                list_dlr.setAdapter(dlrAdapter);
                list_dlr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DLRActivity.this.onItemClick(adapterView, view, i, l);
                    }
                });
            } else {
                list_dlr.setBackgroundColor(getResources().getColor(R.color.colorCorporateBlue));
                text_warning.setText(R.string.error_empty);
            }
        } else {
            list_dlr.setBackgroundColor(getResources().getColor(R.color.colorCorporateBlue));
            text_warning.setText(R.string.error_loading);
        }

    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(i);

        final String dlrId = dlrModels.get(i).getId();
        final Gson gson = new Gson();

        RequestParams rp = new RequestParams();
        Log.d("LinienURL", "https://api.tfl.gov.uk/line/" + dlrId + "/stoppoints?app_id=" + BuildConfig.APIID + "&app_key=" + BuildConfig.APIKEY);

        HttpUtils.get("line/" + dlrId + "/stoppoints?app_id=" + BuildConfig.APIID + "&app_key=" + BuildConfig.APIKEY, rp, new JsonHttpResponseHandler() {
            boolean isLoading = false;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                isLoading=false;
                Log.i(DLRAdapter.class.getName(), "Request was successful.");

                Intent intent = new Intent(DLRActivity.this, StopActivity.class);
                ArrayList<StopModel> temp = setStopList(timeline.toString());
                intent.putExtra("stoppoints", gson.toJson(temp));
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(DLRActivity.class.getName(), "Request failed. Here is why:");
                Log.i(DLRActivity.class.getName(), Integer.toString(statusCode));
                Log.i(DLRActivity.class.getName(), responseString);

            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("Retry Number: " + retryNo);
            }

            @Override
            public void onCancel() {
                Log.e(DLRActivity.class.getName(), "CANCELLED");
            }
        });
        Log.e("ONCLICK", Integer.toString(view.getId()));
    }

    private ArrayList<StopModel> setStopList(String jsonArray) {

        ArrayList<StopModel> content = new ArrayList<>();
        JSONArray stopArray = null;

        try {
            stopArray = new JSONArray(jsonArray);
            Log.i(StopActivity.class.getName(), stopArray.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int counter = 0; counter < stopArray.length(); counter++) {
            try {
                final String id = stopArray.getJSONObject(counter).getString("id");
                final String commonName = stopArray.getJSONObject(counter).getString("commonName");
                final double lat = stopArray.getJSONObject(counter).getDouble("lat");
                final double lon = stopArray.getJSONObject(counter).getDouble("lon");

                StopModel model = new StopModel(id, commonName, lat, lon);

                content.add(model);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return content;
    }



    private ArrayList<DLRModel> setDLRList() {

        ArrayList<DLRModel> content = new ArrayList<>();

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("dlr");
        JSONArray dlrArray = null;

        try {
            dlrArray = new JSONArray(jsonArray);
            Log.i(DLRActivity.class.getName(), dlrArray.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int counter = 0; counter < dlrArray.length(); counter++) {
            try {
                final String id = dlrArray.getJSONObject(counter).getString("id");
                final String name = dlrArray.getJSONObject(counter).getString("name");

                DLRModel model = new DLRModel(id, name);

                content.add(model);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return content;
    }
}
