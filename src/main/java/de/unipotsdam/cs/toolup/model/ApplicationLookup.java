package de.unipotsdam.cs.toolup.model;


import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.util.SubsetUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ApplicationLookup {


    public static final String LIST_DELIMITER = ",";
    private final List<String> features;

    public ApplicationLookup(String features) {
        this.features = Arrays.asList(features.split(LIST_DELIMITER));
        throwExceptionIfNoFeaturesAreSpecified();
    }


    public Set<Application> getAllMatchingApplications() throws InvalidIdException, SQLException, IOException {
        Set<String> resultSetOfApps = getApplicationsWithAtLeastOneFeature();
        return loadApplicationsByIds(resultSetOfApps);
    }

    /**
     * This method gets all Applications that match at least one feature of the feature list.
     */
    public Set<String> getApplicationsWithAtLeastOneFeature() throws IOException, SQLException, InvalidIdException {
        Set<String> unionOfRelatedApps = new HashSet<>();

        for (String featureId : features) {
            unionOfRelatedApps.addAll(DatabaseController.getInstance()
                    .loadRelatedApplicationsForFeature(featureId.trim()));
        }
        return unionOfRelatedApps;
    }


    public Set<Application> getBestMatchingApplications() throws IOException, SQLException, InvalidIdException {
        Set<String> resultSetOfApps = getApplicationsWithLargestSubSetOfFeatures();
        return loadApplicationsByIds(resultSetOfApps);
    }

    /**
     * This method gets all Applications that match the whole feature list;
     * if there are none, it gets the Applications that match the feature subsets with one feature missing;
     * if the are none, with two features missing and so on.
     */
    private Set<String> getApplicationsWithLargestSubSetOfFeatures() throws SQLException, InvalidIdException, IOException {
        Set<String> resultSetOfApps = new HashSet<>();
        int sizeOfFeatureList = features.size();

        while (resultSetOfApps.isEmpty() && sizeOfFeatureList > 0) {
            addAllApplicationsThatMatchFeatureSubsetsOfGivenSize(resultSetOfApps, sizeOfFeatureList);
            sizeOfFeatureList--;
        }
        return resultSetOfApps;
    }

    private void addAllApplicationsThatMatchFeatureSubsetsOfGivenSize(Set<String> resultSetOfApps, int sizeOfFeatureList) throws SQLException, InvalidIdException, IOException {
        Set<Set<String>> allSubSetsOfFeatures = SubsetUtil.getAllSubsetsOfSize(new HashSet<String>(features), sizeOfFeatureList);

        for (Set<String> subSetOfFeatures : allSubSetsOfFeatures) {
            Set<String> applicationsForFeatureSubSet = buildIntersectionOfRelatedApplications(new ArrayList<String>(subSetOfFeatures));
            resultSetOfApps.addAll(applicationsForFeatureSubSet);
        }
    }

    private void throwExceptionIfNoFeaturesAreSpecified() {
        if (features.isEmpty()){
            throw new UnsupportedOperationException("Feature ID must be specified.");
        }
    }

    private Set<String> buildIntersectionOfRelatedApplications(List<String> featureList) throws SQLException, InvalidIdException, IOException {
        //load the related applications of the first feature
        String firstFeatureId = featureList.get(0).trim();
        Set<String> intersectionOfApps = DatabaseController.getInstance()
                .loadRelatedApplicationsForFeature(firstFeatureId);

        //if there is more than one feature, make the intersection of the result sets
        for (int i = 1; i < featureList.size(); i++) {
            String id = featureList.get(i).trim();
            Set<String> apps = DatabaseController.getInstance()
                    .loadRelatedApplicationsForFeature(id);
            intersectionOfApps.retainAll(apps);
        }
        return intersectionOfApps;
    }


    private Set<Application> loadApplicationsByIds(Set<String> intersectionOfApps) throws SQLException, InvalidIdException, IOException {
        Set<Application> resultSetOfApps = new HashSet<>();
        for (String id: intersectionOfApps){
            BusinessObject app = DatabaseController.getInstance().load(id);
            if (!(app instanceof NullBusinessObject)) {
                resultSetOfApps.add((Application) app);
            }
        }
        return resultSetOfApps;
    }

}
