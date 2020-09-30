package ch.janesthomas.tflplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import ch.janesthomas.tflplanner.rest.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class OverviewActivity extends AppCompatActivity {

    private Button button_tube;
    private Button button_dlr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        button_tube = (Button) findViewById(R.id.button_tube);
        button_dlr = (Button) findViewById(R.id.button_dlr);

    }

    public void getTubes(View view) {
        RequestParams rp = new RequestParams();
        HttpUtils.get("Line/Mode/tube?app_id=" + BuildConfig.APIID + "&app_key=" + BuildConfig.APIKEY, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(OverviewActivity.class.getName(), "Response was JSONObject");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Log.i(OverviewActivity.class.getName(), "Response was JSONArray");

                Intent intent = new Intent(getBaseContext(), TubeActivity.class);
                intent.putExtra("tubes", timeline.toString());
                startActivity(intent);
            }
        });
    }
}
