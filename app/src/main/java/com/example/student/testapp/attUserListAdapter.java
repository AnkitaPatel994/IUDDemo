package com.example.student.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

class attUserListAdapter extends RecyclerView.Adapter<attUserListAdapter.ViewHolder>  {

    View v;
    Context context;
    ArrayList<HashMap<String, String>> attUserListArray;
    String url="http://192.168.1.105/WebService/UserDelete.php";;

    public attUserListAdapter(Context context, ArrayList<HashMap<String, String>> attUserListArray) {

        this.context = context ;
        this.attUserListArray = attUserListArray ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String id = attUserListArray.get(position).get("id");
        final String name = attUserListArray.get(position).get("name");
        final String gender = attUserListArray.get(position).get("gender");
        final String hobby = attUserListArray.get(position).get("hobby");
        final String dob = attUserListArray.get(position).get("dob");
        final String img = attUserListArray.get(position).get("img");
        final String imgpath = "http://192.168.1.105/WebService/"+img;

        holder.tvName.setText(name);
        holder.tvGender.setText(gender);
        holder.tvHobby.setText(hobby);
        holder.tvDob.setText(dob);

        Picasso.with(context).load(imgpath).into(holder.ivImg);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Delete User",Toast.LENGTH_SHORT).show();
                UserDelete userDelete = new UserDelete(id);
                userDelete.execute();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Edit User",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context,MainActivity.class);
                i.putExtra("flag","2");
                i.putExtra("id",id);
                i.putExtra("name",name);
                i.putExtra("gender",gender);
                i.putExtra("hobby",hobby);
                i.putExtra("dob",dob);
                i.putExtra("img",imgpath);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attUserListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvGender,tvHobby,tvDob;
        ImageView ivImg;
        Button btnEdit,btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName =(TextView) itemView.findViewById(R.id.tvName);
            tvGender =(TextView) itemView.findViewById(R.id.tvGender);
            tvHobby =(TextView) itemView.findViewById(R.id.tvHobby);
            tvDob =(TextView) itemView.findViewById(R.id.tvDob);
            ivImg =(ImageView) itemView.findViewById(R.id.ivImg);

            btnEdit =(Button) itemView.findViewById(R.id.btnEdit);
            btnDelete =(Button) itemView.findViewById(R.id.btnDelete);
        }
    }

    private class UserDelete extends AsyncTask<String,Void,String> {

        String status,message,id;

        public UserDelete(String id) {
            this.id = id;
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("id",id);
                Postdata postdata = new Postdata();
                String pdUser=postdata.post(url,joUser.toString());
                JSONObject j = new JSONObject(pdUser);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                }
                else
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
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
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,ViewActivity.class);
                context.startActivity(i);
            }
            else
            {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
