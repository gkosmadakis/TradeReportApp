package com.jpmorgan.TradeReportApp.models;

public class DayAmounts {

	/* Total amount of incoming */
	private double incoming = 0;

	/* Total amount of outgoing */
	private double outgoing = 0;

	public DayAmounts() {

	}

	/* Getters and setters */
	public double getIncoming() {

		return this.incoming;
	}

	public void setIncoming(double incoming) {

		this.incoming = incoming;
	}

	public double getOutgoing() {

		return this.outgoing;
	}

	public void setOutgoing(double outgoing) {

		this.outgoing = outgoing;
	}

}
