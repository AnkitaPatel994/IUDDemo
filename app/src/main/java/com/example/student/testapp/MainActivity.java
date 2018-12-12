package com.example.student.testapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText txtName,txtDate;
    RadioGroup rgMF;
    RadioButton rbMale,rbFemale;
    CheckBox chMusic,chMovie,chDance;
    //TextView txtDate;
    ImageView ivImg;
    Button btnSave;
    String Name,Gender = "Male",Date,hobby;

    Bitmap bitmap = null;
    String str_imgpath,encodedImgpath="";
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    String url="http://192.168.1.105/WebService/parson.php";
    String urlEdit="http://192.168.1.105/WebService/UserEdit.php";
    String urlEditImg="http://192.168.1.105/WebService/ImgEdit.php";
    String eid,ename,egender,ehobby,edob,eimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (EditText)findViewById(R.id.txtName);

        rgMF = (RadioGroup) findViewById(R.id.rgMF);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);

        chMusic = (CheckBox)findViewById(R.id.chMusic);
        chMovie = (CheckBox)findViewById(R.id.chMovie);
        chDance = (CheckBox)findViewById(R.id.chDance);

        txtDate = (EditText)findViewById(R.id.txtDate);

        ivImg = (ImageView)findViewById(R.id.ivImg);

        btnSave = (Button)findViewById(R.id.btnSave);

        final String flag = getIntent().getExtras().getString("flag");

        if(flag.equals("1"))
        {
            Toast.makeText(getApplicationContext(),"User Add"+flag,Toast.LENGTH_SHORT).show();
            rbMale.setChecked(true);
        }
        else if(flag.equals("2"))
        {
            Toast.makeText(getApplicationContext(),"User Edit"+flag,Toast.LENGTH_SHORT).show();
            eid = getIntent().getExtras().getString("id");
            ename = getIntent().getExtras().getString("name");
            egender = getIntent().getExtras().getString("gender");
            ehobby = getIntent().getExtras().getString("hobby");
            edob = getIntent().getExtras().getString("dob");
            eimg = getIntent().getExtras().getString("img");

            txtName.setText(ename);
            txtDate.setText(edob);

            Picasso.with(getApplicationContext()).load(eimg).into(ivImg);

            if(egender.equals("Male"))
            {
                rbMale.setChecked(true);
            }
            else if(egender.equals("Female"))
            {
                rbFemale.setChecked(true);
            }

            if(ehobby.equals("Music,Movie,Dance"))
            {
                chMusic.setChecked(true);
                chMovie.setChecked(true);
                chDance.setChecked(true);
            }
            else if(ehobby.equals("Music,Movie"))
            {
                chMusic.setChecked(true);
                chMovie.setChecked(true);
            }
            else if(ehobby.equals("Movie,Dance"))
            {
                chMovie.setChecked(true);
                chDance.setChecked(true);
            }
            else if(ehobby.equals("Music,Dance"))
            {
                chMusic.setChecked(true);
                chDance.setChecked(true);
            }
            else if(ehobby.equals("Movie"))
            {
                chMovie.setChecked(true);
            }
            else if(ehobby.equals("Music"))
            {
                chMusic.setChecked(true);
            }
            else if(ehobby.equals("Dance"))
            {
                chDance.setChecked(true);
            }
        }


        rgMF.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = rgMF.getCheckedRadioButtonId();
                switch (i)
                {
                    case R.id.rbMale:
                        Gender = "Male";
                        break;
                    case R.id.rbFemale:
                        Gender = "Female";
                        break;
                }
            }
        });



        /*txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getApplicationContext(),new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        selectedmonth = selectedmonth + 1;
                        txtDate.setText("" + selectedday + "-" + selectedmonth + "-" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();

            }
        });*/

        ivImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),

                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImage();
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    selectImage();
                }
                return false;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chMusic.isChecked() == true && chMovie.isChecked() == true && chDance.isChecked() == true)
                {
                    hobby = "Music,Movie,Dance";
                }
                else if(chMusic.isChecked() == true && chMovie.isChecked() == true)
                {
                    hobby = "Music,Movie";
                }
                else if(chMovie.isChecked() == true && chDance.isChecked() == true)
                {
                    hobby = "Movie,Dance";
                }
                else if(chMusic.isChecked() == true && chDance.isChecked() == true)
                {
                    hobby = "Music,Dance";
                }
                else if(chMusic.isChecked() == true)
                {
                    hobby = "Music";
                }
                else if(chMovie.isChecked() == true)
                {
                    hobby = "Movie";
                }
                else if(chDance.isChecked() == true)
                {
                    hobby = "Dance";
                }

                Name = txtName.getText().toString();
                Date = txtDate.getText().toString();

                //Toast.makeText(getApplicationContext(),Name +"/"+Gender+"/"+hobby+"/"+Date+"/"+encodedImgpath,Toast.LENGTH_SHORT).show();

                //String flag = getIntent().getExtras().getString("flag");

                if(flag.equals("1"))
                {
                    //Toast.makeText(getApplicationContext(),"User Add",Toast.LENGTH_SHORT).show();
                    GetProfile getProfile = new GetProfile();
                    getProfile.execute();
                }
                else if(flag.equals("2"))
                {
                    Toast.makeText(getApplicationContext(),"User Edit",Toast.LENGTH_SHORT).show();
                    UserEdit userEdit = new UserEdit();
                    userEdit.execute();

                    UserEditImg userEditImg = new UserEditImg();
                    userEditImg.execute();

                }



            }
        });
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(
                        Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri tempUri = getImageUri(MainActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                ivImg.setImageBitmap(bitmap);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                InputStream in = null;
                try {
                    final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
                    in = getApplicationContext().getContentResolver().openInputStream(selectedImageUri);

                    // Decode image size
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(in, null, o);
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int scale = 1;
                    while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                            IMAGE_MAX_SIZE) {
                        scale++;

                    }
                    in = getApplicationContext().getContentResolver().openInputStream(selectedImageUri);
                    if (scale > 1) {
                        scale--;

                        o = new BitmapFactory.Options();
                        o.inSampleSize = scale;
                        bitmap = BitmapFactory.decodeStream(in, null, o);

                        // resize to desired dimensions
                        int height = bitmap.getHeight();
                        int width = bitmap.getWidth();

                        double y = Math.sqrt(IMAGE_MAX_SIZE
                                / (((double) width) / height));
                        double x = (y / height) * width;

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) x,
                                (int) y, true);
                        bitmap.recycle();
                        bitmap = scaledBitmap;

                        System.gc();
                    } else {
                        bitmap = BitmapFactory.decodeStream(in);
                    }
                    in.close();

                } catch (Exception ignored) {

                }

                Uri tempUri = getImageUri(MainActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                ivImg.setImageBitmap(bitmap);

            }
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private class GetProfile extends AsyncTask<String,Void,String> {

        String status,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {

                joUser.put("name",Name);
                joUser.put("gender",Gender);
                joUser.put("hobby",hobby);
                joUser.put("dob",Date);
                joUser.put("img",encodedImgpath);

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
            dialog.dismiss();
            if(status.equals("1"))
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),ViewActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UserEdit extends AsyncTask<String,Void,String> {

        String statuse,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("id",eid);
                joUser.put("name",Name);
                joUser.put("gender",Gender);
                joUser.put("hobby",hobby);
                joUser.put("dob",Date);

                Postdata postdata = new Postdata();
                String pdUsere=postdata.post(urlEdit,joUser.toString());
                JSONObject j = new JSONObject(pdUsere);
                statuse=j.getString("status");
                if(statuse.equals("1"))
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
            dialog.dismiss();
            //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),ViewActivity.class);
            startActivity(i);
            finish();
            /*if(statuse.equals("1"))
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),ViewActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    private class UserEditImg extends AsyncTask<String,Void,String> {

        String statuse,message;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading....");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joUser=new JSONObject();
            try {
                joUser.put("id",eid);
                joUser.put("img",encodedImgpath);

                Postdata postdata = new Postdata();
                String pdUsere=postdata.post(urlEditImg,joUser.toString());
                JSONObject j = new JSONObject(pdUsere);
                statuse=j.getString("status");
                if(statuse.equals("1"))
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
            dialog.dismiss();
            //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),ViewActivity.class);
            startActivity(i);
            finish();
            /*if(statuse.equals("1"))
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),ViewActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }*/
        }
    }
}
