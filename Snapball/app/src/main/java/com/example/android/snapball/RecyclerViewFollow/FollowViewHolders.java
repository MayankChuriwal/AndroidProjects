package com.example.android.snapball.RecyclerViewFollow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.snapball.R;

/**
 * Created by mayankchuriwal on 04/02/18.
 */

public class FollowViewHolders extends RecyclerView.ViewHolder{

    public TextView mEmail;
    public Button mFollow;

    public FollowViewHolders(View itemView)
    {
        super(itemView);
        mEmail = itemView.findViewById(R.id.email);
        mFollow = itemView.findViewById(R.id.follow);
    }

}
