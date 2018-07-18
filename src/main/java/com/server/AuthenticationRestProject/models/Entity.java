package com.server.AuthenticationRestProject.models;

import java.time.OffsetDateTime;

public interface Entity {

    Integer getId();
    void setID(Integer id);
    OffsetDateTime getCreated();
    OffsetDateTime getModified();
}
