package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.GetBookRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GetBookRequestDto {

    Long id;

    String message;
//    @NotNull
//    //@NotBlank
//    Long receiverId;
//    @NotNull
//    //@NotBlank
//    Long giverId;
    @NotNull
    //@NotBlank
    Long bookId;
//
//    Long id;

    public GetBookRequestDto() {
        super();
    }

    public GetBookRequestDto(GetBookRequest getBookRequest) {
        this.message = getBookRequest.getMessage();
        this.bookId = getBookRequest.getBook().getId();
        this.id = getBookRequest.getId();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
