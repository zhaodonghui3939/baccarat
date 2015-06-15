package com.game.baccarat.jersey;

import com.game.baccarat.jersey.helper.JSONHelper;
import com.game.baccarat.manager.BaccaratManager;
import com.game.baccarat.model.BaccaratInfo;
import com.game.baccarat.util.Status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("baccarat")
public class Baccarat {
    BaccaratManager manager = BaccaratManager.getInstance();

    /*初始化一局游戏，返回该游戏的id*/
    @GET
    @Path("init")
    @Produces(MediaType.APPLICATION_JSON)
    public String initialize(){
        return JSONHelper.getDefaultResponse(Status.OK,manager.initialize()).toString();
    }

    //消牌游戏
    @GET
    @Path("eliminate")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminate(
            @QueryParam("id") @DefaultValue("no-id") String id){
        int c = manager.cutting(id);
        if(c == -1) return JSONHelper.getDefaultResponse(Status.AUTH_ERROR,"").toString();
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

}
