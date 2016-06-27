package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class AuthUtils {
    private SecureRandom random = new SecureRandom();

    public String getToken() {
        return new BigInteger(130, random).toString(32);
    }
}
