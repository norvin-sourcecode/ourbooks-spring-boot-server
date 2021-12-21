package cloudcode.v2ourbook.jobs;

import cloudcode.v2ourbook.models.TestMessage;
import cloudcode.v2ourbook.repositories.TestMessageRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TestMessageJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(TestMessageJob.class);

    @Autowired
    private TestMessageRepository testMessageRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        /* Get message id recorded by scheduler during scheduling */
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String messageId = dataMap.getString("messageId");

        log.info("Executing job for message id {}", messageId);

        /* Get message from database by id */
        int id = Integer.parseInt(messageId);
        Optional<TestMessage> messageOpt = testMessageRepository.findById(id);

        /* update message visible in database */
        TestMessage message = messageOpt.get();
        message.setVisible(true);
        testMessageRepository.save(message);

        /* unschedule or delete after job gets executed */

        try {
            context.getScheduler().deleteJob(new JobKey(messageId));

            TriggerKey triggerKey = new TriggerKey(messageId);

            context.getScheduler().unscheduleJob(triggerKey);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
