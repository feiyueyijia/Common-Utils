package com.yfny.utilscommon.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yfny.utilscommon.util.utilEntity.TokenUser;
import io.jsonwebtoken.impl.Base64UrlCodec;

/**
 * JWT获取token中的信息工具类
 *
 * <p>
 * Created  by  renxingWei  on  2019/5/15
 **/
public class JwtUtils {

    public static TokenUser getTokenUserByToken(String token) {
        TokenUser tokenUser = new TokenUser();
        if (token != null) {
            // 截取token的载荷
            token = token.substring(token.indexOf(".") + 1, token.lastIndexOf("."));
        }else {
            return tokenUser;
        }
        String tokenJson = Base64UrlCodec.BASE64URL.decodeToString(token);
        JSONObject jsonObject = JSON.parseObject(tokenJson);
        tokenUser.setUserId(jsonObject.getString("userId"));
        tokenUser.setUserAccount(jsonObject.getString("userAccount"));
        tokenUser.setNXsessionId(jsonObject.getString("NXsessionId"));
        tokenUser.setCurrentRoleId(jsonObject.getString("currentRoleId"));
        tokenUser.setCurrentRoleName(jsonObject.getString("currentRoleName"));
        tokenUser.setCurrentCorpId(jsonObject.getString("currentCorpId"));
        tokenUser.setCurrentCorpName(jsonObject.getString("currentCorpName"));
        tokenUser.setCurrentDeptId(jsonObject.getString("currentDeptId"));
        tokenUser.setCurrentDeptName(jsonObject.getString("currentDeptName"));
        tokenUser.setDefaultRoleId(jsonObject.getString("defaultRoleId"));
        tokenUser.setDescription(jsonObject.getString("description"));
        return tokenUser;
    }


}
