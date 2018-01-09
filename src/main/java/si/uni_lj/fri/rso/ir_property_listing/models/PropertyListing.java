package si.uni_lj.fri.rso.ir_property_listing.models;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;

@Entity(name = "propertylistings")
@NamedQueries(value = {
        @NamedQuery(name = "PropertyListing.getAll", query = "SELECT p FROM propertylistings p")
})
@UuidGenerator(name = "idGenerator")
public class PropertyListing {
    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "property_id")
    private String propertyId;

    @Column(name = "lister_id")
    private String listerId;

    private String date;

    private Integer duration;

    public PropertyListing(String id, String propertyId, String listerId, String date, Integer duration) {
        this.id = id;
        this.propertyId = propertyId;
        this.listerId = listerId;
        this.date = date;
        this.duration = duration;
    }

    public PropertyListing() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getListerId() {
        return listerId;
    }

    public void setListerId(String listerId) {
        this.listerId = listerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
