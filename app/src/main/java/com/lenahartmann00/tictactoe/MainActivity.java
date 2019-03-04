package com.lenahartmann00.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //red: 0, yellow: 1, empty: 2
    int activePlayer = 0; //team red beginns
    int[] fieldState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameEnded = false;

    TextView winnerAnouncment;
    Button restartButton;
    GridLayout board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        winnerAnouncment = findViewById(R.id.txt_winner_announcement);
        restartButton = findViewById(R.id.btn_play_again);
        board = findViewById(R.id.gridlayout);
    }

    public void startNewGame(View view) {
        for (int i = 0; i < fieldState.length; i++) {
            fieldState[i] = 2;
        }
        activePlayer = 0;
        gameEnded = false;

        winnerAnouncment.setVisibility(winnerAnouncment.INVISIBLE);
        restartButton.setVisibility(restartButton.INVISIBLE);

        for (int i = 0; i < board.getChildCount(); i++) {
            ImageView coin = (ImageView) board.getChildAt(i);
            coin.setImageDrawable(null);
        }
    }

    public void dropIn(View view) {
        ImageView coin = (ImageView) view;
        int tag = Integer.parseInt(coin.getTag().toString());
        if (!gameEnded && fieldState[tag] == 2) {
            fieldState[tag] = activePlayer;
            coin.setTranslationY(-1500);
            if (activePlayer == 0) {
                coin.setImageResource(R.drawable.red_coin);
                activePlayer = 1;
            } else {
                coin.setImageResource(R.drawable.yellow_coin);
                activePlayer = 0;
            }
            coin.animate().translationY(0).setDuration(200);
            testIfEndOfGameIsReached();
        }
    }

    private void testIfEndOfGameIsReached() {
        //Check if any winning position is reached
        for (int[] winningPosition : winningPositions) {
            if (fieldState[winningPosition[0]] != 2
                    && fieldState[winningPosition[0]] == fieldState[winningPosition[1]]
                    && fieldState[winningPosition[0]] == fieldState[winningPosition[2]]) {
                gameEnded = true;
            }
        }

        if (gameEnded) {
            if (activePlayer == 1) {
                announceWinner("red");
            } else if (activePlayer == 0) {
                announceWinner("yellow");
            }
        } else { //Check if all fields have been selected
            boolean gameEnded = true;
            for (int counterstate : fieldState) {
                if (counterstate == 2) gameEnded = false;
            }
            if (gameEnded) {
                announceWinner("nobody");
            }
        }
    }

    private void announceWinner(String winner) {
        if (winner.equals("nobody")) {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
            winnerAnouncment.setText("Nobody has won :(");
        } else {
            Toast.makeText(this, "Team " + winner + " won!", Toast.LENGTH_SHORT).show();
            winnerAnouncment.setText("The winner is: " + winner);
        }

        winnerAnouncment.setVisibility(winnerAnouncment.VISIBLE);
        restartButton.setVisibility(restartButton.VISIBLE);
    }
}
