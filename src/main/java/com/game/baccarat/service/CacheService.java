package com.game.baccarat.service;

import com.game.baccarat.model.BaccaratInfo;

import java.util.List;

public interface CacheService {

    public String initializeGame(); //初始化一场游戏

    public boolean exitGameId(String id); //是否存在这场游戏

    public List<Integer> getCardsByID(String id); //获取某个房间当前的牌面信息

    public List<BaccaratInfo> getPastResultById(String id); //获取某个房间过去的结果

    public List<BaccaratInfo> getPastNResultById(String id,int n); //获取某个房间过去n局游戏的结果

    public boolean emptyCardsByID(String id); //清空某个房间的牌面信息

    public boolean emptyResultById(String id); //清空某个房间过去的结果

    public boolean setCardsByID(String id, List<Integer> cards);//更新某个房间的牌面信息

    public boolean setResultById(String id, List<BaccaratInfo> results); //更新某个房间已经完成游戏的信息
}
