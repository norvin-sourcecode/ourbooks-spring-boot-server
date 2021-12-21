package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.error.ExceptionBlueprint;
import cloudcode.v2ourbook.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserFactoryService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void setPassword(User user, String password) throws Exception {
        if (password == null || password.length() < 8) {
            throw new ExceptionBlueprint("badpassword","no",1);
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
    }
}
