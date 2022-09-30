package zhongchiedu.oauth.util;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/****
 * @Description:用户令牌封装
 *****/
@Data
@ToString
public class AuthToken implements Serializable{

    //令牌信息
    String accessToken;
    //刷新token(refresh_token)
    String refreshToken;
    //jwt短令牌
    String jti;
    
    String error;
    String error_description;

}