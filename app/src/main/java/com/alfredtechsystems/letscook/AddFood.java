package com.alfredtechsystems.letscook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFood extends AppCompatActivity {


    private  ImageButton foodImage;
    private  final  int GALLREQ = 1;
    private EditText Name, Price, Description;
    private Uri uri=null;
    private StorageReference storageReference=null;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //ButterKnife.bind(this);

        Name = (EditText) findViewById(R.id.itemName);
        Description = (EditText) findViewById(R.id.itemDescription);
        Price = (EditText) findViewById(R.id.itemPrice);
        storageReference = FirebaseStorage.getInstance().getReference();
        mRef= FirebaseDatabase.getInstance().getReference("Item");
    }
        public void imageButtonClicked(View view) {
            Intent galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("Image/*");
           // startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GALLREQ);
           startActivityForResult(galleryIntent,GALLREQ);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==GALLREQ && requestCode== RESULT_OK){
         uri=data.getData();
         foodImage=(ImageButton)findViewById(R.id.foodimagebutton);
         foodImage.setImageURI(uri);
         }
        }


       public void addItemButtonClicked(View view){
            final String Name_text=Name.getText().toString().trim();
            final String Description_text=Description.getText().toString().trim();
            final String Price_text=Price.getText().toString().trim();
            if(!TextUtils.isEmpty(Name_text) && !TextUtils.isEmpty(Description_text) && !TextUtils.isEmpty(Price_text)){
                Toast.makeText(AddFood.this, "Fill in the Missing Fields", Toast.LENGTH_SHORT).show();
             StorageReference filpath=storageReference.child(uri.getLastPathSegment());
             filpath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  final  Uri downloaduri = taskSnapshot.getDownloadUrl();
                     Toast.makeText(AddFood.this, "Image Uploaded successfully", Toast.LENGTH_LONG).show();
                     final DatabaseReference newPost  = mRef.push();
                     newPost.child("Name").setValue(Name_text);
                     newPost.child("Description").setValue(Description_text);
                     newPost.child("Price").setValue(Price_text);
                     newPost.child("Image").setValue(downloaduri.toString());
                 }
             });
            }

    }

}
