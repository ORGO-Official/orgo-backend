package orgo.backend.domain._1auth.domain;

public class KakaoTokenRequirement extends SocialTokenRequirement {
    public String getCode(){
        return super.code;
    }
    public String getRedirectUri(){
        return super.redirectUri;
    }
}
