package spring.bappy.domain.Hangout;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hangout_detail")
@Data
public class HangoutDetail {

    @Id
    private ObjectId hangoutDetailId;

    private ObjectId hangoutInfoId;

    private String hangoutPlan;
    private String hangoutOpenchat;



}
