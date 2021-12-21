package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.configuration.QuartzConfig;
import cloudcode.v2ourbook.dto.TestMessageDto;
import cloudcode.v2ourbook.jobs.TestMessageJob;
import cloudcode.v2ourbook.models.TestMessage;
import cloudcode.v2ourbook.repositories.TestMessageRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping(path = "/messages")
public class MessageSchedulingController {

    @Autowired
    private QuartzConfig quartzConfig;
    @Autowired
    private TestMessageRepository messageRepository;

    @PostMapping(path = "/schedule-visibility")
    public @ResponseBody
    TestMessageDto scheduleMessageVisibility(@RequestBody  TestMessageDto messageDto) {
        try {
            // save messages in table
            TestMessage message = new TestMessage();
            message.setContent(messageDto.getContent());
            message.setVisible(false);
            message.setMakeVisibleAt(messageDto.getMakeVisibleAt());

            message = messageRepository.save(message);

            // Creating JobDetail instance
            String id = String.valueOf(message.getId());
            JobDetail jobDetail = JobBuilder.newJob(TestMessageJob.class).withIdentity(id).build();

            // Adding JobDataMap to jobDetail
            jobDetail.getJobDataMap().put("messageId", id);

            // Scheduling time to run job
            Date triggerJobAt = new Date(message.getMakeVisibleAt());

            SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(id)
                    .startAt(triggerJobAt).withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withMisfireHandlingInstructionFireNow())
                    .build();
            // Getting scheduler instance
            Scheduler scheduler = quartzConfig.schedulerFactoryBean().getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

            messageDto.setStatus("SUCCESS");

        } catch (IOException | SchedulerException e) {
            // scheduling failed
            messageDto.setStatus("FAILED");
            e.printStackTrace();
        }
        return messageDto;
    }


    @DeleteMapping(path = "/{messageId}/unschedule-visibility")
    public @ResponseBody  TestMessageDto unscheduleMessageVisibility(
            @PathVariable(name = "messageId") Integer messageId) {

        TestMessageDto messageDto = new TestMessageDto();

        Optional<TestMessage> messageOpt = messageRepository.findById(messageId);
        if (!messageOpt.isPresent()) {
            messageDto.setStatus("Message Not Found");
            return messageDto;
        }

        TestMessage message = messageOpt.get();
        message.setVisible(false);
        messageRepository.save(message);

        String id = String.valueOf(message.getId());

        try {
            Scheduler scheduler = quartzConfig.schedulerFactoryBean().getScheduler();

            scheduler.deleteJob(new JobKey(id));
            TriggerKey triggerKey = new TriggerKey(id);
            scheduler.unscheduleJob(triggerKey);
            messageDto.setStatus("SUCCESS");

        } catch (IOException | SchedulerException e) {
            messageDto.setStatus("FAILED");
            e.printStackTrace();
        }
        return messageDto;
    }
}
