package com.server.AuthenticationRestProject.repositories;

import com.server.AuthenticationRestProject.models.Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@NoRepositoryBean
public interface EntityRepository<T extends Entity> extends CrudRepository<T, Integer> {

}
