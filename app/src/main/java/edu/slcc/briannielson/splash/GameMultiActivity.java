package edu.slcc.briannielson.splash;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameMultiActivity extends AppCompatActivity {

    String theWord = "";
    int badLetterCount = 0;
    int goodLetterCount = 0;
    int points = 0;

    String [] GAMETYPE = {"Single Player", "Multi-Player", "Text-Player"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game);

        setupUI(findViewById(R.id.multi_game_parent));

        // Get the word from the intent
        String wordToGuess = "";
        wordToGuess = getIntent().getStringExtra("GUESS_WORD"); // Defaults if data does not come with intent

        Log.d("MYLOG", "Word Sent: " + wordToGuess);

        createTextViews(wordToGuess);

        theWord = wordToGuess;
        // setWord();
    }

    private void createTextViews(String word) {
        // Get the layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLetters);

        for(int i = 0; i < word.length(); i++) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.single_letter, null); // create single_letter XML
            // Add view to layout container
            layout.addView(textView);
            // Test run and look for text views
        }
    }

    //Called when user clicks on button set in activity_game.xml
    public void newLetter(View view) {
        //Toast.makeText(this, "newLetter", Toast.LENGTH_SHORT).show();

        //Find the text box with a letter in it using its id... Then cast it to an editText object
        EditText editText = (EditText) findViewById(R.id.editTextLetter);

        //Set it to my string variable
        String letter = editText.getText().toString().toLowerCase();

        //Blank out text field for next guess
        editText.setText("");

        //Test I am getting the letter
        Log.d("MYLOG", "The letter is: " + letter);

        //Check the length of the letter
        if (letter.length() == 1) {
            checkLetter(letter);
        } else {    //Context, text, duration;
            Toast.makeText(this, "Please Enter a Single Letter", Toast.LENGTH_SHORT).show();
        }
    }

    //Receives the users letter guess and checks if the letter given exists in
    public void checkLetter(String letter) {

        //Tracks if the letter was found in the word
        boolean letterGuessed = false;

        //Parse to char for comparison
        char aLetter = letter.charAt(0);

        //Loop to look for the new letter
        for (int i = 0; i < theWord.length(); i++) {
            if (theWord.charAt(i) == aLetter) {
                Log.d("MYLOG", "Letter Found " + aLetter);
                letterGuessed = true;
                goodLetterCount++;
                showLetter(i, aLetter);
            }
        } //End for loop

        //Letter was not found in word, add to wrong guessed letters
        if (!letterGuessed) {
            Log.d("MYLOG", "Letter NOT Found " + aLetter);

            //Count bad letter
            badLetterCount++;

            //Send the failed letter as a string
            wrongLetter(Character.toString(aLetter));
        }

        letterGuessed = false;

        //Won! If the guess count is equal to the length
        Log.d("MYLOG", "Check for win: " + goodLetterCount + " out of " + theWord.length());
        if (goodLetterCount == theWord.length()) {
//            Toast.makeText(this, "You're so Smart!", Toast.LENGTH_SHORT).show();
            points++;

            gameWinner();
        }

    } //End checkLetter


    //WRONG LETTER METHOD
    //Called when user guessed letter is not found in the word
    public void wrongLetter(String badLetter) {

        //Make a reference of the textview of wrong guessed letters in the activity
        TextView textView = (TextView) findViewById(R.id.textViewWrong);

        //Save previous letters
        String previousLetters = textView.getText().toString();

        //Add or Append
        Log.d("MYLOG", "Bad Letter: " + badLetter + " " + badLetterCount);

        if (badLetterCount > 1) {
            textView.setText(previousLetters + " " + badLetter);
        } else {
            textView.setText(badLetter);
        }

        //Make a reference of the IMAGEVIEW in activity_game.xml using the R reference
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if (badLetterCount == 1)
            imageView.setImageResource(R.drawable.hangdroid_1);
        if (badLetterCount == 2)
            imageView.setImageResource(R.drawable.hangdroid_2);
        if (badLetterCount == 3)
            imageView.setImageResource(R.drawable.hangdroid_3);
        if (badLetterCount == 4)
            imageView.setImageResource(R.drawable.hangdroid_4);
        if (badLetterCount == 5)
            imageView.setImageResource(R.drawable.hangdroid_5);
        if (badLetterCount >= 5) {
            //Go to game over activity
            Toast.makeText(this, "The word was: " + theWord, Toast.LENGTH_LONG).show();
            gameOver();
            //clearScreen();

        }
    }

    //Position: position the letter was found
    //Letter Guessed: The letter that the user guessed
    //Change the display layout to the new letter
    public void showLetter(int position, char letterGuessed) {

        //Make a reference of the linearLayout in the activity_game.xml using the R reference
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLetters);

        //Make a reference of the textview of the child within the layout that matches the position of the guessed letter
        TextView textView = (TextView) layout.getChildAt(position);

        //Replace the "_" of the text view of the guessed letter to the textview
        textView.setText(Character.toString(letterGuessed));
    }

    public void gameOver() {
        //Create intent
        Intent intentLose = new Intent(this, GameOverActivity.class);
        //Send data with intent
        intentLose.putExtra("PointsID", points);
        //Start activity
        startActivity(intentLose);
        finish();
    } //Game Over

    public void gameWinner() {
        //Create intent
        Intent intentWin = new Intent(this, GameWinnerActivity.class);
        //Send data with intent
        intentWin.putExtra("PointsID", points);
        //Start activity
        startActivity(intentWin);
        finish();
    } // end gameWinner

    private void setWord() {
        String words = "away baby back been best both call came care come dead does done down else even ever feel find fine from give good guys hard have hear help here home hope hurt idea into just keep kill kind knew know last left like long look love made make mean mind more much must name need next nice okay only over real room said same show some stay stop sure take talk than that them then they this time told very wait want well went were what when will with work your";
        String[] wordsArray = words.split(" ");
        Log.d("MYLOG", "Number of Words: " + wordsArray.length);

        //Get random number and multiply it by the words in the array
        int theNumber = (int) (Math.random() * wordsArray.length);
        Log.d("MYLOG", "Random Number: " + theNumber);

        theWord = wordsArray[theNumber];
        Log.d("MYLOG", "The Word is: " + theWord);
    } //setWord

    public void clearScreen() {
        //Clear guessed letters
        TextView textView = (TextView) findViewById(R.id.textViewWrong);
        textView.setText("Guessed Letters");

        //Reset Counters
        badLetterCount = 0;
        goodLetterCount = 0;

        //Clear guessed word layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLetters);

        //Reset textview to "_"
        for (int i = 0; i < layout.getChildCount(); i++) {
            TextView childTextView = (TextView) layout.getChildAt(i);
            childTextView.setText("_");
        }

        //Set image back to starting/zero image\
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.hangdroid_0);

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
                AlertDialog.Builder myAlert = new AlertDialog.Builder(GameMultiActivity.this);
                myAlert.setTitle("Choose Game");
                myAlert.setItems(GAMETYPE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                MainActivity.newGame(GameMultiActivity.this);
                                finish();
                                break;
                            case 1:
                                MainActivity.multiPlayerIntent(GameMultiActivity.this);
                                finish();
                                break;
                            case 2:
                                MainActivity.textPlayerGame(GameMultiActivity.this);
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
                MainActivity.scores(GameMultiActivity.this);
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
                    hideSoftKeyboard(GameMultiActivity.this);
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

} //End class
