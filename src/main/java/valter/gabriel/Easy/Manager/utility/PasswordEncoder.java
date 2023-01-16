package valter.gabriel.Easy.Manager.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class PasswordEncoder {
    public static String encodePassword(String password) {
        IPasswordEncoder passwordEncoder = (pass) -> {
            MessageDigest algorithm = null;
            try {
                algorithm = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            byte[] messageDigest = Objects.requireNonNull(algorithm).digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            return hexString.toString();
        };
        return encode(passwordEncoder, password);
    }

    static String encode(IPasswordEncoder passwordEncoder, String password){
        return passwordEncoder.encode(password);
    }


}
