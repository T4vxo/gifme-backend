/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth;

import com.alibaba.fastjson.JSONObject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles authorization specific requests.
 *
 * @author johan
 */
@Path("")
public class RequestHandler {

    private static Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @GET
    @Path("/auth/github")
    public Response authWithGithub(@QueryParam("client_code") String code) {
        try {
            User user = Service.getInstance().authWithCodeAndSignUpFallback(code);
            JSONObject resultObj = new JSONObject();
            resultObj.put("token", user.getAuthToken());
            return Response.ok(resultObj.toJSONString()).build();
        } catch (IllegalArgumentException illegalArgEx) {
            logger.warn("GitHub auth error", illegalArgEx);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (Exception ex) {
            logger.error("GitHub auth generic error", ex);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/auth")
    public Response authWithBasic(@HeaderParam("Authorization") String auth) {
        try {
            User user = Service.getInstance().authWithBasicAuthHeader(auth);
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            JSONObject resultObj = new JSONObject();
            resultObj.put("token", user.getAuthToken());
            return Response.ok(resultObj.toJSONString()).build();
        } catch (Exception ex) {
            logger.error("Basic auth generic error", ex);
            return Response.serverError().build();
        }
    }
}
