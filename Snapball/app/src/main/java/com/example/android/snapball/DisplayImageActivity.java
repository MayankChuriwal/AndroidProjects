package com.example.android.snapball;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.snapball.RecyclerViewStory.StoryObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayImageActivity extends AppCompatActivity {

    String userId;
    private ImageView mImage;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        Bundle b = getIntent().getExtras();
        userId = b.getString("userId");
        mImage = findViewById(R.id.image);

        listenForData();
    }

    private ArrayList<String> imageUrlList = new ArrayList<>();
    private void listenForData() {
            DatabaseReference followingStoryDB = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            followingStoryDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String imageUrl = "";
                    long timestampBeg = 0;
                    long timestampEnd = 0;

                    for(DataSnapshot storySnapshot : dataSnapshot.child("story").getChildren()){
                        if(storySnapshot.child("timeStampBeg").getValue()!=null)
                        {
                            timestampBeg = Long.parseLong(storySnapshot.child("timeStampBeg").getValue().toString());
                        }

                        if(storySnapshot.child("timeStampEnd").getValue()!=null)
                        {
                            timestampEnd = Long.parseLong(storySnapshot.child("timeStampEnd").getValue().toString());
                        }

                        if(storySnapshot.child("imageUrl").getValue()!=null)
                        {
                            imageUrl = storySnapshot.child("imageUrl").getValue().toString();
                        }

                        long timestampCurrent = System.currentTimeMillis();

                        if(timestampCurrent >= timestampBeg && timestampCurrent <= timestampEnd){
                            imageUrlList.add(imageUrl);
                            if(!started) {
                                started = true;
                                initializeDisplay();
                            }

                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            final Handler handler = new Handler();
            final int delay = 5000;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeImage();
                    handler.postDelayed(this,delay);
                }
            },delay);
        }

    private int imageIterator = 0;
    private void initializeDisplay() {
        Glide.with(getApplication()).load(imageUrlList.get(imageIterator)).into(mImage);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });

    }

    private void changeImage() {
        if(imageIterator==imageUrlList.size()-1){
            finish();
            return;
        }
        imageIterator++;
        Glide.with(getApplication()).load(imageUrlList.get(imageIterator)).into(mImage);
    }


}
