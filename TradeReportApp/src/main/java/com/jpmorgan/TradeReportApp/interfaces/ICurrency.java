package com.jpmorgan.TradeReportApp.interfaces;

public interface ICurrency {

	public String getCurrencySymbol();

	public void setCurrencySymbol(String currencySymbol);

	public int getWeekStart();

	public int getWeekEnds();

	public double getAgreededFx();

	public void setAgreededFx(double agreededFx);
}
