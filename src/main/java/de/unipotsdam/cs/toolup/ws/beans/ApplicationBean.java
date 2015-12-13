package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ApplicationBean extends BusinessObjectBean {

    private Collection<String> categories;
    private Collection<String> features;

    public ApplicationBean(Application app) throws InvalidIdException, SQLException, IOException {
        super(app);

        this.categories = app.getRelatedCategories();
        this.features = app.getRelatedFeatures();
    }

    public Collection<String> getCategories() {
        return categories;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public Collection<String> getFeatures() {
        return features;
    }

    public void setFeatures(Collection<String> features) {
        this.features = features;
    }

    public static ApplicationBean getBean(String id) throws InvalidIdException, SQLException, IOException {
        Application app = (Application)DatabaseController.getInstance().load(id);
        return new ApplicationBean(app);

    }

    public static Collection<ApplicationBean> getBeans(Set<Application> applications) throws InvalidIdException, SQLException, IOException {
        Collection<ApplicationBean> appBeans = new HashSet<>();

        for (Application app: applications) {
            ApplicationBean bean = ApplicationBean.getBean(app.getUuid());
            appBeans.add(bean);
        }

        return appBeans;
    }

    public static Collection<ApplicationBean> getAllApplications() throws IOException, SQLException, InvalidIdException {
        Collection<ApplicationBean> result = new HashSet<>();
        Map<String, BusinessObject> allApps = DatabaseController.getInstance().loadAllFrom(DatabaseController.TABLE_NAME_APPLICATION);

        for (BusinessObject app : allApps.values()) {
            result.add(new ApplicationBean((Application) app));
        }
        return result;
    }



    public static Collection<ApplicationBean> searchFor(String searchString) throws IOException, SQLException, InvalidIdException {
        Collection<ApplicationBean> result = new HashSet<>();
        Map<String, BusinessObject> allApps = DatabaseController.getInstance().doFullTextSearch(searchString);

        for (BusinessObject app : allApps.values()) {
            result.add(new ApplicationBean((Application) app));
        }
        return result;

    }
}
