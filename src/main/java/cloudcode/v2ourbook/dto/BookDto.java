package cloudcode.v2ourbook.dto;

import cloudcode.v2ourbook.models.Book;

import java.util.Date;

public class BookDto {

    Long id;
    String titel;
    String isbn;
    int status;
    String authorName;
    String erscheinungsDatum;
    String sprache;
    String auflage;
    String pictureUrl;
    String creatorName;
    Date timeCreated;
    String description;
    Double ratio;

    public BookDto() {
    }


    public BookDto(Book book){
        this.id = book.getId();
        this.titel = book.getTitel();
        this.isbn = book.getIsbn();
        this.status = book.getStatus().ordinal();
        this.authorName = book.getAuthorName();
        this.erscheinungsDatum = book.getErscheinungsDatum();
        this.sprache = book.getSprache();
        this.auflage = book.getAuflage();
        this.timeCreated = book.getTimeCreated();
        this.creatorName = book.getUser().getUsername();
        this.pictureUrl = book.getPictureUrl();
        this.description = book.getDescription();
        this.ratio = book.getRatio();
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getErscheinungsDatum() {
        return erscheinungsDatum;
    }

    public void setErscheinungsDatum(String erscheinungsDatum) {
        this.erscheinungsDatum = erscheinungsDatum;
    }

    public String getSprache() {
        return sprache;
    }

    public void setSprache(String sprache) {
        this.sprache = sprache;
    }

    public String getAuflage() {
        return auflage;
    }

    public void setAuflage(String auflage) {
        this.auflage = auflage;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        return "RestBook{" +
                "id=" + id +
                ", titel='" + titel + '\'' +
                ", isbn='" + isbn + '\'' +
                ", status=" + status +
                ", authorName='" + authorName + '\'' +
                ", erscheinungsDatum='" + erscheinungsDatum + '\'' +
                ", sprache='" + sprache + '\'' +
                ", auflage='" + auflage + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", timeCreated=" + timeCreated +
                ", description='" + description + '\'' +
                '}';
    }
}
