package com.game.baccarat.util;

/**
 * Created by closure on 15/6/15.
 */
public class CardUtil {
    public static int transform(int num) {
        if(num >= 10) return 0;
        return num;
    }
}
