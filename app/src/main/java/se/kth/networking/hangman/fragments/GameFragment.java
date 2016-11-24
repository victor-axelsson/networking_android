package se.kth.networking.hangman.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import se.kth.networking.hangman.MainActivity;
import se.kth.networking.hangman.R;
import se.kth.networking.hangman.http.HttpPost;
import se.kth.networking.hangman.http.OnResponse;

/**
 * Created by victoraxelsson on 2016-11-24.
 */

public class GameFragment extends Fragment {

    private ImageView img;
    private int imgStage;
    private TextView lbl_output;
    private EditText txt;

    public GameFragment(){
        imgStage = 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_fragment, container, false);

        ((MainActivity)getActivity()).playSound(R.raw.day);

        img = (ImageView)v.findViewById(R.id.img_hangman);
        txt = (EditText)v.findViewById(R.id.txt_myGuess);

        lbl_output = (TextView)v.findViewById(R.id.lbl_output);

        Button btn = (Button)v.findViewById(R.id.btn_guess);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGuess(txt.getText().toString());
            }
        });

        setGuessedImage();

        return v;
    }

    private Bitmap getBitmapFromResource(int resource){
        return BitmapFactory.decodeResource(getResources(), resource);
    }

    private void setGuessedImage() {
        switch (imgStage){
        case 0:
            img.setImageBitmap(getBitmapFromResource(R.drawable.one));
            break;
        case 1:
            img.setImageBitmap(getBitmapFromResource(R.drawable.two));
            break;
        case 2:
            img.setImageBitmap(getBitmapFromResource(R.drawable.three));
            break;
        case 3:
            img.setImageBitmap(getBitmapFromResource(R.drawable.four));
            break;
        case 4:
            img.setImageBitmap(getBitmapFromResource(R.drawable.five));
            break;
        case 5:
            img.setImageBitmap(getBitmapFromResource(R.drawable.six));
            break;
        case 6:
            img.setImageBitmap(getBitmapFromResource(R.drawable.seven));
            break;
        }
    }

    private boolean isValidGuess(String guess){
        return guess != null && guess.length() > 0;
    }


    private void doGuess(String guess){
        if(isValidGuess(guess)){
            //Now what?

            String username = ((MainActivity)getActivity()).getKey("username");
            String uri = ((MainActivity)getActivity()).getBASE_URL() + "/guess/" + username;


            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("guess", guess);

            new HttpPost(uri, new OnResponse<String>() {
                @Override
                public void onResponse(String res) {

                    dealWithResponse(res);

                    Log.i("Res", res);
                }
            },  postDataParams).execute();

            setGuessedImage();
        }else{
            Toast.makeText(getContext(), "That is no guess!", Toast.LENGTH_SHORT).show();
        }
    }

    private void dealWithResponse(String response){
        try {
            JSONObject obj = new JSONObject(response);

            boolean success = obj.getBoolean("success");
            int tries = obj.getInt("tries");
            String word = obj.getString("word");
            String message = obj.getString("message");


            if(tries <= 0){
                ((MainActivity)getActivity()).playSound(R.raw.limitations);
            }

            if(!success){
                imgStage++;
                setGuessedImage();

                if(tries > 0 ){
                    ((MainActivity)getActivity()).playSound(R.raw.opinions);
                }
            }


            lbl_output.setText(message);
            txt.setText("");



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
