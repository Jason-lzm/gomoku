package com.lzm.gomoku.model;

import com.lzm.gomoku.common.Constants;

/**
 * Created by lzm on 2017/10/8.
 */

public class AIFactory {
    public static AI build(Constants.LEVEL level) {
        switch (level){
            case NORMAL:
                return new NormalAI();
            case HARD:
                return new NormalAI();
        }
        return new NormalAI();
    }
}
