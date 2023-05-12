package iuh.edu.vn.dreamtrip.server.controller;

import iuh.edu.vn.dreamtrip.server.entity.OTP;
import iuh.edu.vn.dreamtrip.server.entity.TaiKhoan;
import iuh.edu.vn.dreamtrip.server.security.PasswordEncoder;
import iuh.edu.vn.dreamtrip.server.service.OTPServiceImp;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@RestController
@NoArgsConstructor
@RequestMapping("/otp")
public class OTPController {

    @Autowired
    OTPServiceImp otpService;

    @PostMapping("/create")
    public String createOTP(@RequestParam String email) throws InterruptedException, ExecutionException {
        return otpService.create(email);
    }

    @GetMapping("/get")
    public OTP getOTP(@RequestParam String email) throws InterruptedException, ExecutionException {
        return otpService.getOTP(email);
    }
}
