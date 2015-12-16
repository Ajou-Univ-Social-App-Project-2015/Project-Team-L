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
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by JUNGWOO on 2015-06-06.
 */
public class MyPicActivity extends Activity{
    ImageView imageView;
    Button viewBtn;
    Button cancelBtn;
    TextView textView;

    int total= 0;
    int counter= 0;

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
        setContentView(R.layout.mypic);
        cancelBtn=(Button) findViewById(R.id.cancelBtn3);

        viewBtn = (Button) findViewById(R.id.viewBtn3);
        imageView=(ImageView) findViewById(R.id.myPicView);
        textView = (TextView) findViewById(R.id.tv);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyPicActivity.this,CameraActivity.class);
                startActivity(i);

            }
        });
        //사진 받아오기
        viewBtn.setOnClickListener(new View.OnClickListener(){
            //@Override
            public void onClick(View v) {
//                if (total == 0) {
                    ParseQuery<ParseObject> countquery = ParseQuery.getQuery("ImageUpload");
                    countquery.whereEqualTo("ImageUsername", ParseUser.getCurrentUser().getUsername());
                    countquery.countInBackground(new CountCallback() {
                        public void done(int count, ParseException e) {
                            if (e == null) {
                                total = --count;// The count request succeeded. Log the count
                                //Log.d("score", "Sean has played " + count + " games");
                            } else {
                                // The request failed
                            }
                        }
                    });
  //              }
                if (counter <= total) {
                    ParseQuery<ParseObject> querymy = ParseQuery.getQuery("ImageUpload");
                    querymy.whereEqualTo("ImageUsername", ParseUser.getCurrentUser().getUsername());
                    querymy.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> imgupload, ParseException e) {
                            if (e == null) {
                                ParseObject v = imgupload.get(counter);
                                ParseFile f = v.getParseFile("ImageFile");
                                textView.setText("Total:"+Integer.toString(total+1)+"\t        Counter: "+Integer.toString(counter+1));
                                try {
                                    imageView.setImageBitmap(resizeImg(byteArrayToBitmap(f.getData())));
                                    counter++;
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            //Log.d("score", "Retrieved " + imgupload.size() + " scores");


                            else

                            {

                                //Log.d("score", "Error: " + e.getMessage());
                            }
                        }
                    });
                } else {
                    Intent intent = new Intent(MyPicActivity.this, CameraActivity.class);
                    startActivity(intent);
                }

                }
                /*

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

*/

        });


    }
}
