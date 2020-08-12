package ch.janesthomas.tflplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ch.janesthomas.tflplanner.adapters.StopAdapter;
import ch.janesthomas.tflplanner.adapters.TubeAdapter;
import ch.janesthomas.tflplanner.models.StopModel;
import ch.janesthomas.tflplanner.models.TubeModel;
import ch.janesthomas.tflplanner.rest.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class TubeActivity extends AppCompatActivity {

    ListView list_tubes;
    ListView list_stops;
    TextView text_warning;

    ArrayList<TubeModel> tubeModels;
    ArrayList<StopModel> stopModels;
    private static TubeAdapter tubeAdapter;
    private static StopAdapter stopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tube);

        list_tubes = (ListView) findViewById(R.id.list_tubes);
        list_stops = (ListView) findViewById(R.id.list_stops);
        text_warning = (TextView) findViewById(R.id.text_warning);
        text_warning.setText("");
        list_tubes.setBackgroundColor(getResources().getColor(R.color.colorCorporateWhite));

        list_stops.setVisibility(View.GONE);
        tubeModels = setTubeList();

        if (tubeModels != null) {
            if (!tubeModels.isEmpty()) {
                tubeAdapter = new TubeAdapter(tubeModels, getApplicationContext());

                list_tubes.setAdapter(tubeAdapter);
                list_tubes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TubeActivity.this.onItemClick(adapterView, view, i, l);
                    }
                });
            } else {
                list_tubes.setBackgroundColor(getResources().getColor(R.color.colorCorporateBlue));
                text_warning.setText(R.string.error_empty);
            }
        } else {
            list_tubes.setBackgroundColor(getResources().getColor(R.color.colorCorporateBlue));
            text_warning.setText(R.string.error_loading);
        }

    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final boolean isLoading = false;
        System.out.println(i);

        final String tubeId = tubeModels.get(i).getId();

        RequestParams rp = new RequestParams();
        Log.d("LinienURL", "https://api.tfl.gov.uk/line/" + tubeId + "/stoppoints?app_id=f729fc8a&app_key=b0df6b0af0ba1fddc75cf8d8bb6b73e1");

        HttpUtils.get("line/" + tubeId + "/stoppoints?app_id=f729fc8a&app_key=b0df6b0af0ba1fddc75cf8d8bb6b73e1", rp, new JsonHttpResponseHandler() {
            /*@Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TubeAdapter.class.getName(), "STOPPOINT");
            }*/

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                isLoading=false;
                Log.i(TubeAdapter.class.getName(), "STOPPOINT1");

                Intent intent = new Intent(TubeActivity.this, StopActivity.class);
                intent.putExtra("stoppoints", timeline.toString());
                startActivity(intent);
                /*
                list_tubes.setVisibility(View.GONE);
                list_stops.setVisibility(View.VISIBLE);

                stopModels = setStopList(timeline);

                if (stopModels != null) {
                    if (!stopModels.isEmpty()) {
                        stopAdapter = new StopAdapter(stopModels, getApplicationContext());

                        list_stops.setAdapter(stopAdapter);
                        list_stops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Log.d(StopActivity.class.getName(), "no work");
                            }
                        });
                    }
                } else {
                    list_tubes.setBackgroundColor(getResources().getColor(R.color.colorCorporateBlue));
                    text_warning.setText(R.string.error_loading);
                }
                */
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TubeActivity.class.getName(), "STOPPOINT101");
                Log.i(TubeActivity.class.getName(), Integer.toString(statusCode));
                Log.i(TubeActivity.class.getName(), responseString);

            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("Retry Number: " + retryNo);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                if(isLoading == false) {
                    ProgressDialog dialog = ProgressDialog.show(TubeActivity.this, "",
                            "Loading. Please wait...", true);
                    isLoading = true;
                }
            }

            @Override
            public void onCancel() {
                Log.e(TubeActivity.class.getName(), "CANCELLED");
            }

            /*
            public void onRetry(int retryNo) {
        AsyncHttpClient.log.d(LOG_TAG, String.format("Request retry no. %d", retryNo));
    }

    public void onCancel() {
        AsyncHttpClient.log.d(LOG_TAG, "Request got cancelled");
    }
             */
        });
        Log.e("ONCLICK", Integer.toString(view.getId()));
    }
/*
    private ArrayList<StopModel> setStopList(JSONArray stoppoints) {

        ArrayList<StopModel> content = new ArrayList<>();

        for (int counter = 0; counter < stoppoints.length(); counter++) {
            try {
                final String id = stoppoints.getJSONObject(counter).getString("id");
                final String commonName = stoppoints.getJSONObject(counter).getString("commonName");
                final double lat = stoppoints.getJSONObject(counter).getDouble("lat");
                final double lon = stoppoints.getJSONObject(counter).getDouble("lon");

                StopModel model = new StopModel(id, commonName, lat, lon);

                content.add(model);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return content;
    }
    */


    private ArrayList<TubeModel> setTubeList() {

        ArrayList<TubeModel> content = new ArrayList<>();

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("tubes");
        JSONArray tubesArray = null;

        try {
            tubesArray = new JSONArray(jsonArray);
            Log.i(TubeActivity.class.getName(), tubesArray.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int counter = 0; counter < tubesArray.length(); counter++) {
            try {
                final String id = tubesArray.getJSONObject(counter).getString("id");
                final String name = tubesArray.getJSONObject(counter).getString("name");

                TubeModel model = new TubeModel(id, name);

                content.add(model);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return content;
    }
}
