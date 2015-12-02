package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.Feature;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

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

    public static Collection<FeatureBean> getAllFeatures() throws IOException, SQLException {
        Collection<FeatureBean> result = new HashSet<>();
        Map<String, BusinessObject> allFeats = DatabaseController.getInstance().loadAllFrom(DatabaseController.TABLE_NAME_FEATURE);

        for (BusinessObject feat : allFeats.values()) {
            result.add(new FeatureBean((Feature) feat));
        }
        return result;
    }
}



