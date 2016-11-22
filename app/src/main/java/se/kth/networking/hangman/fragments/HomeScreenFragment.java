package se.kth.networking.hangman.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.kth.networking.hangman.MainActivity;
import se.kth.networking.hangman.R;

/**
 * Created by victoraxelsson on 2016-11-22.
 */
public class HomeScreenFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        TextView txt = (TextView)v.findViewById(R.id.lbl_homeText);
        txt.setText("Cabbach");

        //((MainActivity)getActivity()).transit(new HomeScreenFragment());

        return v;
    }
}
