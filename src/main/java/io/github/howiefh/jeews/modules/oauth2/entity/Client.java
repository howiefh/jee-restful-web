/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.entity;

import io.github.howiefh.jeews.common.entity.BasicEntity;

/**
 *  
 *
 *  @author howiefh
 */
public class Client  extends BasicEntity {
	private static final long serialVersionUID = 1534976572117011533L;
	private String clientId;
	private String clientName;
	private String clientSecret;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", clientName=" + clientName
				+ ", clientSecret=" + clientSecret + "]";
	}
}
