package se.kth.networking.hangman;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import se.kth.networking.hangman.fragments.HomeScreenFragment;

/**
 * Created by victoraxelsson on 2016-11-22.
 */
public class MainActivity extends FragmentActivity {

    private final String BASE_URL = "http://130.229.186.25:3000";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        transit(new HomeScreenFragment());
    }


    public void transit(Fragment fr){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_container, fr).addToBackStack(null).commit();
    }

    public void popFromBackStack(){
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

    public void saveKey(String key, String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getKey(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       return preferences.getString(key, null);
    }

    public void playSound(int rawResource){
        MediaPlayer mp = MediaPlayer.create(this, rawResource);
        mp.start();
    }

    public String getBASE_URL(){
        return BASE_URL;
    }
}
