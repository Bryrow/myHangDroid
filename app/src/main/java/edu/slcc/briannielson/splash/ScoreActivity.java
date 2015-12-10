package edu.slcc.briannielson.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    String [] GAMETYPE = {"Single Player", "Multi-Player", "Text-Player"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Find preferences
        SharedPreferences preferences = getSharedPreferences("WORD_SCORES", MODE_PRIVATE);
        // Read preferences
        String scores = preferences.getString("SCORES", "NO SCORES"); // "NO SCORES", uses that string if "SCORES" was empty.
        // Get the textveiw for scores
        TextView textView = (TextView) findViewById(R.id.textViewScores);
        // Put scores in textview
        textView.setText(scores);
    }

    public void clearScores(View view) {
        clearScreen();
    }

    private void clearScreen() {
        SharedPreferences preferences = getSharedPreferences("WORD_SCORES", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Scores Cleared", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_new:
                AlertDialog.Builder myAlert = new AlertDialog.Builder(ScoreActivity.this);
                myAlert.setTitle("Choose Game");
                myAlert.setItems(GAMETYPE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                MainActivity.newGame(ScoreActivity.this);
                                finish();
                                break;
                            case 1:
                                MainActivity.multiPlayerIntent(ScoreActivity.this);
                                finish();
                                break;
                            case 2:
                                MainActivity.textPlayerGame(ScoreActivity.this);
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

            case R.id.action_settings:
                return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
