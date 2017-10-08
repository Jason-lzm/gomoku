package com.lzm.gomoku.model;

import android.graphics.Point;

import com.lzm.gomoku.common.Constants;

/**
 * Created by lzm on 2017/10/6.
 */

public class ChessPoint extends Point {
    public int role = Constants.CHESS_NONE;
    public int score = 0;

    public ChessPoint(){
        super();
    }

    public ChessPoint(int x, int y, int role, int score){
        super(x, y);
        this.role = role;
        this.score = score;
    }

    public void setRole (int role) {
        this.role = role;
    }

    public void setScore(int score){
        this.score = score;
    }
}
