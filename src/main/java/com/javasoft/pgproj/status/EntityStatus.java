/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.status;

import com.javasoft.pgproj.interfaces.EntityResource;

/**
 *
 * @author ayojava
 */
public abstract class EntityStatus implements EntityResource {

    public boolean isActive() {
        return getStatus() == EntityResource.ACTIVE;
    }

    public void setActive() {
        setStatus(EntityResource.ACTIVE);
    }

    public boolean isAvailable() {
        return getStatus() == EntityResource.AVAILABLE;
    }

    public void setAvailable() {
        setStatus(EntityResource.AVAILABLE);
    }

    public boolean isBorrowed() {
        return getStatus() == EntityResource.BORROWED;
    }

    public void setBorrowed() {
        setStatus(EntityResource.BORROWED);
    }

    public boolean isInactive() {
        return getStatus() == EntityResource.INACTIVE;
    }

    public void setInactive() {
        setStatus(EntityResource.INACTIVE);
    }

    public boolean isLocked() {
        return getStatus() == EntityResource.LOCKED;
    }

    public void setLocked() {
        setStatus(EntityResource.LOCKED);
    }

    public boolean isNew() {
        return getStatus() == EntityResource.NEW;
    }

    public void setNew() {
        setStatus(EntityResource.NEW);
    }

    public static String getStatusName(int status) {

        switch (status) {
            case 0:
                return "ACTIVE";
            case 1:
                return "INACTIVE";
            case 2:
                return "LOCKED";
            case 3:
                return "NEW";
            case 4:
                return "AVAILABLE";
            case 5:
                return "BORROWED";
            case 6:
                return "RETURNED";
        }
        return "";
    }
}
