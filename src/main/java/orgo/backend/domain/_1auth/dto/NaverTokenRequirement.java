package orgo.backend.domain._1auth.dto;

public class NaverTokenRequirement extends SocialTokenRequirement {
    public String getCode(){
        return super.code;
    }
    public String getState(){
        return super.state;
    }
}