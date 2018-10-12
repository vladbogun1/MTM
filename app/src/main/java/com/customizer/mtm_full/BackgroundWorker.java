package com.customizer.mtm_full;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.text.Layout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Array;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class BackgroundWorker extends AsyncTask<String, Void, String>{
    public JSONArray Array_from_BD;
    LinearLayout linearLayout;
    ScrollView scroll;


//    Get item from array


//    for (int i = 0; i < recs.length(); ++i) {
//        JSONObject rec = recs.getJSONObject(i);
//        int ID = rec.getInt("ID");
//        String Name = rec.getString("Name");

//    }


    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context cxt, LinearLayout layout, ScrollView scrollView){
        context = cxt;
        linearLayout = layout;
        scroll = scrollView;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "https://vladbogun.store/customize_app/login.php";

        if(type.equals("login")){
            try {
                String user_password = params[0];
                URL url = new URL(login_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                httpsURLConnection.setRequestMethod("POST" );
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream
                ,"UTF-8"));
                String post_data = URLEncoder.encode("user_password", "UTF-8")+"="+URLEncoder.encode(user_password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpsURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {


    }
    @Override
    protected void onPostExecute(String result) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            JSONArray recs = jsonObject.getJSONArray("Table");
            Array_from_BD = recs;


        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            LoadContent("Free");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void LoadContent(String status) throws JSONException {


        for (int i = 0; i < Array_from_BD.length(); i++) {
            JSONObject rec = Array_from_BD.getJSONObject(i);
            int ID = rec.getInt("ID");
            String Name = rec.getString("Name");
            String URL = (rec.getString("SourcePath")).replace("..","");
            LinearLayout l = new LinearLayout(context);
            l.setId(ID);
            l.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    )
            );

            ImageView image = new ImageView(context);
            image.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    )
            );
            image.setScaleType(ImageView.ScaleType.FIT_START);
            image.getLayoutParams().height = 500;
            image.getLayoutParams().width = 410;
            LoadImageFromURl(URL, image);

            Button button = new Button(context);
            button.setText(Integer.toString(i)+ " " + URL + " "+Name+" №" + Integer.toString(ID) );
            button.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    )
            );

            l.addView(image);
            l.addView(button);
            linearLayout.addView(l);
        }

    }
    private void LoadImageFromURl(String url, ImageView image){
        url =  "https://vladbogun.store"+url+"thumbnail.jpg";
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(image, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
