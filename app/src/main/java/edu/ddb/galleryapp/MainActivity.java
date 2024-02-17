package edu.ddb.galleryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String TAG = "MainActivity";

    Pokemon[] pokemons = {
            new Pokemon("Bulbasaur", "Venasaur", 1, 3,
                     R.raw.bulbasaur, R.drawable.bulbasaur, R.drawable.venusaur,Type.Grass ),
            new Pokemon("Charmander", "Charizard", 4, 6,
                     R.raw.charmander, R.drawable.charmander, R.drawable.charizard,Type.Fire ),
            new Pokemon("Squirtle", "Blastoise", 7, 9,
                     R.raw.squirtle, R.drawable.squirtle, R.drawable.blastoise,Type.Water )
    };

    int cardNo = 0;
    boolean isFront = true;
    ImageView imgCard;
    TextView tvName;
    TextView tvDetais;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("GalleryApp");

        imgCard = findViewById(R.id.imgCard);
        tvName = findViewById(R.id.tvName);
        tvDetais = findViewById(R.id.tvDetails);

        gestureDetector = new GestureDetector(this, this);

        setInfo();

        updateCard();

    }
    // Show the Menu on the app
   @Override
   public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
   }

    // React to the clicking of a menu item.
   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      int id = item.getItemId();
      isFront = true;

       if (id == R.id.bulbasaur)
       {
            Log.d(TAG, "onOptionsItemSelected: " + (String)item.getTitle());
            //Do something for bulbasaur
            cardNo = 0;

      } else if (id == R.id.charmander)
      {
            Log.d(TAG, "onOptionsItemSelected: " + (String)item.getTitle());
            cardNo = 1;

      }else
      {
            Log.d(TAG, "onOptionsItemSelected: " + (String)item.getTitle());
            cardNo = 2;
      }
       updateCard();
       return super.onOptionsItemSelected(item);
   }


    private void setInfo()
    {
        for (Pokemon pokemon : pokemons) {

            String info = readFile(pokemon.getInfoFile());
            pokemon.setInfo(info);
        }
    }

    private String readFile(int fileId) {
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer;
        try
        {
            inputStream = getResources().openRawResource(fileId);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            stringBuffer = new StringBuffer();

            String data;

            while((data = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(data).append("\n");
            }

            // Clean up objects

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            Log.d(TAG, "readFile: " + stringBuffer.toString());
            return stringBuffer.toString();
        }
        catch ( Exception e)
        {
            Log.d(TAG, "readFile: " + e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private void updateCard()
    {
        if(isFront){
            imgCard.setImageResource(pokemons[cardNo].getImg());
            tvName.setText(pokemons[cardNo].getName() + " No. " +
                    String.format("%03d", pokemons[cardNo].getNumber()));
            tvDetais.setText("");
        }else{
            imgCard.setImageResource(pokemons[cardNo].getEvolveImg());
            tvName.setText(pokemons[cardNo].getEvolveName() + " No. " +
                    String.format("%03d", pokemons[cardNo].getEvolveNumber()));
            tvDetais.setText(pokemons[cardNo].getInfo());
        }

        Log.d(TAG, "updateToNextCard: ");
    }

    // One of those things that I have to remember!!!!!!!!!!!!!!!!!!!!!
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e)
    {
        Log.d(TAG, "onSingleTapUp: ");
        String message;
        try
        {

            Animation shrink = AnimationUtils.loadAnimation(this, R.anim.shrink);
            Animation grow = AnimationUtils.loadAnimation(this, R.anim.grow);
            shrink.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isFront = !isFront;
                    updateCard();
                    imgCard.startAnimation(grow);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            imgCard.startAnimation(shrink);


            message = "Go To Back";

            //updateCard();

            Log.d(TAG, "onSingleTapUp: " + message);

        }
        catch(Exception ex){
            Log.e(TAG, "onSingleTapUp: " + ex.getMessage());
            ex.printStackTrace();
        }


        return true;
    }



    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, 
                           @NonNull MotionEvent e2, 
                           float velocityX, 
                           float velocityY)
    {

        isFront = true;

        if(velocityX > 0 ){
            Log.d(TAG, "onFling: Positive X");
            cardNo = (cardNo + 1) % 3;

            Animation move = AnimationUtils.loadAnimation(this, R.anim.moveright);
            move.setAnimationListener(new AnimationSwipeListener() );
            imgCard.startAnimation(move);


        }else{
            Log.d(TAG, "onFling: Negative X");
            cardNo = (pokemons.length - 1 + cardNo) % 3;

            Animation move = AnimationUtils.loadAnimation(this, R.anim.moveleft);
            move.setAnimationListener(new AnimationSwipeListener() );
            imgCard.startAnimation(move);

        }

        //updateToNextCard();
        return true;
    }

    private class AnimationSwipeListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            Log.d(TAG, "onAnimationEnd: ");
            updateCard();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}