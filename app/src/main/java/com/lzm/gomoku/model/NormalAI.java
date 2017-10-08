package com.lzm.gomoku.model;

import com.lzm.gomoku.common.Constants;
import com.lzm.gomoku.utils.GameJudger;

/**
 * Created by lzm on 2017/10/6.
 */

public class NormalAI extends AI {
    private int mDepth = 4;

    @Override
    public ChessPoint calculateBest(ChessPoint currentPoint) {
        mAIBoard[currentPoint.x][currentPoint.y] = mPlayerRole;
        ChessPoint bestPoint = max(currentPoint.x, currentPoint.y, mDepth);
        mAIBoard[bestPoint.x][bestPoint.y] = mAIRole;
        return bestPoint;
    }

    private ChessPoint max (int lastX, int lastY, int depth) {
        int score = evaluate(lastX, lastY);
        int maxScore = Integer.MIN_VALUE;
        ChessPoint maxPoint = new ChessPoint();
        if(depth <= 0 || GameJudger.isGameEnd(mAIBoard, lastX, lastY)){
            maxPoint.x = lastX;
            maxPoint.y = lastY;
            maxPoint.score = score;
            return maxPoint;
        }

        for(int x = 0; x < Constants.LINE_COUNT; x++){
            for (int y = 0; y < Constants.LINE_COUNT; y++){
                if(mAIBoard[x][y] == Constants.CHESS_NONE) {
                    mAIBoard[x][y] = mAIRole;
                    ChessPoint nextPoint = min(x, y, --depth);
                    if(-nextPoint.score>maxScore){
                        maxScore = -nextPoint.score;
                        maxPoint.x = x;
                        maxPoint.y = y;
                        maxPoint.score = -nextPoint.score;
                    }
                    mAIBoard[x][y] = Constants.CHESS_NONE;
                }
            }
        }
        return maxPoint;
    }

    private ChessPoint min (int lastX, int lastY, int depth) {
        int score = evaluate(lastX, lastY);
        int minScore = Integer.MAX_VALUE;
        ChessPoint minPoint = new ChessPoint();
        if(depth <= 0 || GameJudger.isGameEnd(mAIBoard, lastX, lastY)){
            minPoint.x = lastX;
            minPoint.y = lastY;
            minPoint.score = score;
            return minPoint;
        }
        for(int y = 0; y < Constants.LINE_COUNT; y++){
            for (int x = 0; x < Constants.LINE_COUNT; x++){
                if(mAIBoard[x][y] == Constants.CHESS_NONE){
                    mAIBoard[x][y] = mPlayerRole;
                    ChessPoint nextPoint = max(x, y, --depth);
                    if(nextPoint.score<minScore){
                        minScore = nextPoint.score;
                        minPoint.x = x;
                        minPoint.y = y;
                        minPoint.score = nextPoint.score;
                    }
                    mAIBoard[x][y] = Constants.CHESS_NONE;
                }
            }
        }
        return minPoint;
    }

}
