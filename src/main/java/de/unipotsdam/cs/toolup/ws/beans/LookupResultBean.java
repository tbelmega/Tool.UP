package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.ApplicationLookup;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by tbelm on 11.06.2016.
 */
public class LookupResultBean {

    private Collection<ApplicationBean> bestMatches;


    private Collection<ApplicationBean> singleMatches;

    public LookupResultBean(Collection<ApplicationBean> bestMatches, Collection<ApplicationBean> singleMatches) {
        this.bestMatches = bestMatches;
        this.singleMatches = singleMatches;
    }

    public static LookupResultBean getBean(String features) throws InvalidIdException, SQLException, IOException {
        ApplicationLookup lookup = new ApplicationLookup(features);

        Set<Application> bestMatchingApplications = lookup.getBestMatchingApplications();
        Collection<ApplicationBean> bestMatches = ApplicationBean.getBeans(bestMatchingApplications);

        Set<Application> allMatchingApplications = lookup.getAllMatchingApplications();
        Collection<ApplicationBean> singleMatches = ApplicationBean.getBeans(allMatchingApplications);

        return new LookupResultBean(bestMatches, singleMatches);
    }

    public Collection<ApplicationBean> getSingleMatches() {
        return singleMatches;
    }

    public Collection<ApplicationBean> getBestMatches() {
        return bestMatches;
    }


}
