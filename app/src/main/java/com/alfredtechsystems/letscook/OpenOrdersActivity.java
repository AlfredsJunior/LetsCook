package com.alfredtechsystems.letscook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class OpenOrdersActivity extends AppCompatActivity {
    private RecyclerView mFoodList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_orders);

        mFoodList = (RecyclerView) findViewById(R.id.orderLayout);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("orders");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Order, OrderViewHolder> FRBA= new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
                Order.class,
                R.layout.singleorderlayout,
                OrderViewHolder.class,
                mDatabase
                ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {
                viewHolder.setItemName(model.getUsername());
                viewHolder.setItemName(model.getItemname());


            }
        };
        mFoodList.setAdapter(FRBA);


    }
    public  static  class OrderViewHolder extends RecyclerView.ViewHolder {

        View OrderView;
        public OrderViewHolder(View itemView) {
            super(itemView);
            OrderView=itemView;
        }

        public  void  setUserName(String userName){
            TextView username_content=(TextView)OrderView.findViewById(R.id.orderUserName);
            username_content.setText(userName);
        }

        public  void  setItemName(String itemName){
            TextView itemname_content=(TextView)OrderView.findViewById(R.id.orderUserName);
            itemname_content.setText(itemName);
        }
    }
}

