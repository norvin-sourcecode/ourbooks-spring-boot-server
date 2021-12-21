package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.GetBookProcess;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

public class GetBookProcessDto {

    Long id;
    Long bookReceiver;
    Long bookGiver;
    Long book;
    Date giveDate;
    Date returnDate;
    Integer status;

    public GetBookProcessDto(GetBookProcess p){
        this.setBook(p.getBook().getId());
        this.setBookReceiver(p.getBookReceiver().getId());
        this.setBookGiver(p.getBookGiver().getId());
        this.setId(p.getId());
        this.setReturnDate(p.getReturnDate());
        this.setStatus(p.getStatus().ordinal());
        this.setGiveDate(p.getGiveDate());
    }

    public GetBookProcessDto() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookReceiver() {
        return bookReceiver;
    }

    public void setBookReceiver(Long bookReceiver) {
        this.bookReceiver = bookReceiver;
    }

    public Long getBookGiver() {
        return bookGiver;
    }

    public void setBookGiver(Long bookGiver) {
        this.bookGiver = bookGiver;
    }

    public Long getBook() {
        return book;
    }

    public void setBook(Long book) {
        this.book = book;
    }

    public Date getGiveDate() {
        return giveDate;
    }

    public void setGiveDate(Date giveDate) {
        this.giveDate = giveDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
