package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.BookClub;
import cloudcode.v2ourbook.models.Licence;

import java.util.List;
import java.util.stream.Collectors;

public class LicenceDto {

    private boolean keep;

    List<Long> bookClubs;

    boolean returnToGiver;

    int weeksUsageTime;

    public LicenceDto() {
    }

    public LicenceDto(Licence licence) {
        this.keep = licence.isKeepLicence();
        this.bookClubs = licence.getBookClubs().stream().
                map(BookClub::getId).collect(Collectors.toList());
        this.returnToGiver = licence.isReturnToGiver();
        this.weeksUsageTime = licence.getWeeksUsageTime();
    }

    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public List<Long> getBookClubs() {
        return bookClubs;
    }

    public void setBookClubs(List<Long> bookClubs) {
        this.bookClubs = bookClubs;
    }

    public boolean isReturnToGiver() {
        return returnToGiver;
    }

    public void setReturnToGiver(boolean returnToGiver) {
        this.returnToGiver = returnToGiver;
    }

    public int getWeeksUsageTime() {
        return weeksUsageTime;
    }

    public void setWeeksUsageTime(int weeksUsageTime) {
        this.weeksUsageTime = weeksUsageTime;
    }
}
