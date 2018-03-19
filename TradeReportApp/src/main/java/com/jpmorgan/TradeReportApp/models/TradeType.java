package com.jpmorgan.TradeReportApp.models;

import com.jpmorgan.TradeReportApp.interfaces.ITradeType;

public class TradeType implements ITradeType {

	private TypeOfTrade tradeType;

	public enum TypeOfTrade {
		S, B;
	};

	public TradeType(TypeOfTrade t) {

		this.tradeType = t;
	}

	public TypeOfTrade getTradeType() {

		return tradeType;
	}

	public void setTradeType(TypeOfTrade tradeType) {

		this.tradeType = tradeType;
	}

}
