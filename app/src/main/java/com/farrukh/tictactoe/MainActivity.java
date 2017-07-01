package com.farrukh.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.farrukh.tictactoe.R.id.gridLayout;
import static com.farrukh.tictactoe.R.id.winnerText;

public class MainActivity extends AppCompatActivity {

    public boolean gameActive=true;

    public int currentPlayer=1;  // 1 = player1    2 = player2

    public int flag=0; //to keep track of a draw game

    public int p1_count=0;  //player 1 win count
    public int p2_count=0;  //player 2 win count

    public int[] tileStatus={3,3,3,3,3,3,3,3,3}; //Status 3 indicates the tile is unoccupied

    public int[][] winningSequences={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void placeMark(View view)
    {
        ImageView tile=(ImageView) view;
        String tile_id=tile.getTag().toString();  //trying to know which imageView was touched
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);

        if(tileStatus[Integer.parseInt(tile_id)]==3 && gameActive) {
            flag++;
            tile.startAnimation(animSlide);
            tileStatus[Integer.parseInt(tile_id)] = currentPlayer;

            if (currentPlayer == 2) {
                tile.setImageResource(R.drawable.o);
                currentPlayer = 1;
            } else {
                tile.setImageResource(R.drawable.x);
                currentPlayer = 2;
            }

            for (int[] win : winningSequences) {

                if (tileStatus[win[0]] == tileStatus[win[1]] &&
                        tileStatus[win[1]] == tileStatus[win[2]] && tileStatus[win[0]] != 3) {
                    gameActive = false;
                    LinearLayout winnerBox = (LinearLayout) findViewById(R.id.winnerBox);
                    winnerBox.setVisibility(View.VISIBLE);

                    TextView winner = (TextView) findViewById(R.id.winnerText);

                    if (currentPlayer == 1) {
                        winner.setText("O has won!");
                        p2_count++;
                        flag=0;  //using this here in case u already won u dont need to check for draw game
                    }
                    else {
                        winner.setText("X has won!");
                        p1_count++;
                        flag=0; //using this here in case u already won u dont need to check for draw game
                    }
                }
                else if(flag==9)
                {
                    gameActive = false;
                    LinearLayout winnerBox=(LinearLayout)findViewById(R.id.winnerBox);
                    winnerBox.setVisibility(View.VISIBLE);
                    TextView winner=(TextView)findViewById(R.id.winnerText);
                    winner.setText("Game ended in a draw!");
                }
            }
        }
    }

    public void resetGame(View view)
    {
        currentPlayer=1;
        flag=0;
        gameActive=true;

        GridLayout gridLayout=(GridLayout)findViewById(R.id.gridLayout);

        for(int i=0;i<tileStatus.length;i++)
        {
            tileStatus[i]=3;
        }
        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
        ((LinearLayout)findViewById(R.id.winnerBox)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.p1Text)).setText("X : "+String.valueOf(p1_count));
        ((TextView)findViewById(R.id.p2Text)).setText("O : "+String.valueOf(p2_count));
    }
}
