package cn.yumutech.news.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.yumutech.news.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {


    public DailyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this DailyFragment
        return inflater.inflate(R.layout.fragment_fragment, container, false);
    }

}
