package com.jpmorgan.TradeReportApp.models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.jpmorgan.TradeReportApp.interfaces.IEntity;

public class Ranking {

	private Map<IEntity, Double> map = new HashMap<IEntity, Double>();
	private String name;

	public Ranking(String name) {

		this.name = name;
	}

	/* add the amount to the map */
	public void addAmount(IEntity ent, double amount) {

		if (!this.map.containsKey(ent)) {

			this.map.put(ent, 0.0);

		}

		amount = this.map.get(ent) + amount;

		this.map.replace(ent, amount);
	}

	/*
	 * this function prints the contents of the map sorted by value in
	 * descending order
	 */
	public void printRanking() {

		System.out.println(this.name);

		Map<IEntity, Double> sortedMapByValue = map.entrySet().stream()
				.sorted(Map.Entry.<IEntity, Double> comparingByValue().reversed()).limit(10)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		for (Map.Entry<IEntity, Double> entry : sortedMapByValue.entrySet()) {
			System.out.println(entry.getKey().getName() + "=" + entry.getValue());
		}

	}

	public Map<IEntity, Double> getMap() {

		return this.map;
	}
}
