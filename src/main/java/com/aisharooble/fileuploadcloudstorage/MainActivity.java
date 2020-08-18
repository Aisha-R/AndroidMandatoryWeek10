package com.aisharooble.fileuploadcloudstorage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.aisharooble.fileuploadcloudstorage.repo.Repo;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView); // finds the imageView in the layout file
        //Repo.uploadFile(this);
    }

    public void downloadImageNow(View view) {
        Repo.downloadImage("fantastic-fruits-cover-hires.jpg", imageView); // calls our download method, giving the
        // imageView as a parameter, so it can be updated whenever the file arrives.
    }

    public void uploadImageNow(View view) {
        Repo.uploadImage(imageView);
    }

    public void galleryBtnPressed(View view){
        Intent intent = new Intent(Intent.ACTION_PICK); // make an implicit intent, which will allow
        // the user to choose among different services to accomplish this task.
        intent.setType("image/*"); // we need to set the type of content to pick
        startActivityForResult(intent, 1); // start the activity, and in this case
        // expect an answer
    }

    public void cameraBtnPressed(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // ask for some app, to handle the camera
        startActivityForResult(intent, 2); // provide a different number to identify the
        // camera action on onActivityResult(...)

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        backFromGallery(requestCode, resultCode, data);
        if(requestCode == 2){ // from gall
            if (resultCode == -1) {  // -1 is code for OK
                System.out.println("Success !!");
                Bitmap bitmap = (Bitmap) data.getExtras().get("data"); // ask for data from the incoming intent.
                imageView.setImageBitmap(bitmap); // assign the data to our imageView
            } else {
                System.out.println("failed to get image");
            }
        }
    }

    private void backFromGallery(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1) { // from gallery
            if (resultCode == -1) { // -1 means OK
                Uri uri = data.getData(); // get path to where the image is located
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {

                }
            }
        }
    }
}
