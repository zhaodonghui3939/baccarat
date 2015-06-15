package com.game.baccarat.service.impl;

import com.game.baccarat.model.BaccaratInfo;
import com.game.baccarat.service.BaccaratService;
import com.game.baccarat.util.CardUtil;

import java.util.*;

public class BaccaratServiceImpl implements BaccaratService {
    private static BaccaratServiceImpl ourInstance = new BaccaratServiceImpl();

    public static BaccaratServiceImpl getInstance() {
        return ourInstance;
    }

    private BaccaratServiceImpl() {
    }

    @Override
    public List<Integer> shuffle(){
        List list = new ArrayList();
        for(int i = 1;i <= 32; ++i) { //8副牌放入list中去
            for (int j = 1; j <= 13; ++j) list.add(j);
            //for(int k = 10; k <= 13; ++k) list.add(0);
        }
        Collections.shuffle(list);
        return list;
    }

    @Override
    public boolean ifContinue(List<Integer> cards){
        if(cards.size() < 6) return false;
        return true;
    }

    @Override
    public List<Integer> cutting(List<Integer> cards){
        return cards.subList(0,338);
    }

    @Override
    public List<Integer> eliminateCard(List<Integer> cards){
        if(cards.size() < 4) return new ArrayList<Integer>();
        if(cards.size() - 1 - cards.get(0) < 4) return new ArrayList<Integer>();
        return cards.subList(1 + CardUtil.transform(cards.get(0)),cards.size());
    }

    @Override
    public int getFirstCard(List<Integer> cards){
        if(cards.size() == 0) return -1;
        return cards.get(0);
    }

    //闲家赢了为0  庄家赢为1  平局为2
    //无对子为0 有对子为1
    @Override
    public List<Integer> compareResult(List<Integer> player,List<Integer> banker){
        int sum1 = 0,sum2 = 0;
        for(int i = 0; i < player.size(); ++i) sum1 = sum1 + CardUtil.transform(player.get(i));
        for(int j = 0; j < banker.size(); ++j) sum2 = sum2 + CardUtil.transform(banker.get(j));
        sum1 = sum1 % 10;
        sum2 = sum2 % 10;
        int pair = 0;
        if(isPair(player) || isPair(banker)) pair = 1; //如果出现对子，则为1

        List<Integer> result = new ArrayList<>();
        if(sum1 > sum2){ //闲家赢了，判断是否对子
            result.add(0);
            result.add(pair);
        } else if(sum1 < sum2){ //庄家赢了
            result.add(1);
            result.add(pair);
        } else{ //打成平局
            result.add(2);
            result.add(pair);
        }
        return result;
    }

    private static boolean isPair(List<Integer> cards){
        List<Integer> nl = new ArrayList<>();
        for(int i = 0; i < cards.size(); ++i){
            if(CardUtil.transform(cards.get(i)) != 0) nl.add(cards.get(i)); //不等于0的加入新的
        }
        Set<Integer> s = new TreeSet<>();
        s.addAll(nl);
        if(s.size() != nl.size()) return true;
        return false;
    }

    @Override
    public boolean playerOutsOrNot(List<Integer> player,List<Integer> banker){
        if((CardUtil.transform(player.get(0)) + CardUtil.transform(player.get(1))) % 10 >= 6) return false; //如果闲家牌点纸盒大于等于6，不补牌
        if((CardUtil.transform(banker.get(0)) + CardUtil.transform(banker.get(1))) % 10 >= 8) return false; //如果庄家为例牌，不补牌
        return true; //补牌
    }

    @Override
    public boolean bankerOutsOrNot(List<Integer> player,List<Integer> banker){
        int bankSum = CardUtil.transform(banker.get(0)) + CardUtil.transform(banker.get(1)) % 10; //庄家牌面大小
        if(bankSum >= 7) return false; //如果庄家两排合计7 8 9 不补牌

        if(player.size() == 3){//闲家补牌成功

            int playAdd = CardUtil.transform(player.get(2));//闲家所赠之牌

            if(bankSum == 3 && playAdd == 8) return false; //两牌合计3点，而闲家所增是8点

            if(bankSum == 4 &&
                    (playAdd == 1 || playAdd == 8 || playAdd == 9 || playAdd == 0))
                return false; //两牌合计4点，而闲家所增是1 8 9 0点之一

            if(bankSum == 5 &&
                    (playAdd == 1 || playAdd == 2 || playAdd == 3 || playAdd == 8 || playAdd == 9 || playAdd == 0))
                return false; //两牌合计5点，而闲家所增是1 2 3 8 9 0点之一

            if(bankSum == 6 &&
                    (playAdd == 1 || playAdd == 2 || playAdd == 3 || playAdd == 4 || playAdd == 5 || playAdd == 8 || playAdd == 9 || playAdd == 0))
                return false; //两牌合计6点，而闲家所增是1 2 3 4  5 8 9 0点之一

            if(bankSum == 1 || bankSum == 2 || bankSum == 10)
                return true; //如果庄家合计1 2 10，则增牌

            if(bankSum == 3 && playAdd != 8) return true; //如果庄3 闲家不等于8

            if(bankSum == 4 && (playAdd != 1 && playAdd != 8 && playAdd != 9 && playAdd != 0)) //庄4 闲家不等于0 1 8 9
                return true;

            if(bankSum == 5 && (playAdd == 4 || playAdd == 5 || playAdd == 6 || playAdd == 7)) //庄5 闲家等于4 5 6 7
                return true;

            if(bankSum == 6 && (playAdd == 6 || playAdd == 7)) //庄6 闲家等于6 7
                return true;
        }
        //闲家无增派
        if((CardUtil.transform(player.get(0)) + CardUtil.transform(player.get(1))) % 10 >= 8) return false;
        if(bankSum != 6 && bankSum != 7 && bankSum != 8 && bankSum != 9 && bankSum != 0 ) return true;
        if(bankSum == 6) return true;
        return true;
    }


    //测试
    public static void main(String[] args){
        BaccaratServiceImpl test =  BaccaratServiceImpl.getInstance();
        List list = test.shuffle();
        //list.add(1);
        System.out.println(list);
        List list2 = test.eliminateCard(list);
        System.out.println(list2);
        System.out.println(list2.remove(0));
       // BaccaratInfo info = test.playBaccarat(list2);
      //  System.out.println(info);
    }


}
