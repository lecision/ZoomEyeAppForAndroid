package com.example.lecision.zoomeye;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;

public class SearchActivity extends AppCompatActivity {
    private CheckBox web;
    private CheckBox hosts;
    private EditText app;
    private Button affirm;
    String choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView imageView = (ImageView)findViewById(R.id.imageView3);
        imageView.setImageResource(R.drawable.img3);
        web = (CheckBox)findViewById(R.id.web);
        hosts = (CheckBox)findViewById(R.id.hosts);
        app = (EditText)findViewById(R.id.editText3);
        affirm = (Button)findViewById(R.id.button5);
        web.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    choice = "web";
                }
            }
        });
        hosts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choice = "host" +
                            "";
                }
            }
        });
        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app_string = app.getText().toString();
                GetThread getThread = new GetThread(choice, app_string);
                getThread.start();
            }
        });
    }
    public class GetThread extends Thread{

        String choice_th;
        String app_string_th;

        public GetThread(String choice_input, String app_string_input){
            choice_th = choice_input;
            app_string_th = app_string_input;
        }
        public void run(){
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get;
            String headers = global.getAccess_token();
            String url = "http://api.zoomeye.org/" + choice_th + "/search?query=" + app_string_th + "&facet=app,os&page=" +
                    "1" ;
            try{
                Log.i("status","1");
                get = new HttpGet(url);
                Log.i("status","2");
                get.setHeader("Authorization", "JWT " + headers);
                Log.i("status", "3");
                Log.i("status","try success");
                HttpResponse httpResponse = httpClient.execute(get);
                BasicResponseHandler myHandler = new BasicResponseHandler();
                String result = myHandler.handleResponse(httpResponse);
                Intent webViewIntent = new Intent();
                webViewIntent.setClass(SearchActivity.this, WebViewActivity.class);
                webViewIntent.putExtra("htmlString",result);
                startActivity(webViewIntent);
            }catch(Exception e){
                Log.i("exception","fail");
                e.printStackTrace();
            }
            Log.i("url",url);
        }

    }
}
