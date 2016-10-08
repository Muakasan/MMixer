package aidansan.tk.mmixer;

import android.app.DownloadManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    //http://stackoverflow.com/questions/7291731/how-to-play-audio-file-in-android
    public void audioPlayer(String path, String filename){
        final TextView tv = (TextView)findViewById(R.id.textView2);
        tv.setText("reached audio method");
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(path + File.separator + filename);
            mp.prepare();
            mp.start();
        }
        catch(Exception e){
            tv.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView)findViewById(R.id.textView2);
        final Button audioButton = (Button)findViewById(R.id.play_audio_btn);
        audioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Button press", "Yay");
                tv.setText("Hi Ethan");
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.pompeii);
                mp.start();
            }
        });

        final Button speedButton = (Button)findViewById(R.id.speed_button);
        final TextView speedTv = (TextView) findViewById(R.id.speedTextView);
        speedButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                tv.setText("Trying to post");
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://tsukiumi.elc1798.tech:11235/temp/";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    speedTv.setText(response.get("speed").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        });
// Add the request to the RequestQueue.
                queue.add(jsObjRequest);
            }}
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
