package com.signal.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

@Service
public class AuthTokenService {

    public static final String COOKIE_NAME = "SIGNAL_AUTH";

    private final String secret;

    public AuthTokenService(@Value("${app.auth.token-secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(String username) {
        String issuedAt = String.valueOf(Instant.now().toEpochMilli());
        String payload = username + ":" + issuedAt;
        String signature = sign(payload);
        return payload + ":" + signature;
    }

    public String extractUsername(String token) {
        String[] parts = token.split(":", 3);
        if (parts.length != 3) {
            return null;
        }
        String payload = parts[0] + ":" + parts[1];
        String signature = parts[2];
        if (!isValidSignature(payload, signature)) {
            return null;
        }
        return parts[0];
    }

    public boolean isValid(String token) {
        return extractUsername(token) != null;
    }

    private String sign(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] raw = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(raw);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to sign token", e);
        }
    }

    private boolean isValidSignature(String payload, String signature) {
        String expected = sign(payload);
        return MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8), signature.getBytes(StandardCharsets.UTF_8));
    }
}
