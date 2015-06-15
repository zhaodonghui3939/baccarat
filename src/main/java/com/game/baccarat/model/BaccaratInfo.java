package com.game.baccarat.model;

import java.util.List;
public class BaccaratInfo {

    private List<Integer> banker; //庄家的牌

    private List<Integer> player; //闲家的牌

    private List<Integer> result; //游戏结果


    public String toString(){
       return  player.toString() + " " + banker.toString() + "\n" + result.toString();
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }

    public List<Integer> getBanker() {
        return banker;
    }

    public void setBanker(List<Integer> banker) {
        this.banker = banker;
    }

    public List<Integer> getPlayer() {
        return player;
    }

    public void setPlayer(List<Integer> player) {
        this.player = player;
    }


}
