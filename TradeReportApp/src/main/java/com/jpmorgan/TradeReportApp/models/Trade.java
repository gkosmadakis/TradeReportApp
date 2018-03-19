package com.jpmorgan.TradeReportApp.models;

/* Software Design */
/*This class creates an object tradeData which is used on the main class to be populated with the trade data.
 * I use the tradeData class as a model that keeps all the data for a trade. This makes it
 *  more extendable because if in the future will be a new field for a trade it will simply 
 *  go to the tradeData class*/
public class Trade {

	private TradeData tradeData;

	public Trade() {

	}

	public Trade(TradeData tradeData) {

		this.tradeData = tradeData;

	}

	public TradeData getTradeData() {

		return this.tradeData;
	}

	/* Calculate the amount for a trade */
	public Double getAmount() {

		return this.tradeData.getPricePerUnit() * this.tradeData.getUnits() * this.tradeData.getCur().getAgreededFx();
	}

}
