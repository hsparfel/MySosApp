package com.pouillos.mysosapp.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mysosapp.dao.DaoSession;
import com.pouillos.mysosapp.dao.ContactDao;
import com.pouillos.mysosapp.dao.ContactEnlevementDao;

@Entity
public class ContactEnlevement {

    @Id
    private Long id;

    @NotNull
    private long contactId;

    @ToOne(joinProperty = "contactId")
    private Contact contact;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1957403330)
    private transient ContactEnlevementDao myDao;

    @Generated(hash = 980351644)
    public ContactEnlevement(Long id, long contactId) {
        this.id = id;
        this.contactId = contactId;
    }

    @Generated(hash = 815319529)
    public ContactEnlevement() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getContactId() {
        return this.contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    @Generated(hash = 321829790)
    private transient Long contact__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 714839487)
    public Contact getContact() {
        long __key = this.contactId;
        if (contact__resolvedKey == null || !contact__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContactDao targetDao = daoSession.getContactDao();
            Contact contactNew = targetDao.load(__key);
            synchronized (this) {
                contact = contactNew;
                contact__resolvedKey = __key;
            }
        }
        return contact;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 675168246)
    public void setContact(@NotNull Contact contact) {
        if (contact == null) {
            throw new DaoException(
                    "To-one property 'contactId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.contact = contact;
            contactId = contact.getId();
            contact__resolvedKey = contactId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1627085208)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContactEnlevementDao() : null;
    }
}
