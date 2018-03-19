package com.jpmorgan.TradeReportApp.models;

import com.jpmorgan.TradeReportApp.interfaces.ICurrency;

public class Currency implements ICurrency {

	private String currencySymbol;
	private int weekStart;
	private int weekEnds;
	private double agreededFx;

	public Currency(String currencySymbol, Double agreededFx) {

		this.currencySymbol = currencySymbol;
		this.agreededFx = agreededFx;
		this.init();
	}

	/*
	 * Function that initialises the weekends depending on the currency
	 */
	private void init() {

		int ws = 2;
		int we = 6;

		if (this.currencySymbol.equals("SAR") || this.currencySymbol.equals("AED")) {

			ws = 1;
			we = 5;
		}

		else if (this.currencySymbol.equals("USD")) {

			ws = 2;
			we = 6;

			// I suppose if the currency is USD the agreededFX is 1
			this.agreededFx = 1;
		}

		else {

			ws = 2;
			we = 6;
		}

		this.weekStart = ws;
		this.weekEnds = we;
	}

	/* Getters and setters */
	public String getCurrencySymbol() {

		return this.currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {

		this.currencySymbol = currencySymbol;
		this.init();
	}

	public int getWeekStart() {

		return this.weekStart;
	}

	public int getWeekEnds() {

		return this.weekEnds;
	}

	public double getAgreededFx() {

		return this.agreededFx;
	}

	public void setAgreededFx(double agreededFx) {

		// I prevent agreededFX change if the currency is USD
		if (!this.currencySymbol.equals("USD")) {

			this.agreededFx = agreededFx;
		}
	}

}
