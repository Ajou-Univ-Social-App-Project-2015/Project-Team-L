package com.example.gwangrok.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;

/**
 * Created by taerin on 2015. 6. 6..
 */


public class ReceiveActivity extends Activity {


    ImageView imageView;
    Button viewBtn;
    Button cancelBtn;

    protected static Bitmap byteArrayToBitmap( byte[] $byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray($byteArray, 0, $byteArray.length) ;
        return bitmap ;
    }
    public byte[] bitmapToByteArray( Bitmap $bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        $bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray;
    }


    protected Bitmap resizeImg(Bitmap bit){
        //안드로이드 디바이스 해상도 구하기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight =displayMetrics.heightPixels;

        Bitmap reSized = Bitmap.createScaledBitmap(bit, deviceWidth,deviceHeight, true);
        return reSized;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive);

        viewBtn = (Button) findViewById(R.id.viewBtn2);
        imageView=(ImageView) findViewById(R.id.receiveView);
        cancelBtn = (Button)findViewById(R.id.cancelBtn2);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (ReceiveActivity.this,CameraActivity.class);
                startActivity(i);
            }
        });




        //사진 받아오기
        viewBtn.setOnClickListener(new View.OnClickListener(){
            //@Override
            public void onClick(View v) {


                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Figures");
//query1.
                query1.getInBackground("4HNjusnBuh", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject figures, ParseException e) {
                        if (e == null) {
                            int rowcount = figures.getInt("Count");
                            double rand = Math.random();
                            int num = (int) (rand * rowcount);
                            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ImageUpload");

                            query2.setSkip(num);
                            query2.setLimit(1);
                            try {
                                ParseObject PopPic = query2.getFirst();
                                ParseFile PopplePic = PopPic.getParseFile("ImageFile");

                                imageView.setImageBitmap(resizeImg(byteArrayToBitmap(PopplePic.getData())));
                            } catch (com.parse.ParseException e2) {
                                e2.printStackTrace();
                            }
                        }

                    }
                });


            }
        });


    }
}