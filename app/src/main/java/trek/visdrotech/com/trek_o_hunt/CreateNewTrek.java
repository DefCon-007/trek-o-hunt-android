package trek.visdrotech.com.trek_o_hunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
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

public class CreateNewTrek extends AppCompatActivity  implements View.OnClickListener{
    private Button start,pause,stop,upload;
    private FloatingActionButton fab;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int MY_PERMISSIONS_REQUEST = 12;
    final String[] items = new String[]{"Camera", "Gallery", "Cancel"};
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
                break;
            case R.id.but_pause :
                Toast.makeText(this,"Location tracking paused !",Toast.LENGTH_SHORT).show();
                start.setText("Continue");
                start.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(true);
                break;
            case R.id.but_stop :
                start.setText("Start");
                Toast.makeText(this,"Location tracking stopped !",Toast.LENGTH_SHORT).show();
                start.setEnabled(false);
                pause.setEnabled(false);
                stop.setEnabled(true);
                upload.setVisibility(View.VISIBLE);
                break;
            case R.id.but_upload :
                Toast.makeText(this,"Uploading your trek to the servers. Please wait",Toast.LENGTH_SHORT).show();
                start.setEnabled(true);
                start.setText("Start");
                pause.setEnabled(false);
                stop.setEnabled(false);
                upload.setVisibility(View.GONE);
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


        new AlertDialog.Builder(CreateNewTrek.this).setTitle("Capture or select a image")
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

}
