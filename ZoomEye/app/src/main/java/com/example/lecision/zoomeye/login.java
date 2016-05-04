package com.example.lecision.zoomeye;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.lecision.zoomeye.global;

public class login extends AppCompatActivity {

    private Button back;
    private Button login;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.zoomeye_map);
        Button back = (Button) findViewById(R.id.button3);
        Button login = (Button)findViewById(R.id.button4);
        username = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(login.this,MainActivity.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = username.getText().toString();
                String pwd = password.getText().toString();
                PostThread postThread = new PostThread(name, pwd);
                postThread.start();
            }
        });
    }
    class PostThread extends Thread{
        String name;
        String pwd;

        public PostThread(String name, String pwd){
            this.name = name;
            this.pwd = pwd;
        }
        @Override
        public void run(){
            JSONObject json = new JSONObject();
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://api.zoomeye.org/user/login" ;
            HttpPost httpPost = new HttpPost(url);
            //System.out.println("success");
            NameValuePair pair1 = new BasicNameValuePair("username", name);
            NameValuePair pair2 = new BasicNameValuePair("password", pwd);
            ArrayList<NameValuePair>pairs = new ArrayList<NameValuePair>();
            pairs.add(pair1);
            pairs.add(pair2);
            try{
                json.put("username", name);
                json.put("password", pwd);
                StringEntity se = new StringEntity(json.toString());
                UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(pairs);
                httpPost.setEntity(se);
                //System.out.print(httpPost);
                try{
                    HttpResponse response = httpClient.execute(httpPost);
                    //System.out.print(response);
                    if (response.getStatusLine().getStatusCode() == 200){
                        System.out.print("success");
                        Intent intent = new Intent();
                        intent.setClass(login.this, SearchActivity.class);
                        startActivity(intent);
                        HttpEntity entity = response.getEntity();
                        BufferedReader reader = new BufferedReader(
                            new InputStreamReader(entity.getContent())
                            );
                        String result = reader.readLine();
                        JSONObject json_result = new JSONObject(result);
                        global.setAccess_token(json_result.getString("access_token"));
                        Log.i("access_token", global.getAccess_token());
                        Log.i("HTTP", result);
                        //Log.d("HTTP", "POST:" + result);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
