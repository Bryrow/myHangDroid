package edu.slcc.briannielson.splash;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MultiPlayerActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    EditText editText; // Declare class attribute so that a listener can be added

    String [] GAMETYPE = {"Single Player", "Multi-Player", "Text-Player"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        setupUI(findViewById(R.id.multi_player_parent));

        // Connect to xml
        editText = (EditText) findViewById(R.id.editTextWord);

        // Add listener
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("MYLOG", "afterTextChanged " + s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("MYLOG", "beforeTextChanged " + "Start: " + start + " Count " + count + " After " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("MYLOG", "onTextChanged " + "Start " + start + " Before " + before + " Count " + count);
            }

        });

        EditText editText1 = (EditText) findViewById(R.id.editTextWord);
        editText1.setOnEditorActionListener(this);

    }

    public void startMultiPlayerGame(View view) {
        createWord(this);
        finish();
    } // End of startMultiPlayerGame

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            createWord(this);
            return true;
        }
        return false;
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
                AlertDialog.Builder myAlert = new AlertDialog.Builder(MultiPlayerActivity.this);
                myAlert.setTitle("Choose Game");
                myAlert.setItems(GAMETYPE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                MainActivity.newGame(MultiPlayerActivity.this);
                                finish();
                                break;
                            case 1:
                                MainActivity.multiPlayerIntent(MultiPlayerActivity.this);
                                finish();
                                break;
                            case 2:
                                MainActivity.textPlayerGame(MultiPlayerActivity.this);
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

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public static void createWord(final Activity activity) {
        // Create new editText object from XML
        EditText editText = (EditText) activity.findViewById(R.id.editTextWord);

        // Get word and cast word to a string, and make it all lower case.
        String wordToGuess = editText.getText().toString().toLowerCase();

        // Debug
        Log.d("MYLOG", "Multi-Player Word: " + wordToGuess);

        // Create intent
        Intent intent = new Intent(activity, GameMultiActivity.class);

        // Send word with intent
        intent.putExtra("GUESS_WORD", wordToGuess);

        // Start Activity
        activity.startActivity(intent);
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
                    hideSoftKeyboard(MultiPlayerActivity.this);
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

} // End of class
