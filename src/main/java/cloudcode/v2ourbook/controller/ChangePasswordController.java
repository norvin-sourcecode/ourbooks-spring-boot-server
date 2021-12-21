package cloudcode.v2ourbook.controller;

import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.User;
import cloudcode.v2ourbook.repositories.UserRepository;
import cloudcode.v2ourbook.services.UserFactoryService;
import cloudcode.v2ourbook.utils.ReturnError;
import cloudcode.v2ourbook.utils.ReturnSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class ChangePasswordController {



    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFactoryService userFactory;

    @Autowired
    UserController userController;


    @GetMapping("/changePasswordAdmin/{name}/{newPassword}")
    public Object changePasswordAdmin(@PathVariable(value="name") String name, @PathVariable(value="newPassword") String newPassword) throws ExceptionBlueprint {
        try {

            User user = userRepository.findUserByUsernameOrEmail(name,name).orElseThrow(()->new ExceptionBlueprint("usernotfound","no",1));

            userFactory.setPassword(user, newPassword);

            user.setPasswordResetToken(null);
            user.setPasswordResetTokenDate(null);

            userRepository.save(user);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return new ReturnError("changePasswordAdmin", e.getMessage(), null);
        }
    }

    @GetMapping("/changePassword/{token}/{newPassword}")
    public Object changePassword(@PathVariable(value="token") String token, @PathVariable(value="newPassword") String newPassword) throws ExceptionBlueprint {

        try {

            User user = userRepository.findUserByPasswordResetToken(token).orElseThrow(()->new ExceptionBlueprint("usernotfound","no",1));

            var tokenDate = user.getPasswordResetTokenDate();

            Calendar c = Calendar.getInstance();
            c.setTime(tokenDate);
            c.add(Calendar.HOUR,1);

            if (c.getTime().before(new Date())) {
                throw new Exception("token has expired.");
            }

            userFactory.setPassword(user, newPassword);
            
            user.setPasswordResetToken(null);
            user.setPasswordResetTokenDate(null);

            userRepository.save(user);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return new ReturnError("changePassword", e.getMessage(), null);
        }

    }
}
