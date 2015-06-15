package com.game.baccarat.jersey.helper;


import com.game.baccarat.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class JSONHelper {
    public static JSONObject getDefaultResponse( String status, Object obj ){

        JSONObject result = new JSONObject();

        result.put("status", status );
        result.put("time", DateUtil.getCurrentTimeString() );
        if(obj instanceof String){
            result.put( "data", obj);
        }else if( obj instanceof List){
            result.put( "data", JSONArray.fromObject(obj) );
        }else
            result.put( "data", JSONObject.fromObject(obj) );

        return result;

    }
}
