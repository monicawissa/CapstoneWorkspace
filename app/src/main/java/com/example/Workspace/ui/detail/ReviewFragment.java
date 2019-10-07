package com.example.Workspace.ui.detail;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Workspace.R;
import com.example.Workspace.adapter.PlaceUserRatingAdapter;
import com.example.Workspace.network.PlaceUserRating;
import com.example.Workspace.utilities.GoogleApiUrl;

import java.util.ArrayList;


public class ReviewFragment extends Fragment {


    public ReviewFragment() {
        // Required empty public constructor
    }
    private ArrayList<PlaceUserRating> mPlaceUserRatingArrayList = new ArrayList<>();
    private PlaceUserRatingAdapter mPlaceUserRatingAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        mPlaceUserRatingArrayList = getArguments()
                .getParcelableArrayList(GoogleApiUrl.CURRENT_LOCATION_USER_RATING_KEY);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        if (mPlaceUserRatingArrayList.size() == 0) {
            rootView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.empty_view).setVisibility(View.GONE);
            mPlaceUserRatingAdapter = new PlaceUserRatingAdapter(getContext(), mPlaceUserRatingArrayList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mPlaceUserRatingAdapter);
        }
        return rootView;
    }

}
