package se.kth.networking.hangman;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import se.kth.networking.hangman.fragments.HomeScreenFragment;

/**
 * Created by victoraxelsson on 2016-11-22.
 */
public class MainActivity extends FragmentActivity {

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
}
