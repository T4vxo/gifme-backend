/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.memebuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administratör
 */
@Path("/memebuilder")
public class RequestHandler {
    @GET
    @Path("/text/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response randomText(@QueryParam("seek") String seek) throws IOException {
        List<String> res = GetWikiApi.GetWiki(seek);    
        MemeText text = new MemeText(res.get(0), res.get(1));
        return Response.ok(JSON.toJSONString(text)).build();
    }
}
