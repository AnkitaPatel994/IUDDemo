package com.example.student.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewActivity extends AppCompatActivity {

    RecyclerView rvUserList;
    RecyclerView.LayoutManager rvManagerUserList;
    RecyclerView.Adapter rvAdapterUserList;
    ArrayList<HashMap<String,String>> attUserListArray=new ArrayList<>();
    String url="http://192.168.1.105/WebService/UserList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("flag","1");
                startActivity(i);
            }
        });

        rvUserList = (RecyclerView)findViewById(R.id.rvUserList);
        rvUserList.setHasFixedSize(true);

        rvManagerUserList = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvUserList.setLayoutManager(rvManagerUserList);

        GetUserList getUserList = new GetUserList();
        getUserList.execute();

    }

    private class GetUserList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                Postdata postdata = new Postdata();
                String pdUser=postdata.post(url,joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("User");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String id =jo.getString("id");
                        String name =jo.getString("name");
                        String gender =jo.getString("gender");
                        String hobby =jo.getString("hobby");
                        String dob =jo.getString("dob");
                        String img =jo.getString("img");

                        hashMap.put("id",id);
                        hashMap.put("name",name);
                        hashMap.put("gender",gender);
                        hashMap.put("hobby",hobby);
                        hashMap.put("dob",dob);
                        hashMap.put("img",img);

                        attUserListArray.add(hashMap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(status.equals("1"))
            {
                rvAdapterUserList=new attUserListAdapter(getApplicationContext(),attUserListArray);
                rvUserList.setAdapter(rvAdapterUserList);
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }

        }
    }
}
