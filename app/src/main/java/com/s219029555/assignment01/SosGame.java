package com.s219029555.assignment01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SosGame extends AppCompatActivity {
    private GridView gvBoard;
    private String selectedString;
    private List<Block> blocks;
    private int player1Score, player2Score, selectedBlocks;
    private TextView txtPlayer1Score, txtPlayer2Score;
    private Button btnS, btnO, btnRestart, btnPlayer1, btnPlayer2, btnQuit;
    private boolean player1_turn;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    private int[][] trackBoard;
    static boolean response;
    private RelativeLayout rlView;
    private int gridSize = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_game);
        player1_turn =true;
        linkUI();
        restartGame();
        linkAction();
    }
    //Link components to User Interface
    private void linkUI(){
        gvBoard = (GridView) findViewById(R.id.gvBoard);
        btnO = (Button) findViewById(R.id.btnO);
        btnS = (Button) findViewById(R.id.btnS);
        txtPlayer1Score = (TextView) findViewById(R.id.txtPlayer1Score);
        txtPlayer2Score = (TextView) findViewById(R.id.txtPlayer2Score);
        btnPlayer1 = (Button) findViewById(R.id.btnPlayer1);
        btnPlayer2 = (Button) findViewById(R.id.btnPlayer2);
        btnRestart =(Button)  findViewById(R.id.btnRestart);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        rlView = (RelativeLayout) findViewById(R.id.rlView);
    }

    private void dialog(String title, int action){
        builder = new AlertDialog.Builder(SosGame.this);
        builder.setTitle(title);
        if(action!= 2){
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(action==0)
                    startActivity( new Intent(SosGame.this,MainActivity.class));
                if(action==1)
                    restartGame();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                response =false;
            }
        });
        }else
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    restartGame();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void restartGame(){
        gvBoard.setNumColumns(gridSize);
        btnO.setBackgroundColor(getResources().getColor(R.color.active));
        btnS.setBackgroundColor(getResources().getColor(R.color.none_active));
        //initialize values and scores
        selectedString = "O";
        player1Score =0;
        selectedBlocks = 0;
        player2Score = 0;
        txtPlayer1Score.setText(""+player1Score);
        txtPlayer2Score.setText(""+player2Score);
        blocks = Block.getBlocks(gridSize);
        //create new empty grid matrix
        trackBoard =  new int[gridSize][gridSize];
        //populate the matrix with -1 values
        for (int i = 0; i < trackBoard.length; i++) {
            for (int j = 0; j < trackBoard[0].length; j++) {
                trackBoard[i][j] = -1;
            }
        }
        //Remove any Strokes are added to the view
        while(rlView.getChildCount()>1){
                rlView.removeViewAt(rlView.getChildCount()-1);
        }

        BlockAdapter blockAdapter = new BlockAdapter(getApplicationContext(),"");
        gvBoard.setAdapter(blockAdapter);
        btnPlayer1.setBackgroundColor(getResources().getColor(R.color.blue));
        btnPlayer2.setBackgroundColor(getResources().getColor(R.color.none_active));
    }

    private void linkAction(){
        btnO.setOnClickListener(view -> {

            btnO.setBackgroundColor(getResources().getColor(R.color.active));
            btnS.setBackgroundColor(getResources().getColor(R.color.none_active));
            selectedString = "O";
            Toast.makeText(SosGame.this,"O has been selected", Toast.LENGTH_SHORT).show();
        });
        btnS.setOnClickListener(View->{
            selectedString ="S";
            btnS.setBackgroundColor(getResources().getColor(R.color.active));
            btnO.setBackgroundColor(getResources().getColor(R.color.none_active));
            Toast.makeText(SosGame.this,"S has been selected", Toast.LENGTH_SHORT).show();
        });
        gvBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView tvBlock =  (TextView)((ViewGroup)view).getChildAt(0);
                if(tvBlock.getText().equals("")){
                    selectedBlocks++;
                    tvBlock.setText(selectedString);
                    if(player1_turn)
                        tvBlock.setTextColor(Color.BLUE);
                    else
                        tvBlock.setTextColor(Color.RED);
                    if(selectedString.equals("O"))
                        addToBoard(position,0,adapterView);
                    else
                        addToBoard(position,5, adapterView);
                    if(selectedBlocks == blocks.size()){
                        if(player1Score>player2Score){
                            dialog("Player 1 Wins!!\nWould you like to restart?",1);
                        }else if(player2Score>player1Score){
                           dialog("Player 2 Wins!!\nWould you like to restart?",1);
                        }else{
                            dialog("DRAW!!\nWould you like to restart?", 1);
                        }
                    }
                }
            }
        });
        btnRestart.setOnClickListener(view -> {dialog("Are you sure you want to restart?",1);});
        btnQuit.setOnClickListener(view -> {dialog("The game scores and data will be lost\n Are you sure you want to quit?",0); });
    }

    private void addPoint(){
        if(player1_turn)
        {
            player1Score++;
            txtPlayer1Score.setText(""+player1Score);
        }else{
            player2Score++;
            txtPlayer2Score.setText(""+player2Score);
        }

    }

    /**
     * <p>Adds the insert letter into the n x n matrix in the form of a number</p>
     *
     * @param position - The position of the view in the grid
     * @param letter - The letter selected by the player
     * @param parent - The gridView containing the items
     */
    private void addToBoard(int position, int letter, AdapterView<?> parent){
        int count = 0;
        for (int i = 0; i < trackBoard.length; i++) {
            for (int j = 0; j < trackBoard[0].length; j++) {
                if(count == position){
                    trackBoard[i][j] = letter;
                    check(new int[]{i,j}, letter, parent);//Checks if whether any SoS patterns can be formed..
                    return;
                }
                count++;
            }
        }
    }

    /**
     * <p>Locates the position of the TextView in the gridView</p>
     * @param arrayPosition - The position of the letter in the matrix
     * @return the position of the TextView in the gridView
     */
    private int getBlockPosition(int [] arrayPosition){
        int count = 0;
        for (int i = 0; i < trackBoard.length; i++) {
            for (int j = 0; j < trackBoard[0].length; j++) {
                if(arrayPosition[0] == i && arrayPosition[1] ==j){
                    return count;
                }
                count++;
            }
        }
        return -1;
    }

    /**
     * <p>This method checks the matrix if there are any SOS patterns that can be formed from the currently inserted value</p>
     * @param position - The position of the recently inserted letter in the matrix
     * @param letter - The letter that was inserted in the form of an integer value
     * @param parent - The gridView to be able to keep track of the items in the view
     */
    private void check(int[] position, int letter, AdapterView<?> parent) {
        boolean addedPoint = false;
        //Checks for SOS patterns when the inserted letter was an S
        if(letter == 5){
            //Check if the top two rows are available from the position of the inserted letter.
            if(position[0]-2>=0){
                //check top right diagonal
                if(position[1]+2<trackBoard.length) {
                    if(trackBoard[position[0]-2][position[1]+2] ==5 && trackBoard[position[0]-1][position[1]+1]==0)
                    {
                        addPoint();
                        addedPoint = true;
                        addStroke(position, new int[]{position[0]-2,position[1]+2}, parent);
                    }
                }
                //Check top left diagonal
                if(position[1]-2>=0){
                    if(trackBoard[position[0]-2][position[1]-2] ==5 && trackBoard[position[0]-1][position[1]-1]==0)
                    {
                        addPoint();
                        addedPoint = true;
                        addStroke(position, new int[]{position[0]-2,position[1]-2}, parent);
                    }
                }
                //Check top horizontal
                if(trackBoard[position[0]-1][position[1]] == 0 && trackBoard[position[0]-2][position[1]] ==5)
                {
                        addPoint();
                        addedPoint = true;
                    addStroke(position, new int[]{position[0]-2,position[1]}, parent);
                }

            }
            //Check if the bottom two rows are available from the position of the inserted letter.
            if(position[0]+2<trackBoard.length){
                //check bottom right diagonal
                if(position[1]+2<trackBoard.length) {
                    if(trackBoard[position[0]+2][position[1]+2] ==5 && trackBoard[position[0]+1][position[1]+1]==0)
                    {
                        addPoint();
                        addedPoint = true;
                        addStroke(position, new int[]{position[0]+2,position[1]+2}, parent);
                    }
                }
                //Check bottom left diagonal
                if(position[1]-2>=0){
                    if(trackBoard[position[0]+2][position[1]-2] == 5 && trackBoard[position[0]+1][position[1]-1]==0)
                    {
                        addPoint();
                        addedPoint = true;
                        addStroke(position, new int[]{position[0]+2,position[1]-2}, parent);
                    }
                }
                //Check bottom horizontal
                if(trackBoard[position[0]+1][position[1]] == 0 && trackBoard[position[0]+2][position[1]] ==5)
                {
                    addPoint();
                    addedPoint = true;
                    addStroke(position, new int[]{position[0]+2,position[1]}, parent);
                }
            }
            //Check if there left two columns are available from the position of the inserted letter.
            if(position[1]-2>=0){
                if(trackBoard[position[0]][position[1]-2] ==5 && trackBoard[position[0]][position[1]-1]==0)
                {
                    addPoint();
                    addedPoint = true;
                    addStroke(position, new int[]{position[0],position[1]-2}, parent);
                }
            }
            //Check if there right two columns are available from the position of the inserted letter.
            if(position[1]+2<trackBoard.length){
                if(trackBoard[position[0]][position[1]+2] ==5 && trackBoard[position[0]][position[1]+1]==0)
                {
                    addPoint();
                    addedPoint = true;
                    addStroke(position, new int[]{position[0],position[1]+2}, parent);
                }
            }
        }else{
            //Check if the bottom row and the top row are available
            if(position[0]-1>=0 && position[0]+1<trackBoard.length){
                if(trackBoard[position[0]-1][position[1]] == 5 && trackBoard[position[0]+1][position[1]] == 5){
                    addPoint();
                    addedPoint = true;
                    addStroke(new int[]{position[0]+1,position[1] }, new int[]{position[0]-1,position[1]}, parent);
                }
                if(position[1]+1<trackBoard.length && position[1]-1>=0){
                    if(trackBoard[position[0]-1][position[1]-1] == 5 && trackBoard[position[0]+1][position[1]+1] == 5){
                        addPoint();
                        addedPoint = true;
                        addStroke(new int[]{position[0]-1,position[1]-1 }, new int[]{position[0]+1,position[1]+1}, parent);
                    }
                    if(trackBoard[position[0]+1][position[1]-1] == 5 && trackBoard[position[0]-1][position[1]+1] == 5){
                        addPoint();
                        addedPoint = true;
                        addStroke(new int[]{position[0]+1,position[1]-1 }, new int[]{position[0]-1,position[1]+1}, parent);
                    }
                    if(trackBoard[position[0]][position[1]-1] == 5 && trackBoard[position[0]][position[1]+1] == 5){
                        addPoint();
                        addedPoint = true;
                        addStroke(new int[]{position[0],position[1]-1 }, new int[]{position[0],position[1]+1}, parent);
                    }
                }
            }
        }
        //Switch players
        if(!addedPoint)
            player1_turn = !player1_turn;
        if(player1_turn)
        {
            Toast.makeText(this,"Player 1's turn", Toast.LENGTH_SHORT).show();
            btnPlayer1.setBackgroundColor(getResources().getColor(R.color.blue));
            btnPlayer2.setBackgroundColor(getResources().getColor(R.color.none_active));
        }else{
            Toast.makeText(this,"Player 2's turn", Toast.LENGTH_SHORT).show();
            btnPlayer2.setBackgroundColor(getResources().getColor(R.color.tomato));
            btnPlayer1.setBackgroundColor(getResources().getColor(R.color.none_active));
        }
    }

    /**
     * <p>Adds a stroke when an SOS pattern is located</p>
     * @param start - the position of the fist S on the matrix
     * @param end -  the position of the second s on the matrix
     * @param parent - GridView
     */
    private void addStroke(int[] start, int[] end, AdapterView<?> parent){
        //Get position of the TextViews Items from the grid view
        int block1Pos = getBlockPosition(start);
        int block2Pos = getBlockPosition(end);

        TextView block1 = (TextView) ((ViewGroup) parent.getChildAt(block1Pos)).getChildAt(0);
        TextView block2 = (TextView) ((ViewGroup) parent.getChildAt(block2Pos)).getChildAt(0);

        //get position of the TextViews on the Screen
        int [] locationBlock1 = new int[2];
        int [] locationBlock2 = new int[2];
        block1.getLocationInWindow(locationBlock1);
        block2.getLocationInWindow(locationBlock2);

        int color;
        if(player1_turn)
            color = Color.BLUE;
        else
            color = Color.RED;
        rlView.addView(new DrawLine(this, new Point(locationBlock1[0], locationBlock1[1]-370), new Point(locationBlock2[0], locationBlock2[1]-370),color));
    }

}