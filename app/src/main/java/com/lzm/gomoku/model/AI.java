package com.lzm.gomoku.model;

import com.lzm.gomoku.common.Constants;

/**
 * Created by lzm on 2017/10/6.
 */

public abstract class AI {
    enum DIRECTION {
        HORIZONTAL,
        VERTICAL,
        LEFT_TILT,
        RIGHT_TILT
    }

    protected ChessPoint mBestPoint = new ChessPoint();
    protected int mAIRole = Constants.CHESS_BLACK;
    protected int mPlayerRole = Constants.CHESS_WHITE;

    protected int[][] mAIBoard = new int[Constants.LINE_COUNT][Constants.LINE_COUNT];

    public AI(){}

    public AI(int aiRole, int playerRole){
        mAIRole = aiRole;
        mPlayerRole = playerRole;
    }

    public abstract ChessPoint calculateBest(ChessPoint currentPoint);

    public void resetBoard(){
        mAIBoard = new int[Constants.LINE_COUNT][Constants.LINE_COUNT];
    }

    protected int evaluate (int x, int y){
        int score_horizontal = getLineScore(x, y, DIRECTION.HORIZONTAL);
        int score_vertical = getLineScore(x, y, DIRECTION.VERTICAL);
        int score_left_tilt = getLineScore(x, y, DIRECTION.LEFT_TILT);
        int score_right_tilt = getLineScore(x, y, DIRECTION.RIGHT_TILT);

        return score_horizontal + score_vertical + score_left_tilt + score_right_tilt;
    }

    protected int getLineScore(int x, int y, DIRECTION direction) {
        int size = 5;

        int count = 1; //连子数
        int empty = 0; //空位数
        int block = 0; //封闭数

        int currentRole = mAIBoard[x][y];
        int tempX = x+1;
        int tempY = y+1;
        switch (direction) {
            case HORIZONTAL:
                //RIGHT_HORIZONTAL
                while(tempY<y+size){
                    if(tempY>=Constants.LINE_COUNT){
                        block++;
                        break;
                    }
                    if(mAIBoard[x][tempY] == currentRole){
                        count++;
                    } else if(count<4 && tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[x][tempY] == Constants.CHESS_NONE ){
                        if(mAIBoard[x][tempY+1] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[x][tempY+1] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempY++;
                }
                //LEFT_HORIZONTAL
                tempY = y-1;
                while(tempY>y-size){
                    if(tempY<0){
                        block++;
                        break;
                    }
                    if(mAIBoard[x][tempY] == currentRole){
                        count++;
                    } else if(count<4&& tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[x][tempY] == Constants.CHESS_NONE){
                        if(mAIBoard[x][tempY-1] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[x][tempY-1] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempY--;
                }
                break;
            case VERTICAL:
                //DOWN_VERTICAL
                while(tempX<x+size){
                    if(tempX>=Constants.LINE_COUNT){
                        block++;
                        break;
                    }
                    if(mAIBoard[tempX][y] == currentRole){
                        count++;
                    } else if(count<4 && tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[tempX][y] == Constants.CHESS_NONE){
                        if(mAIBoard[tempX+1][y] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[tempX+1][y] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempX++;
                }
                //UP_VERTICAL
                tempX = x-1;
                while(tempX>x-size){
                    if(tempX<0){
                        block++;
                        break;
                    }
                    if(mAIBoard[tempX][y] == currentRole){
                        count++;
                    } else if(count<4 && tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[tempX][y] == Constants.CHESS_NONE){
                        if(mAIBoard[tempX-1][y] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[tempX-1][y] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempX--;
                }
                break;
            case LEFT_TILT:
                //DOWN_LEFT_TILT
                tempX = x+1;
                tempY = y+1;
                while(tempX<x+size){
                    if(tempX>=Constants.LINE_COUNT || tempY >= Constants.LINE_COUNT){
                        block++;
                        break;
                    }
                    if(mAIBoard[tempX][tempY] == currentRole){
                        count++;
                    } else if(count<4 && tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[tempX][tempY] == Constants.CHESS_NONE){
                        if(mAIBoard[tempX+1][tempY+1] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[tempX+1][tempY+1] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempX++;
                    tempY++;
                }
                //UP_LEFT_TILT
                tempX = x-1;
                tempY = y-1;
                while(tempX>x-size){
                    if(tempX<0||tempY<0){
                        block++;
                        break;
                    }
                    if(mAIBoard[tempX][tempY] == currentRole){
                        count++;
                    } else if(count<4 && tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[tempX][tempY] == Constants.CHESS_NONE){
                        if(mAIBoard[tempX-1][tempY-1] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[tempX-1][tempY-1] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempX--;
                    tempY--;
                }
                break;
            case RIGHT_TILT:
                //DOWN_RIGHT_TILT
                tempX = x+1;
                tempY = y-1;
                while(tempX<x+size){
                    if(tempX>=Constants.LINE_COUNT || tempY<0){
                        block++;
                        break;
                    }
                    if(mAIBoard[tempX][tempY] == currentRole){
                        count++;
                    } else if(count<4 && tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[tempX][tempY] == Constants.CHESS_NONE){
                        if(mAIBoard[tempX+1][tempY-1] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[tempX+1][tempY-1] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempX++;
                    tempY--;
                }
                //UP_RIGHT_TILT
                tempX = x-1;
                tempY = y+1;
                while(tempX>x-size){
                    if(tempX<0 || tempY >= Constants.LINE_COUNT){
                        block++;
                        break;
                    }
                    if(mAIBoard[tempX][tempY] == currentRole){
                        count++;
                    } else if(count<4 && tempX>0 && tempX<Constants.LINE_COUNT-1 && tempY>0 && tempY<Constants.LINE_COUNT-1
                            && mAIBoard[tempX][tempY] == Constants.CHESS_NONE){
                        if(mAIBoard[tempX-1][tempY+1] == currentRole) {
                            empty++;
                            continue;
                        } else if(mAIBoard[tempX-1][tempY+1] == Constants.CHESS_NONE){
                            break;
                        } else {
                            block++;
                            break;
                        }
                    } else {
                        block++;
                        break;
                    }
                    tempX--;
                    tempY++;
                }
                break;
        }

        return getScore(count, empty, block);
    }

    private int getScore(int count, int empty, int block){
        if(empty==0){
            if(count>=5){
                return Score.FIVE;
            }
            if(block == 0){
                switch (count) {
                    case 1:
                        return Score.ONE;
                    case 2:
                        return Score.TWO;
                    case 3:
                        return Score.THREE;
                    case 4:
                        return Score.FOUR;
                }
            }

            if(block == 1){
                switch (count) {
                    case 1:
                        return Score.BLOCKED_ONE;
                    case 2:
                        return Score.BLOCKED_TWO;
                    case 3:
                        return Score.BLOCKED_THREE;
                    case 4:
                        return Score.BLOCKED_FOUR;
                }
            }
        } else {
            //中间有一个空位，这种情况下只考虑二，三，四
            //有一个空位的四连其实和三连是一样的分数
            if(block == 0) {
                switch(count) {
                    case 2: return Score.TWO;
                    case 3:
                    case 4: return Score.THREE;
                }
            }
            if(block == 1) {
                switch(count) {
                    case 2: return Score.BLOCKED_TWO;
                    case 3:
                    case 4: return Score.BLOCKED_THREE;
                }
            }
        }

        return Score.BLOCKED;
    }
}
