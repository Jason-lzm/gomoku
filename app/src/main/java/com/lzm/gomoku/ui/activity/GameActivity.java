package com.lzm.gomoku.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lzm.gomoku.common.Constants;
import com.lzm.gomoku.model.AI;
import com.lzm.gomoku.model.AIFactory;
import com.lzm.gomoku.model.ChessPoint;
import com.lzm.gomoku.ui.view.ChessBoardView;
import com.lzm.gomoku.R;

public class GameActivity extends AppCompatActivity implements ChessBoardView.PutChessListener {

    private ChessBoardView chessBoardView;
    private Constants.MODEL model;
    private AI computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        model = (Constants.MODEL) getIntent().getSerializableExtra("model");
        chessBoardView = (ChessBoardView) findViewById(R.id.boardView);
        chessBoardView.setPutChessListener(this);
        init();
    }

    private void init(){
        if(model == Constants.MODEL.SINGLE){
            computer = AIFactory.build(Constants.LEVEL.NORMAL);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 再来一局
        if (id == R.id.action_setting) {
            chessBoardView.reStart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPutChess(ChessPoint currentPoint) {
        new AITask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, currentPoint);
    }

    @Override
    public void onGameOver() {
        computer.resetBoard();
    }

    public class AITask extends AsyncTask<ChessPoint, Void, ChessPoint> {

        @Override
        protected ChessPoint doInBackground(ChessPoint... params) {
            return computer.calculateBest(params[0]);
        }

        @Override
        protected void onPostExecute(ChessPoint bestPoint) {
            //super.onPostExecute(bestPoint);
            chessBoardView.putChess(bestPoint.x, bestPoint.y);
        }
    }
}