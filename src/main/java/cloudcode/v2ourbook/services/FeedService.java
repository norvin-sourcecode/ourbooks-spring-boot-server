package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.enums.PostType;
import cloudcode.v2ourbook.models.Book;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FeedService {

    public String generateMessage(Book book, PostType postType){
        switch(postType){
            case NEWBOOK:
                return book.getTitel() + " ist nun verfügbar bei " + book.getUser().getUsername();
            case BOOKMARKED:
                return book.getUser().getUsername()+ " hat " + book.getTitel() + "zu seiner Leseliste hinzugefügt";
            case LIKED:
                return book.getUser().getUsername()+ " liked <3 " + book.getTitel();
            case FAVOURITE:
                return book.getUser().getUsername()+ " hat " + book.getTitel() + " zu seinen Lieblingsbüchern hinzugefügt";
            default:
                return "not a valid postType";
        }
    }

}
