package io.dannio.fishpi.api;

import org.springframework.stereotype.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("api")
@Controller
public class Api {

    @POST
    @Path("/foo")
    public Response foo() {

        return Response.ok().entity("bar").build();
    }
}
