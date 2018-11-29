/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Handles authorization specific requests.
 * @author johan
 */
@Path("")
public class RequestHandler {
    @GET
    @Path("/auth")
    public Response authWithGithub(@QueryParam("client_code") String code) {
        Service.getInstance().authWithCodeAndSignUpFallback(code);
        return Response.ok("Check console").build();
    }
}
