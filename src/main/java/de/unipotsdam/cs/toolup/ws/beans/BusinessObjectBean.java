package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

public class BusinessObjectBean implements Comparable<BusinessObjectBean> {

    private String id;
    private String title;
    private String description;

    public BusinessObjectBean(BusinessObject bo) {
        this.id = bo.getUuid();
        this.title = bo.getTitle();
        this.description = bo.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof BusinessObjectBean))
            return false;
        if (obj == this)
            return true;

        BusinessObjectBean anotherBean = (BusinessObjectBean) obj;

        return new EqualsBuilder()
               .append(this.id, anotherBean.id)
               .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(5,47)
                .append(this.id)
                .toHashCode();
    }


    @Override
    public int compareTo(BusinessObjectBean o) {
        return this.getId().compareTo(o.getId());
    }

    protected String getJSONRepresentation(String businessObjectId) throws IOException, SQLException, InvalidIdException {
        if (null == businessObjectId) return null;

        BusinessObject bo = DatabaseController.getInstance().load(businessObjectId);
        return bo.convertToJson().toString();
    }

    protected Collection<String> getJSONRepresentations(Collection<String> relatedBOs) throws IOException, SQLException, InvalidIdException {
        Collection<String> apps = new HashSet<>();
        for (String id: relatedBOs) {
            apps.add(getJSONRepresentation(id));
        }
        return apps;
    }
}
