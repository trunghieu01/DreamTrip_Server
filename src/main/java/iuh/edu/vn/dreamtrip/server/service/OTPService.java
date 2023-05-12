package iuh.edu.vn.dreamtrip.server.service;

import iuh.edu.vn.dreamtrip.server.entity.OTP;

import java.util.concurrent.ExecutionException;

public interface OTPService {
    public String create(String email) throws ExecutionException, InterruptedException;
    public OTP getOTP(String email) throws ExecutionException, InterruptedException;
}
