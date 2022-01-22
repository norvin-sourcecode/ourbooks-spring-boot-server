package cloudcode.v2ourbook.controller;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.GetBookProcess;
import cloudcode.v2ourbook.models.Message;
import cloudcode.v2ourbook.repositories.GetBookProcessRepository;
import cloudcode.v2ourbook.services.MessageService;
import cloudcode.v2ourbook.services.ReminderService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class MessageController {

    public MessageService messageService;

    ReminderService reminderService;

    GetBookProcessRepository getBookProcessRepository;

    public MessageController(MessageService messageService, ReminderService reminderService, GetBookProcessRepository getBookProcessRepository) {
        this.messageService = messageService;
        this.reminderService = reminderService;
        this.getBookProcessRepository = getBookProcessRepository;
    }

    @PostMapping("/messages/create")
    public String createMessage(String text, Long chatId) throws InterruptedException, ExecutionException, ExceptionBlueprint, IOException {
//        GetBookProcess getBookProcess = getBookProcessRepository.findById(chatId).
//                orElseThrow(() -> new ExceptionBlueprint("Process not found", "no", 1));
//        reminderService.scheduleDueReminderJob(getBookProcess);
        return messageService.createMessage(text, chatId);
    }



}
