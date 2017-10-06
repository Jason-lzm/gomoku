package com.lzm.gomoku.common;

/**
 * Created by Linux on 2016/4/8.
 */
public class Constants {
    public static int CHESS_NONE = 0;
    public static int CHESS_WHITE = 1;
    public static int CHESS_BLACK = 2;

    // 五子连珠
    public final static int MAX_COUNT_IN_LINE = 5;
    // 棋盘的行数
    public final static int LINE_COUNT = 15;

    // 检查的方向
    public final static int HORIZONTAL = 0;
    public final static int VERTICAL = 1;
    public final static int LEFT_DIAGONAL = 2;
    public final static int RIGHT_DIAGONAL = 3;
}
