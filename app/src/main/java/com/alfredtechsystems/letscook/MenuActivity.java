package com.alfredtechsystems.letscook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView mFoodList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mFoodList=(RecyclerView)findViewById(R.id.foodList);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase =FirebaseDatabase.getInstance().getReference().child("Item");

        mAuth= FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null){
                    Intent loginIntent= new Intent((MenuActivity.this, Main_clientActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Food, FoodViewHolder> FBRA=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
               food.class,
                R.layout.singlemenuitem,
                FoodViewHolder.class,
                mDatabase
        ) {

            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
            viewHolder.setName(.getName)
            viewHolder.setDescription(.getDescription()
            viewHolder.setPrice(.getPrice()
            viewHolder.setImage(getApplicationContext(),.getImage)
                final  food_key = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleFoodActivity = new Intent(MenuActivity.this, SingleFoodActivity.class);
                        singleFoodActivity.putExtra("FoodId", food_key);
                        startActivity(singleFoodActivity);
                    }
                });

            }
        };
        mFoodList.setAdapter(FBRA);
    }
    public static  class FoodViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String name) {
            TextView food_name = (TextView) mView.findViewById(R.id.foodName);
            food_name.setText(name);
            }
        public void setDescription(String description) {
            TextView food_description = (TextView) mView.findViewById(R.id.foodDescription);
            food_description.setText(description);
        }
        public void setPrice(String price) {
            TextView food_price = (TextView) mView.findViewById(R.id.foodPrice);
            food_price.setText(price);
        }
        public void setImage(Context ctx, String image) {
            ImageView food_image=(ImageView) mView.findViewById(R.id.foodimage);
            Picasso.with(ctx).load(image).into(food_image);

        }
    }
}
