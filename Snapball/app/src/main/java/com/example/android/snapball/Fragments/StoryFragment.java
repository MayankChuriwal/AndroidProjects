package com.example.android.snapball.Fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.snapball.R;
import com.example.android.snapball.RecyclerViewStory.StoryAdapter;
import com.example.android.snapball.RecyclerViewStory.StoryObject;
import com.example.android.snapball.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mayankchuriwal on 17/01/18.
 */

public class StoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static StoryFragment newInstance()
    {
        StoryFragment fragment = new StoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story,container,false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new StoryAdapter(getDataSet(),getContext());
        mRecyclerView.setAdapter(mAdapter);

        Button mRefresh = view.findViewById(R.id.refresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                listenForData();
            }
        });

        return view;
    }

    private void clear() {
        int size = this.results.size();
        this.results.clear();
        mAdapter.notifyItemRangeChanged(0,size);
    }

    private ArrayList<StoryObject> results = new ArrayList<>();

    private ArrayList<StoryObject> getDataSet() {
        listenForData();
        return results;
    }

    private void listenForData() {
        for(int i= 0; i< UserInformation.listFollowing.size(); i++)
        {
            DatabaseReference followingStoryDB = FirebaseDatabase.getInstance().getReference().child("users").child(UserInformation.listFollowing.get(i));
            followingStoryDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.child("email").getValue().toString();
                    String uid = dataSnapshot.getRef().getKey();
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

                        long timestampCurrent = System.currentTimeMillis();

                        if(timestampCurrent >= timestampBeg && timestampCurrent <= timestampEnd){
                            StoryObject obj = new StoryObject(email,uid);
                            if(!results.contains(obj)) {
                                results.add(obj);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
