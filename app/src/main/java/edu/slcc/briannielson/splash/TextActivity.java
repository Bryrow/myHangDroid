package edu.slcc.briannielson.splash;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class TextActivity extends AppCompatActivity {

    private EditText editText;
    private SharedPreferences preferences;
    private TextView textView;
    private String textWord;
    private String friendPhone;
    private String texterPhone;

    String [] GAMETYPE = {"Single Player", "Multi-Player", "Text-Player"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

//        setupUI(findViewById(R.id.text_player_parent));

        // Get text message from preferences
        preferences = getSharedPreferences("TEXT_MSGS", MODE_PRIVATE);
        // Read preferences to get friend's phone if called from contacts activity
        friendPhone = getIntent().getStringExtra("Phone"); // Defaults if data does not come with intent.

        Log.d("MYLOG", "Friend: " + friendPhone);

        // Read preferences
        getTextFromPref();
    }

    public void setTextMessage(View view) {
        getTextFromPref();
    }

    public void getTextFromPref() {
        // Get text messages from preferences
        // preferences = getSharedPreferences("TEXT_MSGS", MODE_PRIVATE);
        // Read preferences
        textWord = preferences.getString("TextedWord", "NO WORD"); // No word if preferences not found
        texterPhone = preferences.getString("TexterPhone", "NO PHONE"); // NO PHONE if preferences are not found
        textView = (TextView) findViewById(R.id.editTextWord);

        // set up boolean values
        boolean phone = true;
        boolean word = true;
        boolean friend = true;
        if (textWord == "NO WORD") word = false;
        if (textWord == "NO PHONE") phone = false;
        if (friendPhone == null) friend = false;

        // word but no friend selected
        if (!friend && word) {
            textView.setText(textWord);
            textWord = "";
            texterPhone = "";
            return;
        }

        // word and friend phone then check phone
        if (word && phone) {
            if (friendPhone.equals(texterPhone)) {
                textView.setText(textWord);
                textWord = "";
                texterPhone = "";

            } else {
                Toast.makeText(this, "No Text From Selected Friend", Toast.LENGTH_LONG).show();
            }

            return;
        }

        if (!word) {
            Toast.makeText(this, "No Text Received", Toast.LENGTH_LONG).show();
        }

        // Get the text view from the texted word
        if (textWord == "NO WORD") {
            textWord = "";
            Toast.makeText(this, "No Text Received", Toast.LENGTH_LONG).show();
        }
        Log.d("MYLOG", "Texted Word: " + textWord);

        // Put texted word in text view
        textView = (TextView) findViewById(R.id.editTextWord);
        textView.setText(textWord);
    }

    //Play button
    public void startMultiPlayerGame(View view) {
        // Connect to xml
        // Get word and cast word to a String
        String textWord = textView.getText().toString();

        if (textWord.length() > 0) {
            // Clear field for next word
            textView.setText("");
            // Clear word from shared preferences
            preferences.edit().remove("TextedWord").apply();
            Log.d("MYLOG", "Removed Texted Word: " + textView);
            // Create intent
            Intent intent = new Intent(this, GameMultiActivity.class);
            // Send word with intent
//            intent.putExtra("GuessID", textWord);
             intent.putExtra("GUESS_WORD", textWord);

            // Start activity
            startActivity(intent);

        } else {
            Toast.makeText(this, "No Word Found - Try Clicking Get New Text Button", Toast.LENGTH_LONG).show();
        }
    } // End of startMultiPlayerGame

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
                AlertDialog.Builder myAlert = new AlertDialog.Builder(TextActivity.this);
                myAlert.setTitle("Choose Game");
                myAlert.setItems(GAMETYPE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                MainActivity.newGame(TextActivity.this);
                                finish();
                                break;
                            case 1:
                                MainActivity.multiPlayerIntent(TextActivity.this);
                                finish();
                                break;
                            case 2:
                                MainActivity.textPlayerGame(TextActivity.this);
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
                MainActivity.scores(this);
                finish();

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//    }
//
//    public void setupUI(View view) {
//
//        //Set up touch listener for non-text box views to hide keyboard.
//        if(!(view instanceof EditText)) {
//
//            view.setOnTouchListener(new View.OnTouchListener() {
//
//                public boolean onTouch(View v, MotionEvent event) {
//                    hideSoftKeyboard(TextActivity.this);
//                    return false;
//                }
//
//            });
//        }
//
//        //If a layout container, iterate over children and seed recursion.
//        if (view instanceof ViewGroup) {
//
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//
//                View innerView = ((ViewGroup) view).getChildAt(i);
//
//                setupUI(innerView);
//            }
//        }
//    }

} // End of class
