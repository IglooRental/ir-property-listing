package si.uni_lj.fri.rso.ir_property_listing.api;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Metered;
import si.uni_lj.fri.rso.ir_property_listing.cdi.PropertyListingDatabase;
import si.uni_lj.fri.rso.ir_property_listing.models.PropertyListing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("propertylistings")
@Log
public class PropertyListingResource {
    @Inject
    private PropertyListingDatabase propertyListingDatabase;

    @Context
    protected UriInfo uriInfo;

    private Logger log = Logger.getLogger(PropertyListingResource.class.getName());


    @GET
    @Metered
    public Response getAllProperties() {
        if (ConfigurationUtil.getInstance().getBoolean("rest-config.endpoint-enabled").orElse(false)) {
            List<PropertyListing> properties = propertyListingDatabase.getProperties();
            return Response.ok(properties).build();
        } else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"reason\": \"Endpoint disabled.\"}").build();
        }
    }

    @GET
    @Path("/filtered")
    public Response getPropertiesFiltered() {
        if (ConfigurationUtil.getInstance().getBoolean("rest-config.endpoint-enabled").orElse(false)) {
            List<PropertyListing> customers = propertyListingDatabase.getPropertiesFilter(uriInfo);
            return Response.status(Response.Status.OK).entity(customers).build();
        } else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"reason\": \"Endpoint disabled.\"}").build();
        }
    }

    @GET
    @Metered
    @Path("/{propertyListingId}")
    public Response getPropertyListing(@PathParam("propertyListingId") String propertyListingId, @DefaultValue("true") @QueryParam("includeExtended") boolean includeExtended) {
        if (ConfigurationUtil.getInstance().getBoolean("rest-config.endpoint-enabled").orElse(false)) {
            PropertyListing propertyListing = propertyListingDatabase.getPropertyListing(propertyListingId, includeExtended);
            return propertyListing != null
                    ? Response.ok(propertyListing).build()
                    : Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"reason\": \"Endpoint disabled.\"}").build();
        }
    }

    @POST
    @Metered
    public Response addNewPropertyListing(PropertyListing property) {
        if (ConfigurationUtil.getInstance().getBoolean("rest-config.endpoint-enabled").orElse(false)) {
            propertyListingDatabase.createPropertyListing(property);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"reason\": \"Endpoint disabled.\"}").build();
        }
    }

    @DELETE
    @Metered
    @Path("/{propertyListingId}")
    public Response deletePropertyListing(@PathParam("propertyListingId") String propertyListingId) {
        if (ConfigurationUtil.getInstance().getBoolean("rest-config.endpoint-enabled").orElse(false)) {
            propertyListingDatabase.deletePropertyListing(propertyListingId);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"reason\": \"Endpoint disabled.\"}").build();
        }
    }
}
