package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private Button startButton;
    private ImageView imageView1;
    private ImageView imageView2;
    private TextView textView;
    private TextView HumanText;
    private TextView AndroidText;
    Boolean mGameOver;
    boolean isFirstImage=true;
    private int level=3;
    private int Human=0;
    private int AndroidBot=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button0);
        mBoardButtons[1] = (Button) findViewById(R.id.button1);
        mBoardButtons[2] = (Button) findViewById(R.id.button2);
        mBoardButtons[3] = (Button) findViewById(R.id.button3);
        mBoardButtons[4] = (Button) findViewById(R.id.button4);
        mBoardButtons[5] = (Button) findViewById(R.id.button5);
        mBoardButtons[6] = (Button) findViewById(R.id.button6);
        mBoardButtons[7] = (Button) findViewById(R.id.button7);
        mBoardButtons[8] = (Button) findViewById(R.id.button8);
        mInfoTextView = (TextView) findViewById(R.id.information);
        HumanText=(TextView)findViewById(R.id.Human) ;
        AndroidText=(TextView)findViewById(R.id.AndroidBot);
        imageView1=(ImageView)findViewById(R.id.ImageView01);
        imageView2=(ImageView) findViewById(R.id.ImageView02);
        textView=(TextView)findViewById(R.id.coinText);
        imageView2.setVisibility(View.INVISIBLE);
        HumanText.setText(getResources().getString(R.string.HuamnText)+"0");
        AndroidText.setText(getResources().getString(R.string.AndBotText)+"0");
        mGame = new TicTacToeGame();
        startNewGame();

    }

    private void fileCoin(){
        //imageView1.setVisibility(View.VISIBLE);
        //textView.setVisibility(View.VISIBLE);




            if (isFirstImage) {

                applyRotation(0, -90);
                isFirstImage = !isFirstImage;

            } else {
                applyRotation(0, -90);
                isFirstImage = !isFirstImage;
            }


    }

    private void applyRotation(float start, float end) {
// Find the center of image
        final float centerX = imageView1.getWidth() / 2.0f;
        final float centerY = imageView1.getHeight() / 2.0f;


// The animation listener is used to trigger the next animation
        final FlipAnimation rotation =
                new FlipAnimation(start, end, centerX, centerY);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, imageView1, imageView2));

        if (isFirstImage)
        {
            imageView1.startAnimation(rotation);
        } else {
            imageView2.startAnimation(rotation);
        }

    }




    private void startNewGame() {
        isFirstImage=true;
        int ranNum=(int)(Math.random()*2);
        System.out.println(ranNum);
        if(ranNum==1){

            for(int i=0;i<4;i++) {
                fileCoin();

            }
            textView.setText(R.string.GoFirst);



            mGameOver = false;
            mGame.clearBoard();
            //---Reset all buttons
            for (int i = 0; i < mBoardButtons.length; i++) {
                mBoardButtons[i].setText("");
                mBoardButtons[i].setEnabled(true);
                mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            }


            mInfoTextView.setText(R.string.GoFirst);

        }else{


                fileCoin();


            textView.setText(R.string.AGoFirst);



            mGameOver = false;
            mGame.clearBoard();
            //---Reset all buttons
            for (int i = 0; i < mBoardButtons.length; i++) {
                mBoardButtons[i].setText("");
                mBoardButtons[i].setEnabled(true);
                mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            }

            mInfoTextView.setText(R.string.AGoFirst);
            int move = mGame.getComputerMove(level);
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            mInfoTextView.setTextColor(Color.rgb(0, 0, 0));

            mInfoTextView.setText(R.string.you_turn);

        }



    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }

    public void newGame(View v) {
        startNewGame();
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;
        public ButtonClickListener(int location) {
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    //--- If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.ATurn);
                        int move = mGame.getComputerMove(level);
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.you_turn);
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.Tie);
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.Won);
                        Human++;
                        //System.out.println(getResources().getString(R.string.HuamnText+Human));
                        HumanText.setText(getResources().getString(R.string.HuamnText)+Human);
                        mGameOver = true;
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.AWon);
                        AndroidBot++;
                        //System.out.println(getResources().getString(R.string.AndBotText));
                        AndroidText.setText(getResources().getString(R.string.AndBotText)+AndroidBot);
                        mGameOver = true;
                    }
                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.level1:
                level=1;
                Toast.makeText(this, R.string.level1Info,
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.level2:
                level=2;
                Toast.makeText(this, R.string.level2Info,
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.level3:
                level=3;
                Toast.makeText(this, R.string.level3Info,
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return false;
    }
}
