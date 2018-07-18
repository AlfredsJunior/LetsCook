package com.alfredtechsystems.letscook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends AppCompatActivity {
      //private food_key = null;
      private DatabaseReference mDatabase, userData;
      private TextView singleItemName, singleItemDescription, singleItemPrice;
      private ImageView singleFoodImage;
      private Button OrderFoodItemButton;
      private FirebaseAuth mAuth;
      private  FirebaseUser current_user;
      private DatabaseReference mRef;
      private  String food_key,food_name,food_price, food_description, food_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        food_key= getIntent().getExtras().getString("FoodId");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Item");
        singleItemName=(TextView)findViewById(R.id.foodName);
        singleItemDescription=(TextView)findViewById(R.id.foodDescription);
        singleItemPrice=(TextView)findViewById(R.id.foodPrice);

        mAuth=FirebaseAuth.getInstance();

        current_user = mAuth.getCurrentUser();
        userData=FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
        mRef= FirebaseDatabase.getInstance().getReference().child("orders");
        mDatabase.child(food_key);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food_name = (String) dataSnapshot.child("name").getValue();
                food_price = (String) dataSnapshot.child("price").getValue();
                food_description = (String) dataSnapshot.child("price").getValue();
                food_image = (String) dataSnapshot.child("image").getValue();


                singleItemName.setText(food_name);
                singleItemDescription.setText(food_description);
                singleItemPrice.setText(food_price);
                Picasso.with(SingleFoodActivity.this).load(food_image).into(singleFoodImage);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void OrderItemButtonClicked(View view) {

        final  DatabaseReference newOrder= mRef.push();
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             newOrder.child("singleItemName").setValue(food_name);
             newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     startActivity(new Intent(SingleFoodActivity.this, MenuActivity.class ));

                 }
             });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
