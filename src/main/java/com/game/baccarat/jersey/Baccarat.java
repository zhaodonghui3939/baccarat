package com.game.baccarat.jersey;

import com.game.baccarat.jersey.helper.JSONHelper;
import com.game.baccarat.manager.BaccaratManager;
import com.game.baccarat.model.BaccaratInfo;
import com.game.baccarat.service.BaccaratService;
import com.game.baccarat.service.impl.BaccaratServiceImpl;
import com.game.baccarat.util.Status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("baccarat")
public class Baccarat {
    BaccaratManager manager = BaccaratManager.getInstance();

    BaccaratService service = BaccaratServiceImpl.getInstance();

    /*初始化一局游戏，返回该游戏的id*/
    @GET
    @Path("init")
    @Produces(MediaType.APPLICATION_JSON)
    public String initialize(){
        return JSONHelper.getDefaultResponse(Status.OK,manager.initialize()).toString();
    }

    /*显示所有的房间*/
    @GET
    @Path("show_room")
    @Produces(MediaType.APPLICATION_JSON)
    public String show(){
        return JSONHelper.getDefaultResponse(Status.OK,manager.initialize()).toString();
    }

    //消牌游戏
    @GET
    @Path("eliminate")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminate(
            @QueryParam("id") @DefaultValue("no-id") String id){
        int c = manager.cutting(id);
        if(c == -1) reurn JSONHelper.getDefaultResponse(Status.AUTH_ERROR,"").toString();
        return JSONHelper.getDefaultResponse(Status.OK,String.valueOf(manager.cutting(id))).toString();
    }

    //正式开始一局游戏
    @GET
    @Path("play")
    @Produces(MediaType.APPLICATION_JSON)
    public String play(
            @QueryParam("id") @DefaultValue("no-id") String id){

        BaccaratInfo baccaratInfo = manager.play(id);
        if(baccaratInfo == null) return JSONHelper.getDefaultResponse(Status.AUTH_ERROR,null).toString();

        return JSONHelper.getDefaultResponse(Status.OK,baccaratInfo).toString();
    }

    //最近一场游戏的结果
    @GET
    @Path("latest_result")
    @Produces(MediaType.APPLICATION_JSON)
    public String latest(
            @QueryParam("id") @DefaultValue("no-id") String id){

        BaccaratInfo baccaratInfo = manager.getLatestResult(id);
        if(baccaratInfo == null) return JSONHelper.getDefaultResponse(Status.AUTH_ERROR,null).toString();

        return JSONHelper.getDefaultResponse(Status.OK,baccaratInfo).toString();
    }

    //过去所有的结果
    @GET
    @Path("all_result")
    @Produces(MediaType.APPLICATION_JSON)
    public String all(
            @QueryParam("id") @DefaultValue("no-id") String id){

       List<BaccaratInfo> baccaratInfos = manager.getAllPastResults(id);
        if(baccaratInfos == null) return JSONHelper.getDefaultResponse(Status.AUTH_ERROR,null).toString();
        return JSONHelper.getDefaultResponse(Status.OK,baccaratInfos).toString();
    }

    //调试游戏运行过程
    @GET
    @Path("debug")
    @Produces(MediaType.APPLICATION_JSON)
    public String debug(){
        StringBuffer buffer = new StringBuffer();
        List<Integer> cards = service.shuffle(); //洗盘
        buffer.append("shuffle: "+cards.toString()+"\n");
        List<Integer> cardsCutting = service.cutting(cards); //去除1.5倍牌
        buffer.append("eliminate cards 1.5: "+cardsCutting.toString()+"\n");
        List<Integer> cardsE = service.eliminateCard(cardsCutting); //消牌
        buffer.append("eliminate: "+cardsE.toString()+"\n");
        while (cardsE.size()>=6){
            buffer.append("current cards: "+cardsE.toString()+"\n");
            BaccaratInfo baccaratInfo = manager.playDebug(cardsE);

            buffer.append("current result: "+baccaratInfo.toString()+"\n");
        }

        return buffer.toString();

    }

}
