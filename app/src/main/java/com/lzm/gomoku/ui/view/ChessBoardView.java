package com.lzm.gomoku.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lzm.gomoku.common.Constants;
import com.lzm.gomoku.R;
import com.lzm.gomoku.model.ChessPoint;
import com.lzm.gomoku.utils.GameJudger;

/**
 * Created by lzm on 2017/9/28.
 */
public class ChessBoardView extends View {

    private static final int BOARD_SIZE = Constants.LINE_COUNT;
    // 棋盘的宽度，也是长度
    private int mViewWidth;
    // 棋盘每格的长度
    private float mGridWidth;
    private Paint paint = new Paint();
    // 定义黑白棋子的Bitmap
    private Bitmap mWhiteChessBitmap, mBlackChessBitmap;
    private float ratioPieceOfLineHeight = 0.9f;
    private int mPieceWidth;

    private PutChessListener mPutChessListener;

    // 判断当前落下的棋子是否是白色的
    private boolean mIsCurrentWhite = true;
    // 记录黑白棋子位置的列表
    private int[][] mBoard = new int[BOARD_SIZE][BOARD_SIZE];

    // 游戏是否结束
    private boolean mIsGameOver;

    private boolean mPlayerRound = true;

    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(0x88000000);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        mWhiteChessBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.white_piece);
        mBlackChessBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.black_piece);
    }

    public void setPutChessListener(PutChessListener listener) {
        mPutChessListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (widthModel == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightModel == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制棋盘的网格
        drawBoard(canvas);
        // 绘制棋盘的黑白棋子
        drawPieces(canvas);
    }

    // 检查游戏是否结束
    private void checkGameOver(int[][] board, int x, int y) {
        mIsGameOver = GameJudger.isGameEnd(board, x, y);

        if(mIsGameOver) {
            String content = mIsCurrentWhite ? "白棋胜利" : "黑棋胜利";
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("游戏结束").setMessage(content).setPositiveButton("重玩", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reStart();
                }
            }).setNegativeButton("取消", null).show();
            mPutChessListener.onGameOver();
        }
    }

    // 根据黑白棋子的数组绘制棋子
    private void drawPieces(Canvas canvas) {

        for (int row = 0; row < Constants.LINE_COUNT; row++) {
            for (int col = 0; col < Constants.LINE_COUNT; col++) {
                float x = (row + (1 - ratioPieceOfLineHeight) / 2) * mGridWidth;
                float y = (col + (1 - ratioPieceOfLineHeight) / 2) * mGridWidth;
                //RectF rectF = new RectF(x - mPieceWidth/2, y - mPieceWidth/2, x + mPieceWidth/2, y + mPieceWidth/2);
                if (mBoard[row][col] == Constants.CHESS_WHITE) {
                    canvas.drawBitmap(mWhiteChessBitmap, x, y, null);
                } else if (mBoard[row][col] == Constants.CHESS_BLACK) {
                    canvas.drawBitmap(mBlackChessBitmap, x, y, null);
                }
            }
        }
    }

    // 绘制棋盘的网线
    private void drawBoard(Canvas canvas) {
        int w = mViewWidth;
        float lineHeight = mGridWidth;

        for (int i = 0; i < Constants.LINE_COUNT; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, paint);
            canvas.drawLine(y, startX, y, endX, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mGridWidth = mViewWidth * 1.0f / Constants.LINE_COUNT;

        mPieceWidth = (int) (mGridWidth * ratioPieceOfLineHeight);
        mWhiteChessBitmap = Bitmap.createScaledBitmap(mWhiteChessBitmap, mPieceWidth, mPieceWidth, false);
        mBlackChessBitmap = Bitmap.createScaledBitmap(mBlackChessBitmap, mPieceWidth, mPieceWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int)(event.getX()/ mGridWidth);
            int y = (int)(event.getY()/ mGridWidth);

            putChess(x, y);
        }
        return true;
    }

    // 再来一局
    public void reStart() {
        mBoard = new int[BOARD_SIZE][BOARD_SIZE];
        mIsGameOver = false;
        invalidate();
    }

    public boolean putChess(int x, int y){
        if(mBoard[x][y] == Constants.CHESS_NONE){
            if(mIsCurrentWhite) {
                mBoard[x][y] = Constants.CHESS_WHITE;
            } else {
                mBoard[x][y] = Constants.CHESS_BLACK;
            }
            // 检查游戏是否结束
            checkGameOver(mBoard, x, y);
            if(mPlayerRound){
                ChessPoint currentPoint = new ChessPoint(x, y, mBoard[x][y], 0);
                mPutChessListener.onPutChess(currentPoint);
            }
            mIsCurrentWhite = !mIsCurrentWhite;
            mPlayerRound = !mPlayerRound;
            invalidate();
            return true;
        }
        return false;
    }

    public interface PutChessListener {
        void onPutChess(ChessPoint currentPoint);
        void onGameOver();
    }
}
