package edu.slcc.briannielson.splash;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //String [] WORDLENGTH = {"4 Letter Words", "5 Letter Words", "6 Letter Words"};

    public void startSinglePlayer(View view) {
        newGame(this);
    }

    public void startMultiPlayer(View view) {
        multiPlayerIntent(this);
    }

    public void startTextPlayerGame(View view) {
        textPlayerGame(this);
    }

    public void openScores(View view) {
        scores(this);
    }

    public void viewContacts(View view) {
        contacts(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //methods for action bar
    public static void newGame(final Activity activity) {
        Intent intent = new Intent(activity, GameActivity.class);
        activity.startActivity(intent);
    }

    public static void multiPlayerIntent(final Activity activity) {
        //create intent for multi-player as separate method
        Intent intent = new Intent(activity, MultiPlayerActivity.class);
        activity.startActivity(intent);
    }

    public static void textPlayerGame(final Activity activity) {
        Intent intent = new Intent(activity, TextActivity.class);
        activity.startActivity(intent);
    }

    public static void scores(final Activity activity) {
        // Update scores xml
        Intent intent = new Intent(activity, ScoreActivity.class);
        activity.startActivity(intent);
    }

    public static void contacts(final Activity activity) {
        Intent intent = new Intent(activity, ContactsActivity.class);
        activity.startActivity(intent);
    }


//    public void openScores(View view) {
//        // Update scores xml
//        Intent intent = new Intent(this, ScoreActivity.class);
//        startActivity(intent);
//    }

    //create separate method for startSinglePlayer to call so the user can decide type of game to play.
//    public void setWordLevel() {
//        final Intent intentFour = new Intent(this, GameActivity.class);
//        final Intent intentFive = new Intent(this, FiveLetterGame.class);
//        final Intent intentSix = new Intent(this, SixLetterGame.class);
//
//        AlertDialog.Builder myAlert = new AlertDialog.Builder(MainActivity.this);
//        myAlert.setTitle("Choose Word Length");
//        myAlert.setItems(WORDLENGTH, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0:
//                        startActivity(intentFour);
//                        break;
//                    case 1:
//                        startActivity(intentFive);
//                        break;
//                    case 2:
//                        startActivity(intentSix);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        AlertDialog alertDialog = myAlert.create();
//        alertDialog.show();
//    }//end of methods for action bar

}
