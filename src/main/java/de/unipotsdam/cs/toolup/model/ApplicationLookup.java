package de.unipotsdam.cs.toolup.model;


import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ApplicationLookup {


    public static final String LIST_DELIMITER = ",";
    private final List<String> features;

    public ApplicationLookup(String features) {
        this.features = Arrays.asList(features.split(LIST_DELIMITER));
    }

    public Set<Application> getApplications() throws IOException, SQLException, InvalidIdException {
        throwExceptionIfNoFeaturesAreSpecified();
        Set<String> intersectionOfApps = buildIntersectionOfRelatedApplications();
        return loadApplicationsByIds(intersectionOfApps);
    }

    private void throwExceptionIfNoFeaturesAreSpecified() {
        if (features.isEmpty()){
            throw new UnsupportedOperationException("Feature ID must be specified.");
        }
    }

    private Set<String> buildIntersectionOfRelatedApplications() throws SQLException, InvalidIdException, IOException {
        //load the related applications of the first feature
        String firstFeatureId = features.get(0).trim();
        Set<String> intersectionOfApps = DatabaseController.getInstance()
                .loadRelatedApplicationsForFeat(firstFeatureId);

        //if there is more than one feature, make the intersection of the result sets
        for (int i = 1; i < features.size(); i++) {
            String id = features.get(i).trim();
            Set<String> apps = DatabaseController.getInstance()
                    .loadRelatedApplicationsForFeat(id);
            intersectionOfApps.retainAll(apps);
        }
        return intersectionOfApps;
    }

    private Set<Application> loadApplicationsByIds(Set<String> intersectionOfApps) throws SQLException, InvalidIdException, IOException {
        Set<Application> resultSetOfApps = new HashSet<>();
        for (String id: intersectionOfApps){
            Application app = (Application) DatabaseController.getInstance().load(id);
            resultSetOfApps.add(app);
        }
        return resultSetOfApps;
    }
}
