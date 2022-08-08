package spring.bappy.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {
    private int totalCount;
    private int currentCount;

    public PageInfo(int totalCount,int currentCount) {
        this.totalCount = totalCount;
        this.currentCount = currentCount;
    }
}

