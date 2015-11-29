package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.Feature;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class FeatureBean extends BusinessObjectBean{

    private Collection<String> applications;

    public FeatureBean(Feature feat) {
        super(feat);
        this.applications = feat.getRelatedApplications();
    }

    public static FeatureBean getBean(String id) throws IOException, SQLException, InvalidIdException {
        Feature feat = (Feature) DatabaseController.getInstance().load(id);
        return new FeatureBean(feat);
    }

}



