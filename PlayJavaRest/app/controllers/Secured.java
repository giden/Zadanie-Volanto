package controllers;

import com.google.inject.Inject;
import dao.AuthDAO;
import org.apache.commons.codec.binary.Base64;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Inject
    private AuthDAO authDAO;

    @Override
    public String getUsername(Context ctx) {
        String authorization = ctx.request().getHeader("Authorization");
        if (authorization != null) {
            String encoded = authorization.split(" ")[1];
            byte[] bytes = Base64.decodeBase64(encoded.getBytes());
            String[] credentials = new String(bytes).split(":");

            try {
                if (authDAO.findAuthByUsername(credentials[0]).token.equals(credentials[1])) {
                    ctx.args.put("username", credentials[0]);
                    return credentials[0];
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized();
    }
}
