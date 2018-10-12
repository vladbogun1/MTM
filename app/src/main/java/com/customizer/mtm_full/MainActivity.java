package com.customizer.mtm_full;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText exPass;
    public LinearLayout Main_Block;
    BackgroundWorker backgroundWorker;
    public JSONArray Array_from;
    ScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main_Block = (LinearLayout)findViewById(R.id.Main_Block);
        scrollView = (ScrollView)findViewById(R.id.Scroll);
        SendPost();



    }

    public void SendPost() {
        String pass = "";
        String type = "login";

        backgroundWorker = new BackgroundWorker(this, Main_Block, scrollView);
        backgroundWorker.execute(type,pass);

    }


}
