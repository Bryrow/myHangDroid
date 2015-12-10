package edu.slcc.briannielson.splash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

/**
 * Created by briannielson on 12/9/15.
 */
public class GifWinner extends View {

    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth, movieHieght;
    private long movieDuration;
    private long movieStart;

    public GifWinner(Context context){
        super(context);
        init(context);
    }

    public GifWinner(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public GifWinner(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setFocusable(true);
        gifInputStream = context.getResources().openRawResource(R.drawable.androidify_winner);

        gifMovie = Movie.decodeStream(gifInputStream);
        movieWidth = gifMovie.width();
        movieHieght = gifMovie.height();
        movieDuration = gifMovie.duration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(movieWidth, movieHieght);
    }

    public int getMovieWidth(){
        return movieWidth;
    }

    public int getMovieHieght(){
        return movieHieght;
    }

    public long getMovieDuration(){
        return movieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas){
        long now = SystemClock.uptimeMillis();

        if(movieStart == 0){
            movieStart = now;
        }

        if(gifMovie != null){
            int dur = gifMovie.duration();
            if(dur == 0){
                dur = 1000;
            }

            int relTime = (int)((now - movieStart) % dur);
            gifMovie.setTime(relTime);
            gifMovie.draw(canvas, 0, 0);
            invalidate();
        }
    }
}
