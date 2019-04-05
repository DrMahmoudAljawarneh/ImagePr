

package com.example.image;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    Bitmap orginalImg,currentImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button click = (Button)findViewById(R.id.bCapture);
        img = (ImageView)findViewById(R.id.imageView);
        if(!hasCamera()) {
            click.setEnabled(false);
        }
        img.setImageBitmap(orginalImg);
        Toast myToast = Toast.makeText(getApplicationContext(), "This App is created by Harshit", Toast.LENGTH_LONG);
        myToast.show();
        verifyStoragePermissions(this);
    }
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public static final int REQUEST_CAPTURE = 1;

    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            img.setImageBitmap(photo);
            Toast myToast = Toast.makeText(getApplicationContext(), "photo captured", Toast.LENGTH_SHORT);
            myToast.show();
            orginalImg = photo;
        }
    }

    void launchCamera(View view ){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_CAPTURE);
    }

    void onInvertClicked(View view){

        Bitmap orgImg = ((BitmapDrawable)img.getDrawable()).getBitmap();
        Bitmap photo = Bitmap.createBitmap(orgImg.getWidth(),orgImg.getHeight(),orgImg.getConfig());


        int A,R,G,B;
        int height=orgImg.getHeight();
        int width=orgImg.getWidth();
        int pixColor;
        int x,y;
        for(x=0; x<height; x++){
            for(y=0; y<width; y++){
                pixColor=orgImg.getPixel(y,x);
                A=Color.alpha(pixColor);
                R=255- Color.red(pixColor);
                G=255- Color.green(pixColor);
                B=255- Color.blue(pixColor);
                photo.setPixel(y,x,Color.argb(A,R,G,B));
            }
        }
        img.setImageBitmap(photo);
        img.buildDrawingCache();
        Bitmap bmap = img.getDrawingCache();
        currentImg= bmap;
    }

    void onBrightClicked(View view){

        Bitmap orgImg = ((BitmapDrawable)img.getDrawable()).getBitmap();
        Bitmap photo = Bitmap.createBitmap(orgImg.getWidth(),orgImg.getHeight(),orgImg.getConfig());


        int A,R,G,B;
        int height=orgImg.getHeight();
        int width=orgImg.getWidth();
        int pixColor;
        int x,y;
        for(x=0; x<height; x++){
            for(y=0; y<width; y++){
                pixColor=orgImg.getPixel(y,x);
                A=Color.alpha(pixColor);
                R=Color.red(pixColor);
                G=Color.green(pixColor);
                B=Color.blue(pixColor);
                if((R+20)<=255)
                    R+=20;
                if((G+20)<=255)
                    G+=20;
                if((B+20)<=255)
                    B+=20;
                photo.setPixel(y,x,Color.argb(A,R,G,B));
            }
        }
        img.setImageBitmap(photo);
    }

    void onGreyClicked(View view){

        Bitmap orgImg = ((BitmapDrawable)img.getDrawable()).getBitmap();
        Bitmap photo = Bitmap.createBitmap(orgImg.getWidth(),orgImg.getHeight(),orgImg.getConfig());


        int A,R,G,B;
        int height=orgImg.getHeight();
        int width=orgImg.getWidth();
        int pixColor;
        int x,y;
        for(x=0; x<height; x++){
            for(y=0; y<width; y++){
                pixColor=orgImg.getPixel(y,x);
                A=Color.alpha(pixColor);
                R=255- Color.red(pixColor);
                G=255- Color.green(pixColor);
                B=255- Color.blue(pixColor);
                pixColor=(R+G+B)/3;
                photo.setPixel(y,x,Color.argb(A,pixColor,pixColor,pixColor));
            }
        }
        img.setImageBitmap(photo);
    }

    void onFlipClicked(View view){

        Bitmap orgImg = ((BitmapDrawable)img.getDrawable()).getBitmap();
        Bitmap photo = Bitmap.createBitmap(orgImg.getWidth(),orgImg.getHeight(),orgImg.getConfig());


        int A,R,G,B;
        int height=orgImg.getHeight();
        int width=orgImg.getWidth();
        int pixColor;
        int x,y;
        for(x=0; x<height; x++){
            for(y=0; y<width; y++){
                pixColor=orgImg.getPixel(y,x);
                A=Color.alpha(pixColor);
                R=Color.red(pixColor);
                G=Color.green(pixColor);
                B=Color.blue(pixColor);
                photo.setPixel((width-y-1),x,Color.argb(A,R,G,B));
            }
        }
        img.setImageBitmap(photo);
    }

    void onUndoAllClicked(View view){
        currentImg= ((BitmapDrawable)img.getDrawable()).getBitmap();
        img.setImageBitmap(orginalImg);
        Toast myToast = Toast.makeText(getApplicationContext(),"Undo All",Toast.LENGTH_SHORT);
        myToast.show();
    }

    void onRedoAllClicked(View view){
        img.setImageBitmap(currentImg);
        Toast myToast = Toast.makeText(getApplicationContext(),"Redo All",Toast.LENGTH_SHORT);
        myToast.show();
    }

    public void onShareTo(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        Bitmap orgImg = ((BitmapDrawable)img.getDrawable()).getBitmap();
        //change the type of data you need to share,
        //for image use "image/*"
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, orgImg);
        startActivity(Intent.createChooser(intent, "Share"));

    }

    public boolean saveImageToDisk(Bitmap imageData, String filename) {
        // get path to external storage (SD card)

        File sdIconStorageDir = null;


        sdIconStorageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/myAppDir/");
        // create storage directories, if they don't exist
        if(!sdIconStorageDir.exists())
        {
            sdIconStorageDir.mkdirs();
        }
        try {
            String filePath = sdIconStorageDir.toString() + File.separator + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //Toast.makeText(m_cont, "Image Saved at----" + filePath, Toast.LENGTH_LONG).show();
            // choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            System.out.println("Image saved Succcscscs");


        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        } catch ( IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void saveImage(View view) {
        Bitmap orgImg = ((BitmapDrawable)img.getDrawable()).getBitmap();
        if(orgImg !=null){
            saveImageToDisk(orgImg, "mahmoud.jpeg");
        } else
        System.out.println("No Image Foundddddd");

    }

}