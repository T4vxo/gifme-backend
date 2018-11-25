/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.greetings9gag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import te.te4.gifmebackend.greetings9gag.models.Gag;
import com.alibaba.fastjson.JSON;

/**
 *
 * @author Administratör
 */
@Path("/9gag")
public class RequestHandler {

    @GET
    @Path("/post/random")
    public Response randomPost() {
        Gag post = Service.getInstance().getRandomResult();
        if (post == null) {
            return Response.serverError().build();
        }
        return Response.ok(JSON.toJSONString(post)).build();
    }
}
