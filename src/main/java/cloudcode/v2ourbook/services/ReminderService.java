package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.configuration.QuartzConfig;
import cloudcode.v2ourbook.jobs.DueReminderJob;
import cloudcode.v2ourbook.jobs.TestMessageJob;
import cloudcode.v2ourbook.models.GetBookProcess;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class ReminderService {

    @Autowired
    private QuartzConfig quartzConfig;

    @Autowired
    TimeService timeService;


    public ReminderService() {

    }

    public void scheduleDueReminderJob(GetBookProcess getBookProcess) throws IOException {
        // Creating JobDetail instance
        String uuid = String.valueOf(UUID.randomUUID());
        JobDetail jobDetail = JobBuilder.newJob(DueReminderJob.class).withIdentity(uuid).build();

        // Adding JobDataMap to jobDetail
        jobDetail.getJobDataMap().put("processId", getBookProcess.getId());
        jobDetail.getJobDataMap().put("uuid", uuid);

        // Scheduling time to run job
//        Date triggerJobAt = getBookProcess.getReturnDate();
        Date triggerJobAt = timeService.calculateDateInNMinutes(1);

        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(uuid)
                .startAt(triggerJobAt).withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withMisfireHandlingInstructionFireNow())
                .build();
        // Getting scheduler instance
        Scheduler scheduler = quartzConfig.schedulerFactoryBean().getScheduler();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        }
         catch (SchedulerException e) {
            // scheduling failed
             System.out.println("job not scheduled!");
             e.printStackTrace();
        }
    }

}
