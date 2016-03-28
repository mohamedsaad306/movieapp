package com.webcraft.android.mov_app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Mohamed on 25/03/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)

  public class movie_fragment extends Fragment {

    public movie_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //the grid data
        GridView movies_grid= (GridView)rootView.findViewById(R.id.gridView_movies);




        return rootView;
    }
}
