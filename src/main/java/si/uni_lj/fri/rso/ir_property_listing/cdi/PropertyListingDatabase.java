package si.uni_lj.fri.rso.ir_property_listing.cdi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import si.uni_lj.fri.rso.ir_property_listing.models.PropertyListing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
public class PropertyListingDatabase {
    private Logger log = LogManager.getLogger(PropertyListingDatabase.class.getName());

    @Inject
    private EntityManager em;

    private HttpClient httpClient = HttpClientBuilder.create().build();
    private ObjectMapper objectMapper = new ObjectMapper();


    public List<PropertyListing> getProperties() {
        TypedQuery<PropertyListing> query = em.createNamedQuery("PropertyListing.getAll", PropertyListing.class);
        return query.getResultList();
    }

    public List<PropertyListing> getPropertiesFilter(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(em, PropertyListing.class, queryParameters);
    }

    public PropertyListing getPropertyListing(String propertyListingId, boolean includeExtended) {
        PropertyListing propertyListing = em.find(PropertyListing.class, propertyListingId);
        if (propertyListing == null) {
            throw new NotFoundException();
        }
        if (includeExtended) {
            // nothing here yet
        }
        return propertyListing;
    }

    public PropertyListing createPropertyListing(PropertyListing propertyListing) {
        try {
            beginTx();
            em.persist(propertyListing);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return propertyListing;
    }

    public PropertyListing putPropertyListing(String propertyListingId, PropertyListing propertyListing) {
        PropertyListing p = em.find(PropertyListing.class, propertyListingId);
        if (p == null) {
            return null;
        }
        try {
            beginTx();
            propertyListing.setId(p.getId());
            propertyListing = em.merge(propertyListing);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        return propertyListing;
    }

    public boolean deletePropertyListing(String propertyListingId) {
        PropertyListing p = em.find(PropertyListing.class, propertyListingId);
        if (p != null) {
            try {
                beginTx();
                em.remove(p);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }
        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
