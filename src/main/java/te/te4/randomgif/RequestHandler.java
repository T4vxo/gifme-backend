/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.randomgif;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author ElKebabHenry
 */

@Path("/radgif")
public class RequestHandler {
    
    @Path("/random/jeff")
    @GET
    public Response randomGif(){
        return Response.ok().build();
    }
}
