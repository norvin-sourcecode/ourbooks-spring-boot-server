package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.models.ConfirmationToken;
import cloudcode.v2ourbook.models.ResetPasswordToken;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.ConfirmationTokenRepository;
import cloudcode.v2ourbook.repositories.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    private ConfirmationTokenRepository confirmationTokenRepository;

    private UserService userService;

    private TimeService timeService;

    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, ConfirmationTokenRepository confirmationTokenRepository, UserService userService, TimeService timeService, ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userService = userService;
        this.timeService = timeService;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void sendConfirmationEmail(User user){
        ConfirmationToken confirmationToken = new ConfirmationToken(user, timeService.calculateDateInNMinutes(15));
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Ourbook Registration!");
        mailMessage.setFrom("ourbookfire@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"https://ourbooktest3-n52mpekfgq-ey.a.run.app/confirm-account?token="+confirmationToken.getConfirmationToken());
        sendEmail(mailMessage);
    }

    public void sendPasswordResetEmail(User user){
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user, timeService.calculateDateInNMinutes(15));
        resetPasswordTokenRepository.save(resetPasswordToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset Ourbook Password");
        mailMessage.setFrom("ourbookfire@gmail.com");
        mailMessage.setText("To reset your password click the link below : "
                +"https://ourbooktest3-n52mpekfgq-ey.a.run.app/resetPassword?token="+resetPasswordToken.getResetPasswordToken());
        sendEmail(mailMessage);
    }
}
