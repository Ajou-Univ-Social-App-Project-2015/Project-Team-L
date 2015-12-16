package com.example.gwangrok.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by taerin on 2015. 5. 30..
 */
public class SendActivity extends Activity{


    Bitmap bm;

    public byte[] bitmapToByteArray( Bitmap $bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        $bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray;
    }



    Button cancelBtn;
    Button uploadBtn;
    ImageView imageView;
    //Bitmap currentPhoto = CameraActivity.byteArrayToBitmap(snap);



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendlayout);
        imageView=(ImageView)findViewById(R.id.photo);

        Intent intent= getIntent();
        String currImageURI= intent.getStringExtra("uri");
        Uri currImageURI2 = Uri.parse(currImageURI); //uri·Î º¯È¯


        try {

            bm = MediaStore.Images.Media.getBitmap(getContentResolver(), currImageURI2);
            imageView.setImageBitmap(bm);


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }





        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        uploadBtn = (Button) findViewById(R.id.uploadBtn);



        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser currentUser = ParseUser.getCurrentUser();
                byte[] upfile =bitmapToByteArray(bm);


                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.androidphoto);

                //ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //               bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //             byte[] image = stream.toByteArray();


                ParseObject imgupload = new ParseObject("ImageUpload");
                ParseFile file = new ParseFile(currentUser.getUsername()+".png", upfile);

                file.saveInBackground();

                imgupload.put("ImageUsername", currentUser.getUsername());
                imgupload.put("ImageLike", 0);//Like
                imgupload.put("ImageFile", file);
                imgupload.saveInBackground();
                ParseQuery<ParseObject> q = ParseQuery.getQuery("Figures");
                q.getInBackground("4HNjusnBuh", new GetCallback<ParseObject>() {
                    public void done(ParseObject c, ParseException e) {
                        if (e == null) {
                            c.increment("Count");
                            c.saveInBackground();
                            //Toast.makeText(SendActivity.this, "Count Success",
                              //      Toast.LENGTH_SHORT).show();
                            // object will be your game score
                        } else {
                            Toast.makeText(SendActivity.this, "Count Fail",
                                    Toast.LENGTH_SHORT).show();// something went wrong
                        }
                    }
                });

                Toast.makeText(SendActivity.this, "Uploaded",
                        Toast.LENGTH_SHORT).show();

                onDestroy();
                Intent i = new Intent(SendActivity.this,CameraActivity.class);
                startActivity(i);




            }



        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SendActivity.this,CameraActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onDestroy() {

        bm.recycle();
        bm = null;

        super.onDestroy();

    }



}