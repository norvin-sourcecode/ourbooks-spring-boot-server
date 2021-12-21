package cloudcode.v2ourbook.jobs;

import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.GetBookProcess;
import cloudcode.v2ourbook.models.TestMessage;
import cloudcode.v2ourbook.repositories.GetBookProcessRepository;
import cloudcode.v2ourbook.repositories.TestMessageRepository;
import cloudcode.v2ourbook.services.MessageService;
import lombok.SneakyThrows;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DueReminderJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(DueReminderJob.class);

    @Autowired
    private GetBookProcessRepository getBookProcessRepository;

    @Autowired
    private MessageService messageService;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        /* Get message id recorded by scheduler during scheduling */
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        Long processId = Long.parseLong(dataMap.getString("processId"));
        String uuid = dataMap.getString("uuid");

        log.info("Executing job with processId {}", processId);

        System.out.println("Executing job for processId {}" + processId);

        /* Get message from database by id */
        GetBookProcess process = getBookProcessRepository.findById(processId).get();

        messageService.createSystemMessage("Job was executed", processId);
        /* update message visible in database */
//        process.ifPresent(getBookProcess -> getBookProcessRepository.save(getBookProcess));
        /* unschedule or delete after job gets executed */

        try {
            context.getScheduler().deleteJob(new JobKey(uuid));

            TriggerKey triggerKey = new TriggerKey(uuid);

            context.getScheduler().unscheduleJob(triggerKey);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
