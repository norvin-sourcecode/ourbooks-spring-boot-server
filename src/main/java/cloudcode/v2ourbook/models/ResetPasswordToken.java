package cloudcode.v2ourbook.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    private String resetPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    private Boolean used;

    public ResetPasswordToken(User user, Date date) {
        setUser(user);
        setExpirationDate(date);
        setResetPasswordToken(UUID.randomUUID().toString());
        setUsed(false);
    }

    public ResetPasswordToken() {

    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTokenid() {
        return tokenid;
    }

    public void setTokenid(long tokenid) {
        this.tokenid = tokenid;
    }
}
