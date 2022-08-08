package spring.bappy.domain.Hangout;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import spring.bappy.domain.Place;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "hangout_info")
@Data
public class HangoutInfo {

    @Id
    private ObjectId hangoutInfoId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date hangoutMeetTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date hangoutRegTime;

    private String hangoutTitle;
    private String hangoutPlan;
    private String hangoutImageUrl;
    private String hangoutLanguage;
    private String hangoutOpenChat;

    private int hangoutTotalNum;
    private int hangoutCurrentNum;

    private int hangoutVisitCount;
    private int hangoutLikeCount;

    private ArrayList<String> hangoutUserList;
    private ArrayList<String> hangoutLikeList;
    private ArrayList<String> hangoutCategory;

    // ----- place 관련
    private Place hangoutPlace;

    public HangoutInfo() {
        hangoutTitle = "";
        hangoutPlan = "";
        hangoutImageUrl = "";
        hangoutLanguage = "";
        hangoutOpenChat = "";
        hangoutTotalNum = 0;
        hangoutCurrentNum = 0;
        hangoutVisitCount = 0;
        hangoutLikeCount = 0;
        hangoutUserList = new ArrayList<String>();
        hangoutLikeList = new ArrayList<String>();
        hangoutCategory = new ArrayList<String>();
    }




    public void addHangoutLikeList(String userId) {
        hangoutLikeList.add(userId);
        hangoutLikeCount++;

    }
    public void removeHangoutLikeList(String userId) {
        hangoutLikeList.remove(userId);
        hangoutLikeCount--;
    }
    public void addHangoutUserList(String userId) {
        hangoutUserList.add(userId);
        hangoutCurrentNum++;

    }
    public void removeHangoutUserList(String userId) {
        hangoutUserList.remove(userId);
        hangoutCurrentNum--;
    }


}
