package com.game.baccarat.service.impl;

import com.game.baccarat.model.BaccaratInfo;
import com.game.baccarat.service.CacheService;
import com.game.baccarat.util.HashUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CacheServiceImpl implements CacheService {

    private static Map<String,List<Integer>> gameCards = null; //当前的游戏以及剩余牌面
    private static Map<String,List<BaccaratInfo>> gameCompleted = null; //已经完成的游戏的结果

    private static CacheServiceImpl ourInstance = new CacheServiceImpl();

    public static CacheServiceImpl getInstance() {
        return ourInstance;
    }

    private CacheServiceImpl() {
        gameCards = new TreeMap<>();
        gameCompleted = new TreeMap<>();
    }

    @Override
    public String initializeGame(){
        String id = HashUtil.getRandomId(); //生成一个房间代码
        gameCards.put(id,null);
        gameCompleted.put(id,null);
        return id;
    }

    @Override
    public boolean exitGameId(String id){
        return gameCards.containsKey(id);
    }

    @Override
    public List<Integer> getCardsByID(String id){
        if(gameCards.containsKey(id)) return gameCards.get(id);
        return null;
    }

    @Override
    public List<BaccaratInfo> getPastResultById(String id){
        if(gameCompleted.containsKey(id)) return gameCompleted.get(id);
        return null;
    }
    @Override
    public boolean emptyCardsByID(String id){
        if(gameCards.containsKey(id)) {
            gameCards.remove(id);
            return true;
        }
        return false;

    }
    @Override
    public boolean emptyResultById(String id){
        if(gameCompleted.containsKey(id)){
            gameCompleted.remove(id);
            return true;
        }
        return false;

    }
    @Override
    public boolean setCardsByID(String id, List<Integer> cards){
        if(gameCards.containsKey(id)){
            gameCards.put(id,cards);
            return true;
        }
        return false;
    }
    @Override
    public boolean setResultById(String id, List<BaccaratInfo> results){
        if(gameCompleted.containsKey(id)){
            gameCompleted.put(id,results);
            return true;
        }
        return false;
    }
}
