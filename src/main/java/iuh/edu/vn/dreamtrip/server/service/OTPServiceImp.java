package iuh.edu.vn.dreamtrip.server.service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import iuh.edu.vn.dreamtrip.server.entity.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class OTPServiceImp implements OTPService {

    @Autowired
    JavaMailSender mailSender;

    Firestore dbFireStore = FirestoreClient.getFirestore();
    CollectionReference collectionReference = dbFireStore.collection("otp");

    @Override
    public String create(String email) throws ExecutionException, InterruptedException {
        if (collectionReference.document(email).get().get().toObject(OTP.class) != null)
            collectionReference.document(email).delete();
        String otp = new DecimalFormat("0000").format(new Random().nextInt(9999));
        OTP o = new OTP(email, otp);
        collectionReference.document(email).set(o);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here, and if you have to refresh UI put this code:
                        collectionReference.document(email).delete();
                    }
                },
                5000 * 60
        );
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dreamtripotpsup@gmail.com");
        message.setTo(email);
        message.setSubject("Mã xác thực");
        message.setText("Mã xác thực của bạn là: "+otp+". Mã này chỉ tồn tại trong vòng 5 phút,");

        mailSender.send(message);
        return "Ok";
    }

    @Override
    public OTP getOTP(String email) throws ExecutionException, InterruptedException {
        return collectionReference.document(email).get().get().toObject(OTP.class);
    }
}
