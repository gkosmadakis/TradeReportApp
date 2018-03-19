package com.jpmorgan.TradeReportApp.models;

import com.jpmorgan.TradeReportApp.interfaces.IEntity;

public class Entity implements IEntity {

	private String name;

	public Entity(String name) {

		this.name = name;
	}

	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

}
