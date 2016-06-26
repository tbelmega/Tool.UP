package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.database.DatabaseController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tbelm on 26.06.2016.
 * <p>
 * Returns a set of applications that do match the search string.
 * <p>
 * An Application matches, if the search string is contained either in its title or description,
 * or in the title or description of one of its features.
 * The comparison is NOT case sensitive (all lowercase).
 */
public class ApplicationStringSearch {
    private final String searchString;
    private Set<Application> result = new HashSet<>();
    private Collection<BusinessObject> allFeatures;
    private Map<String, BusinessObject> allApps;

    public ApplicationStringSearch(String searchString) throws IOException, SQLException {
        this.searchString = searchString.toLowerCase();
        init();
        search();
    }

    public Set<Application> getResult() {
        return this.result;
    }


    private void search() throws IOException, SQLException {
        for (BusinessObject feature : allFeatures) {
            if (matchesSearchString(feature)) addRelatedApplications((Feature) feature);
        }

        for (BusinessObject application : allApps.values()) {
            if (matchesSearchString(application)) result.add((Application) application);
        }
    }

    private boolean matchesSearchString(BusinessObject bo) {
        return bo.getTitle().toLowerCase().contains(searchString) ||
                (bo.getDescription() != null && bo.getDescription().toLowerCase().contains(searchString));
    }

    private void addRelatedApplications(Feature f) {
        for (String appId : f.getRelatedApplications()) {
            if (allApps.containsKey(appId)) {
                result.add((Application) allApps.get(appId));

                //Remove from the set of apps that still need to be searched for a match,
                //because it is already added to the result set
                allApps.remove(appId);
            }
        }
    }


    /**
     * Load all features and applications from the database.
     *
     * @throws SQLException
     * @throws IOException
     */
    private void init() throws SQLException, IOException {
        allFeatures = DatabaseController.getInstance().
                loadAllFrom(DatabaseController.TABLE_NAME_FEATURE).values();
        allApps = DatabaseController.getInstance().
                loadAllFrom(DatabaseController.TABLE_NAME_APPLICATION);
    }
}
