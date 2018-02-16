package com.example.android.snapball.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.snapball.R;

/**
 * Created by mayankchuriwal on 17/01/18.
 */

public class ChatFragment extends Fragment {

    public static ChatFragment newInstance()
    {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        return view;
    }
}
