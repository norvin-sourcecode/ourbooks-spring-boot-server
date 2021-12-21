package cloudcode.v2ourbook.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Licence {

    @Id
    @GeneratedValue
    private Long id;

    private boolean keepLicence;

    int weeksUsageTime;

    boolean returnToGiver;

    @OneToMany(cascade= CascadeType.PERSIST)
    List<BookClub> bookClubs;

    public Licence() {
        this.keepLicence = false;
    }

//    public Licence(RestLicence restLicence) {
//        this.keep = restLicence.isKeep();
//        restLicence.bookClubs.stream().map(b -> );
//        this.bookClubs = ;
//        this.returnToGiver = restLicence.returnToGiver;
//        this.weeksUsageTime = restLicence.getWeeksUsageTime();
//    }

    public List<BookClub> getBookClubs() {
        return bookClubs;
    }

    public void setBookClubs(List<BookClub> bookClubs) {
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

    public boolean isKeepLicence() {
        return keepLicence;
    }

    public void setKeepLicence(boolean keep) {
        this.keepLicence = keep;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
