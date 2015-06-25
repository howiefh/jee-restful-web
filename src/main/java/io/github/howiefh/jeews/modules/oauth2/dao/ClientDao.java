/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.dao;

import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.common.dao.PagingAndSortingDao;
import io.github.howiefh.jeews.modules.oauth2.entity.Client;

/**
 *  
 *
 *  @author howiefh
 */
@Repository
public interface ClientDao extends PagingAndSortingDao<Client, Long>{
    Client findByClientName(String clientName);
    Client findByClientId(String clientId);
    Client findByClientSecret(String clientSecret);
}
