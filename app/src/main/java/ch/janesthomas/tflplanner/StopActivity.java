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
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ch.janesthomas.tflplanner.adapters.StopAdapter;
import ch.janesthomas.tflplanner.models.StopModel;

public class StopActivity extends AppCompatActivity {

    ListView list_stops;
    TextView text_warning;
    Gson gson = new Gson();

    ArrayList<LinkedTreeMap> stopModels;
    private static StopAdapter stopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        list_stops = (ListView) findViewById(R.id.list_stops);
        text_warning = (TextView) findViewById(R.id.text_warning);
        text_warning.setText("");
        list_stops.setBackgroundColor(getResources().getColor(R.color.colorCorporateWhite));

        stopModels = gson.fromJson(getIntent().getStringExtra("stoppoints"), ArrayList.class);

        if(stopModels != null) {
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
        }
    }

    private ArrayList<StopModel> setStopList() {

        ArrayList<StopModel> content = new ArrayList<>();

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("stoppoints");
        intent.removeExtra("stoppoints");
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

}
