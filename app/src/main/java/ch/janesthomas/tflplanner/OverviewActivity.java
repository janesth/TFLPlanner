package ch.janesthomas.tflplanner;

import android.app.AlertDialog;
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
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        button_tube = (Button) findViewById(R.id.button_tube);
        button_dlr = (Button) findViewById(R.id.button_dlr);
        builder = new AlertDialog.Builder(this);
        setUpDialog();

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
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(OverviewActivity.class.getName(), "Request failed. Here is why:");
                Log.i(OverviewActivity.class.getName(), Integer.toString(statusCode));
                Log.i(OverviewActivity.class.getName(), responseString);

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    public void getDLR(View view) {
        RequestParams rp = new RequestParams();
        HttpUtils.get("Line/Mode/dlr?app_id=" + BuildConfig.APIID + "&app_key=" + BuildConfig.APIKEY, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(OverviewActivity.class.getName(), "Response was JSONObject");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Log.i(OverviewActivity.class.getName(), "Response was JSONArray");

                Intent intent = new Intent(getBaseContext(), DLRActivity.class);
                intent.putExtra("dlr", timeline.toString());
                startActivity(intent);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(OverviewActivity.class.getName(), "Request failed. Here is why:");
                Log.i(OverviewActivity.class.getName(), Integer.toString(statusCode));
                Log.i(OverviewActivity.class.getName(), responseString);

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    private void setUpDialog() {
        builder.setMessage(R.string.error_loading);
        builder.setTitle(R.string.error_generic_title);
        builder.setCancelable(true);
    }
}
