package se.kth.networking.hangman.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import se.kth.networking.hangman.MainActivity;
import se.kth.networking.hangman.R;
import se.kth.networking.hangman.http.HttpGet;
import se.kth.networking.hangman.http.HttpPost;
import se.kth.networking.hangman.http.OnResponse;

/**
 * Created by victoraxelsson on 2016-11-22.
 */
public class HomeScreenFragment extends Fragment {

    private EditText txt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);


        txt = (EditText)v.findViewById(R.id.txt_username);

        Button btn = (Button)v.findViewById(R.id.btn_startGame);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(txt.getText().toString());
            }
        });

        //((MainActivity)getActivity()).transit(new HomeScreenFragment());

        return v;
    }

    private boolean isValidUsername(String username){
        return username != null && username.length() > 3;
    }

    private void startGame(final String username){
        if(isValidUsername(username)){

            new HttpGet(((MainActivity)getActivity()).getBASE_URL() + "/start/" + username, new OnResponse<String>() {
                @Override
                public void onResponse(String res) {
                    ((MainActivity)getActivity()).saveKey("username", username);
                    ((MainActivity)getActivity()).transit(new GameFragment());
                }
            }).execute();

        }else{
            Toast.makeText(getContext(), "Your username got to be at least 3 char long", Toast.LENGTH_LONG).show();
        }
    }
}
