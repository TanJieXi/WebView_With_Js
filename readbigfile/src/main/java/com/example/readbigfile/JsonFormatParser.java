package com.example.readbigfile;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by TanJieXi on 2018/4/10.
 */

public class JsonFormatParser implements JsonDeserializer<Bean.UserinfoBean> {
    private Class mClass;

    public JsonFormatParser(Class aClass) {
        this.mClass = aClass;
    }

    @Override
    public Bean.UserinfoBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //根据Json元素获取Json对象
        JsonObject asJsonObject = json.getAsJsonObject();
        Bean.UserinfoBean bean = new Bean.UserinfoBean();
        //由于Json是以键值对的形式存在的，此处根据键获取对应的Json字符串
        String mJson = asJsonObject.get("hfx").toString();
        //判断是Object还是String
        JsonElement hfx = asJsonObject.get("hfx");
        if(hfx.isJsonObject() && !hfx.isJsonNull()){
            bean.setHfx(fromJsonObject(mJson,mClass));
        }else if(hfx.isJsonPrimitive() && !hfx.isJsonNull()){
            bean.setHfx(mJson);
        }else if(hfx.isJsonNull() || hfx.getAsString().isEmpty()){
            bean.setHfx(fromJsonObject(bean.toString(), mClass));
        }
        return bean;
    }

    /**
     * 用来解析对象
     */
    private <T> T fromJsonObject(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }
}
