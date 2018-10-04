package de.janl1.tsquery.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.janl1.tsquery.R;

public class ServerinfoFragment extends Fragment {


    public ServerinfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_serverinfo, container, false);

        return root;
    }

}
