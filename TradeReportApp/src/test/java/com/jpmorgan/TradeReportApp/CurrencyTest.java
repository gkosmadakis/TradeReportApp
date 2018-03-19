package com.jpmorgan.TradeReportApp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.TradeReportApp.models.Currency;

/*This tests the week start, week end and the agreeded Fx for every currency*/
public class CurrencyTest {

	private static final double DELTA = 1e-15;
	private Currency cur;

	@Before
	public void before() {

		this.cur = new Currency("USD", 1.0);

	}

	@Test
	public void daysUSD() {

		assertEquals(2, this.cur.getWeekStart());
		assertEquals(6, this.cur.getWeekEnds());
	}

	@Test
	public void daysAED() {

		this.cur.setCurrencySymbol("AED");

		assertEquals(1, this.cur.getWeekStart());
		assertEquals(5, this.cur.getWeekEnds());
	}

	@Test
	public void daysSAR() {

		this.cur.setCurrencySymbol("SAR");

		assertEquals(1, this.cur.getWeekStart());
		assertEquals(5, this.cur.getWeekEnds());
	}

	@Test
	public void aggreededFXUSD() {

		this.cur.setAgreededFx(0.50);
		assertEquals(1, cur.getAgreededFx(), DELTA);

		this.cur.setCurrencySymbol("AED");
		this.cur.setAgreededFx(0.20);
		assertEquals(0.20, cur.getAgreededFx(), DELTA);
	}
}
