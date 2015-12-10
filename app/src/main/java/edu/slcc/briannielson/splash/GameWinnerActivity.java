package edu.slcc.briannielson.splash;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameWinnerActivity extends AppCompatActivity {

    String [] GAMETYPE = {"Single Player", "Multi-Player", "Text-Player"};

    private int playerPoints = 0;
    GifWinner gifViewWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_winner);

        setupUI(findViewById(R.id.game_winner_parent));

        int points = getIntent().getIntExtra("PointsID", 0); // 0 is the default data in case no data was sent.

        TextView textView = (TextView) findViewById(R.id.textViewPoints); //Specified from game over xml
        textView.setText(String.valueOf(points)); //All text fields are Strings... it will accept an int but will call a different method.

        gifViewWinner = (GifWinner) findViewById(R.id.winner);

        playerPoints = points;
    }

    public void saveWinScore(View view) {
        savedScore();
    }

    // Called from button onClick in activities xml
    public void savedScore() {
        // Set up to store preferences
        SharedPreferences preferences = getSharedPreferences("WORD_SCORES", Context.MODE_PRIVATE);
        // Get name from gameOver xml
        EditText editText = (EditText) findViewById(R.id.editTextName);
        // Set it to a string
        String name = editText.getText().toString();

        // Start the preference editor
        SharedPreferences.Editor editor = preferences.edit();
        // Get previous scores using the KEY
        String previousScores = preferences.getString("SCORES", "");
        Log.d("MYLOG", "Previous Scores: " + previousScores);

        // KEY = SCORES, VALUE = Cancatinated String..
        editor.putString("SCORES", name + " " + playerPoints + " POINT(S)\n" + previousScores);
        // Saves buffer
        editor.apply();

        // Name X POINTS \n NAME2 Y POINTS
        Toast.makeText(this, "Score Saved", Toast.LENGTH_SHORT).show();
        editText.setText("");
        // finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_new:
                AlertDialog.Builder myAlert = new AlertDialog.Builder(GameWinnerActivity.this);
                myAlert.setTitle("Choose Game");
                myAlert.setItems(GAMETYPE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                MainActivity.newGame(GameWinnerActivity.this);
                                finish();
                                break;
                            case 1:
                                MainActivity.multiPlayerIntent(GameWinnerActivity.this);
                                finish();
                                break;
                            case 2:
                                MainActivity.textPlayerGame(GameWinnerActivity.this);
                                finish();
                                break;
                            default:
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = myAlert.create();
                alertDialog.show();
                break;

            case R.id.action_scores:
                MainActivity.scores(GameWinnerActivity.this);
                finish();

            case R.id.action_settings:
                return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(GameWinnerActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

}
