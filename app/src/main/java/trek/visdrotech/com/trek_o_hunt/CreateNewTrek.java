package trek.visdrotech.com.trek_o_hunt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import static android.graphics.BitmapFactory.decodeFile;

public class CreateNewTrek extends AppCompatActivity  implements View.OnClickListener{
    private Button start,pause,stop,upload;
    private FloatingActionButton fab;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int MY_PERMISSIONS_REQUEST = 12;
    private Location currentLocation;
    final String[] items = new String[]{"Camera", "Cancel"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trek);

        start = (Button) findViewById(R.id.but_start);
        stop = (Button) findViewById(R.id.but_stop);
        pause = (Button) findViewById(R.id.but_pause);
        upload = (Button) findViewById(R.id.but_upload);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);
        upload.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_start :
                Toast.makeText(this,"Location tracking started !",Toast.LENGTH_SHORT).show();
                start.setEnabled(false);
                pause.setEnabled(true);
                stop.setEnabled(true);
                HyperTrack.resumeTracking(new HyperTrackCallback() {
                    @Override
                    public void onSuccess(@NonNull SuccessResponse response) {

                    }

                    @Override
                    public void onError(@NonNull ErrorResponse errorResponse) {
                        Log.d("HyperTrack", "Some error occured during resuming tracking");
                    }
                });
                break;
            case R.id.but_pause :
                Toast.makeText(this,"Location tracking paused !",Toast.LENGTH_SHORT).show();
                start.setText("Continue");
                start.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(true);
                HyperTrack.pauseTracking();
                break;
            case R.id.but_stop :
                start.setText("Start");
                Toast.makeText(this,"Location tracking stopped !",Toast.LENGTH_SHORT).show();
                start.setEnabled(false);
                pause.setEnabled(false);
                stop.setEnabled(true);
                upload.setVisibility(View.VISIBLE);
                HyperTrack.pauseTracking();
                break;
            case R.id.but_upload :
                Toast.makeText(this,"Uploading your trek to the servers. Please wait",Toast.LENGTH_SHORT).show();
                start.setEnabled(true);
                start.setText("Start");
                pause.setEnabled(false);
                stop.setEnabled(false);
                upload.setVisibility(View.GONE);
                HyperTrack.pauseTracking();
                break;
            case R.id.fab :
                getPermissions();
                break ;
        }
    }


    public void imagePicker() {
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, SELECT_PHOTO);


        new AlertDialog.Builder(CreateNewTrek.this).setTitle("Capture a new image")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (items[item].equals("Gallery")) {
                            if (Build.VERSION.SDK_INT <= 19) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                            } else if (Build.VERSION.SDK_INT > 19) {
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                            }
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        } else {
                            Log.d("Create new trek", "Some error occured in the alert dialog on image picker function");
                        }
                    }
                }).show();

    }

    private void getPermissions() {
        if ((ContextCompat.checkSelfPermission(CreateNewTrek.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(CreateNewTrek.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)) {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(CreateNewTrek.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST);


        } else {
            imagePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!

                    imagePicker();

                } else {
                    Toast.makeText(CreateNewTrek.this, "This permissions are necessary!", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(CreateNewTrek.this.getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
            decodeFile(finalFile.toString());
        } else {
            Log.d("CreateTrek", "Unable to get image form camera or gallery");
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = CreateNewTrek.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void decodeFile(String path) {
        File f = new File(path);
        if (f.exists()) {
            Log.d("CreateNewTrek", "Sending file ");
            HyperTrack.getCurrentLocation(new HyperTrackCallback() {
                @Override
                public void onSuccess(@NonNull SuccessResponse response) {
                    currentLocation = (Location) response.getResponseObject();
//                        Handle file with current location


                }

                @Override
                public void onError(@NonNull ErrorResponse errorResponse) {

                }
            });
            //Got the file here

//                sendFile(f);
        } else {
            Toast.makeText(CreateNewTrek.this, "File error", Toast.LENGTH_SHORT).show();
        }
    }

}
