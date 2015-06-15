package com.game.baccarat.service;

import com.game.baccarat.model.BaccaratInfo;

import java.util.List;

public interface BaccaratService {

    public List<Integer> shuffle(); //洗牌

    public List<Integer> cutting(List<Integer> cards); //去除大概1.5副牌

    public int getFirstCard(List<Integer> cards); //获得第一张牌

    public List<Integer> eliminateCard(List<Integer> cards); //首次玩的时候消除牌

    public boolean playerOutsOrNot(List<Integer> player,List<Integer> banker); //闲家是否需要补牌

    public boolean bankerOutsOrNot(List<Integer> player,List<Integer> banker); //庄家是否需要补牌

    public boolean ifContinue(List<Integer> cards); //判断游戏能否继续

    public List<Integer> compareResult(List<Integer> player,List<Integer> banker); //比较闲家和庄家的牌面

    //public BaccaratInfo playBaccarat(List<Integer> cards); //根据当前的牌给出该局百家乐游戏的结果

}
