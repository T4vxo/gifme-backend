/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administratör
 */
@Path("")
public class RequestHandler {
    @GET
    @Path("/9gag/post/random")
    public Response randomPost() {
        return Response.ok("HELO").build();
    }
}
