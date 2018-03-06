package fr.lyon1.avote.logic.model.api;

import fr.lyon1.avote.logic.model.entity.Restriction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiRequestRestriction {
    private Restriction restriction;
    private int anonymity; //0 -> false, 1 -> true, other -> both (anonymous & not anonymous)
    private Date createdAfter;
    private Date createdBefore;

    public ApiRequestRestriction() {
        this.restriction = new Restriction();
        this.anonymity = 2;
        this.createdAfter = null;
        this.createdBefore = null;
    }

    public static ApiRequestRestriction buildApiRequestRestriction(String ageMin, String ageMax, String city, String department, String anonymity, String createdAfter, String createdBefore) throws Exception {
        ApiRequestRestriction current = new ApiRequestRestriction();
        current.restriction = new Restriction();
        if (ageMin.isEmpty()) {
            current.restriction.setAgeMin(0);
        } else {
            current.restriction.setAgeMin(Integer.parseInt(ageMin));
        }
        if (ageMax.isEmpty()) {
            current.restriction.setAgeMax(0);
        } else {
            current.restriction.setAgeMax(Integer.parseInt(ageMax));
        }
        current.restriction.setCity(city);
        current.restriction.setDepartment(department);
        if (anonymity.equals("0") || anonymity.equals("1")) {
            current.anonymity = Integer.parseInt(anonymity);
        } else {
            current.anonymity = 2;
        }

        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            if (createdAfter.isEmpty() || createdAfter == null) {
                current.createdAfter = null;
            } else {
                current.createdAfter = formatter.parse(createdAfter);
            }
            if (createdBefore.isEmpty() || createdBefore == null) {
                current.createdBefore = null;
            } else {
                current.createdBefore = formatter.parse(createdBefore);
            }
        } catch (ParseException e) {
            throw new Exception("Invalid date format : " + createdAfter + ", " + createdBefore + " ", e);
        }
        return current;
    }

    public Restriction getRestriction() {
        return restriction;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    public int getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(int anonymity) {
        this.anonymity = anonymity;
    }

    public Date getCreatedAfter() {
        return createdAfter;
    }

    public void setCreatedAfter(Date createdAfter) {
        this.createdAfter = createdAfter;
    }

    public Date getCreatedBefore() {
        return createdBefore;
    }

    public void setCreatedBefore(Date createdBefore) {
        this.createdBefore = createdBefore;
    }
}
