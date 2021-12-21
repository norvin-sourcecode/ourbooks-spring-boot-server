package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.dto.GetBookProcessWithBookDto;
import cloudcode.v2ourbook.dto.LicenceDto;
import cloudcode.v2ourbook.enums.BookStatus;
import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.*;
import cloudcode.v2ourbook.repositories.*;
import cloudcode.v2ourbook.services.LicenceService;
import cloudcode.v2ourbook.services.MessageService;
import cloudcode.v2ourbook.services.TimeService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static cloudcode.v2ourbook.enums.GetBookStatus.*;

@RestController
public class ProcessController {

    @Autowired
    GetBookProcessRepository getBookProcessRepository;

    @Autowired
    UserController userController;

    @Autowired
    GetBookRequestRepository getBookRequestRepository;

    @Autowired
    BookClubRepository bookClubRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LicenceService licenceService;

    @Autowired
    LicenceRepository licenceRepository;

    @Autowired
    TimeService timeService;

    @Autowired
    MessageService messageService;

    @GetMapping("/process/borrowed")
    public List<GetBookProcessWithBookDto> borrowingProcesses() {
        List<GetBookProcessWithBookDto> list = new java.util.ArrayList<>();
        getBookProcessRepository.findAllByBookReceiver(userController.getCurrentUser()).
                forEach(l -> list.add(new GetBookProcessWithBookDto(l)));
        return list;
    }

//    @GetMapping("/process/lend/filtered/{term}")
//    public List<RestGetBookProcess> borrowingProcessesFiltered(@PathVariable(value = "term") String term) {
//        return getBookProcessRepository.findAllByBookReceiver(userController.getCurrentUser()).stream().
//                filter(l -> l.getBook().getTitel().toLowerCase().contains(term.toLowerCase())).map(RestGetBookProcess::new).collect(Collectors.toList());
//    }

    @GetMapping("/process/{id}")
    public GetBookProcessWithBookDto getBookProcessById(@PathVariable(value = "id") Long id) throws ExceptionBlueprint{
        GetBookProcess process = getBookProcessRepository.findById(id).
                orElseThrow(() -> new ExceptionBlueprint("get book request not found","no",1));
        return new GetBookProcessWithBookDto(process);
    }

    @GetMapping("/process/lend")
    public List<GetBookProcessWithBookDto> lendingProcesses() {
        List<GetBookProcessWithBookDto> list = new java.util.ArrayList<>();
        getBookProcessRepository.findAllByBookGiver(userController.getCurrentUser()).forEach(l -> list.add(new GetBookProcessWithBookDto(l)));
        return list;
    }

    @GetMapping("/process/lend/filtered/{term}")
    public List<GetBookProcessWithBookDto> lendingProcessesFiltered(@PathVariable(value = "term") String term) {
        return getBookProcessRepository.findAllByBookGiver(userController.getCurrentUser()).stream().
                filter(l -> l.getBook().getTitel().toLowerCase().contains(term.toLowerCase())).map(GetBookProcessWithBookDto::new).collect(Collectors.toList());
    }

    @GetMapping("/process/dashStatLend")
    public Integer dashStatLend() {
        return getBookProcessRepository.countAllByBookGiver(userController.getCurrentUser());
    }

        @GetMapping("/process/dashStatBorrowed")
    public Integer dashStatBorrowed() {
        return getBookProcessRepository.countAllByBookReceiver(userController.getCurrentUser());
    }

    @PostMapping("/process/agree/{id}")
    public GetBookProcessWithBookDto agreeGetBookProcess(@RequestBody LicenceDto licenceDto,@PathVariable(value = "id") Long processId) throws ExceptionBlueprint, ExecutionException, InterruptedException {
        GetBookProcess process = getBookProcessRepository.findById(processId).
                orElseThrow(() -> new ExceptionBlueprint("getBook Process not found not found","no",1));
        Licence licence = process.getBook().getLicence();
        if (!licence.isKeepLicence()){
            process.getBook().setLicence(licenceService.update(licence, licenceDto));
        }
        process.setStatus(AGREED);
        messageService.createSystemMessage("Vergiss nicht, bei der Übergabe QR Code (Button oben rechts) zu scannen!", process.getId());
        System.out.println(processId + " agreed");
        getBookProcessRepository.save(process);
        return new GetBookProcessWithBookDto(process);
    }


    @GetMapping("/process/transferOwnership/{id}") // TODO write method
    public GetBookProcessWithBookDto transferOwnership() throws ExceptionBlueprint {
        return new GetBookProcessWithBookDto();
    }


    @GetMapping("/process/delivered/{id}")
    public GetBookProcessWithBookDto deliveredGetBookProcess(@PathVariable(value = "id") Long processId) throws ExceptionBlueprint, ExecutionException, InterruptedException {
        User currentUser = userController.getCurrentUser();
        GetBookProcess process = getBookProcessRepository.findById(processId).
                orElseThrow(() -> new ExceptionBlueprint("getBook Process not found not found","no",1));
        if(!(process.getBookGiver().equals(currentUser) || process.getBookReceiver().equals(currentUser))){
            throw new ExceptionBlueprint("not authorized","no",1);
        }
        process.setReturnDate(timeService.calculateDateInNWeeks(
                process.getBook().getLicence().getWeeksUsageTime()));
        process.setGiveDate(new Date());
        process.setStatus(RECEIVED);
        messageService.createSystemMessage("Die Frist von " + process.getBook().getLicence().getWeeksUsageTime() * 7 + " Tagen beginnt nun!", process.getId());
        getBookProcessRepository.save(process);
        return new GetBookProcessWithBookDto(process);
    }

    @GetMapping("/process/returned/{id}")
    public GetBookProcessWithBookDto returnedGetBookProcess(@PathVariable(value = "id") Long processId) throws ExceptionBlueprint, FirebaseAuthException, ExecutionException, InterruptedException {
        User currentUser = userController.getCurrentUser();
        GetBookProcess process = getBookProcessRepository.findById(processId).
                orElseThrow(() -> new ExceptionBlueprint("getBook Process not found not found","no",1));
        if(!(process.getBookGiver().equals(currentUser) || process.getBookReceiver().equals(currentUser))){
            throw new ExceptionBlueprint("not authorized","no",1);
        }
        process.setBookReturnedDate(new Date());
        process.setStatus(RETURNED);
        process.getBook().setStatus(BookStatus.AVAILABLE);
        messageService.createSystemMessage("Der Verleihprozess ist abgeschlossen, dieser Chat wird in 3 Tagen archiviert!", process.getId());
        getBookProcessRepository.save(process);
        return new GetBookProcessWithBookDto(process);
    }

}
