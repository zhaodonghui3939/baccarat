package com.game.baccarat.manager;

import com.game.baccarat.model.BaccaratInfo;
import com.game.baccarat.service.BaccaratService;
import com.game.baccarat.service.CacheService;
import com.game.baccarat.service.impl.BaccaratServiceImpl;
import com.game.baccarat.service.impl.CacheServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class BaccaratManager {
    private static BaccaratManager ourInstance = new BaccaratManager();
    private BaccaratService baccaratservice;
    private CacheService cacheService;

    public static BaccaratManager getInstance() {
        return ourInstance;
    }

    private BaccaratManager() {
        baccaratservice = BaccaratServiceImpl.getInstance();
        cacheService = CacheServiceImpl.getInstance();
    }

    //初始化游戏
    public String initialize(){
        String id = cacheService.initializeGame(); //初始化一个房间
        List<Integer> cards =  baccaratservice.shuffle(); //洗牌
        List<Integer> cardsCutting = baccaratservice.cutting(cards); //切牌
        cacheService.setCardsByID(id,cardsCutting);//将初始化的牌放进数据中
        List<BaccaratInfo> baccaratInfos = new ArrayList<>(); //初始化一个空得结果
        cacheService.setResultById(id,baccaratInfos);//将结果放入数据库中去
        return id;
    }

    //这一桌前面所有游戏结果
    public List<BaccaratInfo> getAllPastResults(String id){
        if(!cacheService.exitGameId(id)) return null; //如果不存在这场游戏，返回为空
        List<BaccaratInfo> baccaratInfos = cacheService.getPastResultById(id);
        return baccaratInfos;
    }

    //这一桌最近上局的结果
    public BaccaratInfo getLatestResult(String id){
        if(!cacheService.exitGameId(id)) return null; //如果不存在这场游戏，返回为空
        List<BaccaratInfo> baccaratInfos = cacheService.getPastResultById(id);
        if(baccaratInfos == null || baccaratInfos.size() == 0) return null;
        return baccaratInfos.get(baccaratInfos.size() - 1);
    }

    //消牌,返回牌面
    public int cutting(String id){
        List<Integer> cards = cacheService.getCardsByID(id);
        if(cards == null) return -1;
        int card = cards.get(0);
        List<Integer> cardsCutting = baccaratservice.cutting(cards); //根据牌面消牌
        cacheService.setCardsByID(id,cardsCutting); //数据库中更新牌面信息
        return card;
    }

    //对游戏过程进行调试
    public BaccaratInfo playDebug(List<Integer> cards){
        if(!baccaratservice.ifContinue(cards)) return null; //当前牌面无法支撑下一局
        List<Integer> player = new ArrayList<>(); //闲家牌面
        List<Integer> banker = new ArrayList<>(); //庄家牌面
        //顺序发牌
        player.add(cards.remove(0));
        banker.add(cards.remove(0));
        player.add(cards.remove(0));
        banker.add(cards.remove(0));

        if(baccaratservice.playerOutsOrNot(player,banker)){//闲家需要补牌
            player.add(cards.remove(0));
        }

        if(baccaratservice.bankerOutsOrNot(player,banker)){//庄家需要补牌
            banker.add(cards.remove(0));
        }

        List<Integer> result = baccaratservice.compareResult(player,banker);

        BaccaratInfo baccaratInfo = new BaccaratInfo();
        baccaratInfo.setPlayer(player);
        baccaratInfo.setBanker(banker);
        baccaratInfo.setResult(result);
        return baccaratInfo;

    }

    //开始游戏
    public BaccaratInfo play(String id){
        if(!cacheService.exitGameId(id)) return null; //如果不存在这场游戏，返回为空

        List<Integer> cards = cacheService.getCardsByID(id);//获取当前的牌面信息
        if(!baccaratservice.ifContinue(cards)) return null; //当前牌面无法支撑下一局

        List<Integer> player = new ArrayList<>(); //闲家牌面
        List<Integer> banker = new ArrayList<>(); //庄家牌面
        //顺序发牌
        player.add(cards.remove(0));
        banker.add(cards.remove(0));
        player.add(cards.remove(0));
        banker.add(cards.remove(0));

        if(baccaratservice.playerOutsOrNot(player,banker)){//闲家需要补牌
            player.add(cards.remove(0));
        }

        if(baccaratservice.bankerOutsOrNot(player,banker)){//庄家需要补牌
            banker.add(cards.remove(0));
        }

        List<Integer> result = baccaratservice.compareResult(player,banker);

        BaccaratInfo baccaratInfo = new BaccaratInfo();
        baccaratInfo.setPlayer(player);
        baccaratInfo.setBanker(banker);
        baccaratInfo.setResult(result);

        //更新数据库信息
        cacheService.setCardsByID(id,cards); //更新新的牌面信息
        List<BaccaratInfo> baccaratInfos = cacheService.getPastResultById(id);
        baccaratInfos.add(baccaratInfo);
        cacheService.setResultById(id,baccaratInfos); //将这局结果写入数据库中去

        return baccaratInfo;
    }


}
