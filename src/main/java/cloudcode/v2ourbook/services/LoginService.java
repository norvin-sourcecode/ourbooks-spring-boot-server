package cloudcode.v2ourbook.services;

import cloudcode.v2ourbook.controller.UserController;
import cloudcode.v2ourbook.models.Authority;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginService {

    public LoginService() {
    }

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Autowired
    UserController userController;

    public String token(String username, Set<Authority> authorities) throws FirebaseAuthException {
        //joining elements of collections as comma seperated string
        String authorityCollection = authorities.stream().map(Authority::getName).sorted().collect(Collectors.joining(","));
        String customToken = firebaseAuth.createCustomToken(username, Collections.singletonMap("authorities", authorityCollection));

        String token = Collections.singletonMap("token", customToken).toString();
        token = token.replace("{token=", "");
        token = token.replace("}", "");

        return token;
    }
}
