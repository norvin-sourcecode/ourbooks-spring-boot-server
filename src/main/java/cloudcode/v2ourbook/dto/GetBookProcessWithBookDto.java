package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.GetBookProcess;
import java.util.Date;

public class GetBookProcessWithBookDto {

    Long id;
    Long bookReceiver;
    Long bookGiver;
    BookDto book;
    Date giveDate;
    Date returnDate;
    Integer status;

    public GetBookProcessWithBookDto(GetBookProcess p){
        this.setBook(new BookDto(p.getBook()));
        this.setBookReceiver(p.getBookReceiver().getId());
        this.setBookGiver(p.getBookGiver().getId());
        this.setId(p.getId());
        this.setReturnDate(p.getReturnDate());
        this.setStatus(p.getStatus().ordinal());
        this.setGiveDate(p.getGiveDate());
    }

    public GetBookProcessWithBookDto() {
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

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
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
