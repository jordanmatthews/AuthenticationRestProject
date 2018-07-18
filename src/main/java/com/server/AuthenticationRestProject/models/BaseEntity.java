package com.server.AuthenticationRestProject.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity implements Entity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(updatable = false)
    @JsonDeserialize
    @JsonSerialize
    private OffsetDateTime created;

    @JsonDeserialize
    @JsonSerialize
    private OffsetDateTime modified;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        created = getOrDefault(created, now);
        modified = getOrDefault(modified, now);
    }

    /**
     * @return a default value, if the specified value is null, or this entity is new
     */
    private <T> T getOrDefault(T value, T defaultValue) {
        return id != null && value != null ? value : defaultValue;
    }

    @PreUpdate
    public void preUpdate() {
        modified = OffsetDateTime.now();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    @Override
    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    @Override
    public OffsetDateTime getModified() {
        return modified;
    }

    public void setModified(OffsetDateTime modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof BaseEntity && equals((BaseEntity) that);
    }

    private boolean equals(BaseEntity that) {
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        return s.append("id:" + getId())
                .append(" ,")
                .append("created: " + getCreated())
                .append(" ,")
                .append("modified: " + getModified())
                .toString();
    }

}
