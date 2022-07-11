/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.service;

import java.util.List;
import java.util.UUID;

import io.github.howiefh.jeews.modules.oauth2.dao.ClientDao;
import io.github.howiefh.jeews.modules.oauth2.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author howiefh
 */
@Service
public class ClientService {
    @Autowired
    private ClientDao clientDao;

    public int save(Client client) {
        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        return clientDao.save(client);
    }

    public int update(Client client) {
        return clientDao.update(client);
    }

    public int delete(Long id) {
        return clientDao.delete(id);
    }

    public int deleteBatch(List<Long> ids) {
        return clientDao.deleteBatch(ids);
    }

    public Client findOne(Long id) {
        return clientDao.findOne(id);
    }

    public List<Client> findAll() {
        return clientDao.findAll();
    }

    public Page<Client> findPageBy(Pageable pageable, Client client) {
        List<Client> clients = (List<Client>) clientDao.findPageBy(pageable, client);
        long count = clientDao.countBy(client);
        return new PageImpl<Client>(clients, pageable, count);
    }

    public Client findByClientName(String clientName) {
        return clientDao.findByClientName(clientName);
    }

    public Client findByClientId(String clientId) {
        return clientDao.findByClientId(clientId);
    }

    public Client findByClientSecret(String clientSecret) {
        return clientDao.findByClientSecret(clientSecret);
    }
}
