package cloudcode.v2ourbook.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Configuration
public class FirebaseConfig {

//    private static final String GOOGLE_APPLICATION_CREDENTIALS = "/home/yannx/IdeaProjects/v2ourbook/src/main/resources/firebase_config.json";
    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public FirebaseAuth firebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @PostConstruct
    public void initializeFirebaseApp() throws IOException {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource("firebase_config.json").getInputStream());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(googleCredentials)
                .setDatabaseUrl("https://ourbook-357db-default-rtdb.europe-west1.firebasedatabase.app")
//                .setServiceAccountId(GOOGLE_APPLICATION_CREDENTIALS)
                .setStorageBucket("ourbook-357db.appspot.com")
                .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
