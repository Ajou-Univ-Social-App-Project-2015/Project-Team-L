package com.example.gwangrok.gallery;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class CameraActivity extends Activity {

    Button bn; //사진찍기
   // Button bn2; //비디오찍기
    //Button bn3; // 갤러리에서 불러오기
    //Button bn4;
   // Button uploadBtn; // 사진 날리기
    Button viewBtn;//사진 보기
    Button myPicBtn;//자기가 올린 사진 보기
    ImageView IMG;
    Button logoutButton;


    private final int REQUEST_CODE = 0;
    private final int SELECT_PHOTO = 1;
   // private ImageView imageView;
    //ArrayList ob;
   // byte[] snap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.startlayout);
        bn = (Button) findViewById(R.id.bn);
        //bn2 = (Button) findViewById(R.id.bn2);
        // bn3 = (Button) findViewById(R.id.bn3);
        viewBtn = (Button) findViewById(R.id.viewBtn);
        // uploadBtn=(Button) findViewById(R.id.uploadBtn);
        logoutButton = (Button) findViewById(R.id.logoutPage);
        myPicBtn = (Button) findViewById(R.id.myPicBtn);
        IMG = (ImageView) findViewById(R.id.img);
        // imageView = (ImageView)findViewById(R.id.img);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CameraActivity.this, LoginSignupActivity.class);
                startActivity(i);
            }
        });
        //사진찍기
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    FileOutputStream fos = openFileOutput("test_picture.jpg", Context.MODE_WORLD_WRITEABLE);
                    // 파일 출력 스트림을 닫는다.
                    fos.close();

                    // 어플리케이션의 고유영역 경로를 File 객체로 얻는다.
                    File path = getFilesDir();
                    // 고유영역에 있는 test_picture.jpg 파일의 객체를 얻는다.
                    File file = new File(path, "test_picture.jpg");
                    // File 객체의 URI 를 얻는다.
                    Uri uri = Uri.fromFile(file);
                    // 인텐트에 URI 정보를 저장한다.
                    // 카메라 액티비티는 이 URI 에 입력된 경로에 촬영한 이미지를 저장한다.
                    i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(i, REQUEST_CODE);
                } catch (IOException ie) {
                    // 파일 입출력에 관한 예외 발생시 예외사항을 출력한다.
                    //TextView tv = (TextView) findViewById(R.id.id_tv);
                    //tv.setText(ie.toString());
                }
                /*
                if(i.resolveActivity(getPackageManager())!=null)
                {
                    startActivityForResult(i, REQUEST_CODE);
                }
                */

            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CameraActivity.this, ReceiveActivity.class);
                startActivity(i);
            }
        });



        myPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraActivity.this, MyPicActivity.class);
                startActivity(intent);

        }});
    }




    protected static Bitmap byteArrayToBitmap( byte[] $byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray($byteArray, 0, $byteArray.length) ;
        return bitmap ;
    }
    protected Bitmap resizeImg(Bitmap bit){
        //안드로이드 디바이스 해상도 구하기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight =displayMetrics.heightPixels;
        /*
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        int photoWidth = bmOptions.outWidth;
        int photoHeight = bmOptions.outHeight;*/
/*
        //get MaxMetrics
        BitmapFactory.Options options = new BitmapFactory.Options();
        if(photoWidth>=2880 || photoHeight>=5120) {
            if (deviceWidth >= 1440 || height >= 2560) {//9:16비율 해상도 2배, 세로로 찍은 사진
                options.inSampleSize = 2;//사진 크기 절반으로
            } else if (width >= 720 || height >= 1280) {
                options.inSampleSize = 4;//사진 크기 절반으로
            }
        }*/
        // Determine how much to scale down the image
        //int scaleFactor = Math.min(photoWidth / deviceWidth, photoHeight / deviceHeight);
        //bmOptions.inSampleSize = scaleFactor;
        //Bitmap.createScaledBitmap()

        Bitmap reSized = Bitmap.createScaledBitmap(bit, deviceWidth,deviceHeight, true);
        return reSized;
    }
    protected static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            /*case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
*/
            case REQUEST_CODE:
                //if(requestCode==REQUEST_CODE){
                if(resultCode==RESULT_OK)
                {

                    File path = getFilesDir();

                    // 고유영역에 있는 test_picture.jpg 파일의 객체를 얻는다.
                    File file = new File(path, "test_picture.jpg");
                    //snap=convertFileToByteArray(file);

                    Intent intent = new Intent(CameraActivity.this, SendActivity.class);
                    Uri uri = Uri.fromFile(file);
                    intent.putExtra("uri", uri.toString());
                    startActivityForResult(intent, 0);
                    System.gc();

                    //final Bitmap selectedImage1 = byteArrayToBitmap(snap);
                    //System.gc();




                }
                else if (resultCode == RESULT_CANCELED){

                }

        }
    }
}






