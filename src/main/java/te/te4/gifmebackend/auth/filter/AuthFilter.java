/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import te.te4.gifmebackend.auth.User;
import te.te4.gifmebackend.db.ConnectionFactory;

/**
 *
 * @author johan
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            if (!authenticateAuthHeader(requestContext.getHeaderString("Authorization"))) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            requestContext.abortWith(Response.serverError().build());
        }
    }

    /**
     * Checks with a header for authentication.
     *
     * @param header
     * @return Whether the header has valid authentication credentials.
     */
    private boolean authenticateAuthHeader(String header) throws SQLException, IllegalStateException {
        try {
            //  We don't care about the user ID
            User.getUserIdFromAuth(header);
        } catch (SecurityException secEx) {
            return false;
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (IllegalStateException illegalStateEx) {
            throw illegalStateEx;
        }
        
        return false;
    }
}
