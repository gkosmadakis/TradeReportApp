package com.jpmorgan.TradeReportApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jpmorgan.TradeReportApp.interfaces.IEntity;
import com.jpmorgan.TradeReportApp.models.Currency;
import com.jpmorgan.TradeReportApp.models.Entity;
import com.jpmorgan.TradeReportApp.models.Trade;
import com.jpmorgan.TradeReportApp.models.TradeData;
import com.jpmorgan.TradeReportApp.models.TradeType;
import com.jpmorgan.TradeReportApp.models.TradeType.TypeOfTrade;
import com.jpmorgan.TradeReportApp.reports.TradeAppMain;

/*
 * This tests two trades that they are processed and added to the map with the ranking, 
 * the settlement dates for different currencies, as well as that data provided have errors
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TradeTest {

	@Test
	public void testFirstTrade() {

		/* Given */
		TradeAppMain.rankingOutgoing.getMap().clear();
		TradeData t = mock(TradeData.class);
		Currency cur = new Currency("USD", 1.0);
		Entity ent = new Entity("Foo");
		TradeType tt = new TradeType(TypeOfTrade.B);
		String instructionDate = "01 Jan 2016";
		Date settlementDate = null;
		SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			Date d = f.parse("02-Jan-2016");
			settlementDate = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int units = 300;
		Double pricePerUnit = 50.25;

		/* When */
		when(t.getCur()).thenReturn(cur);
		when(t.getEnt()).thenReturn(ent);
		when(t.getTradeType()).thenReturn(tt);
		when(t.getInstructionDate()).thenReturn(instructionDate);
		when(t.getSettlementDateAsDate()).thenReturn(settlementDate);
		when(t.getUnits()).thenReturn(units);
		when(t.getPricePerUnit()).thenReturn(pricePerUnit);
		Trade trade = new Trade(t);

		/* Then */
		TradeAppMain.processTrade(trade);
		for (Map.Entry<IEntity, Double> entry : TradeAppMain.rankingOutgoing.getMap().entrySet()) {
			assertTrue(entry.getKey().getName().equals("Foo"));
			assertTrue(entry.getValue() == 15075.0);
		}

	}

	@Test
	public void testSecondTrade() {

		/* Given */
		TradeAppMain.rankingIncoming.getMap().clear();
		TradeData t = mock(TradeData.class);
		Currency cur = new Currency("SAR", 0.5);
		Entity ent = new Entity("One");
		TradeType tt = new TradeType(TypeOfTrade.S);
		String instructionDate = "01 Jan 2016";
		Date settlementDate = null;
		SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			Date d = f.parse("02-Jan-2016");
			settlementDate = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int units = 500;
		Double pricePerUnit = 125.5;

		/* When */
		when(t.getCur()).thenReturn(cur);
		when(t.getEnt()).thenReturn(ent);
		when(t.getTradeType()).thenReturn(tt);
		when(t.getInstructionDate()).thenReturn(instructionDate);
		when(t.getSettlementDateAsDate()).thenReturn(settlementDate);
		when(t.getUnits()).thenReturn(units);
		when(t.getPricePerUnit()).thenReturn(pricePerUnit);
		Trade trade = new Trade(t);

		/* Then */
		TradeAppMain.processTrade(trade);
		for (Map.Entry<IEntity, Double> entry : TradeAppMain.rankingIncoming.getMap().entrySet()) {
			assertTrue(entry.getKey().getName().equals("One"));
			assertTrue(entry.getValue() == 31375.0);
		}

	}

	@Test
	public void testDateOfTrade() {

		/* Given */
		TradeData t = new TradeData();
		Currency cur = new Currency("USD", 0.0);

		/* when */ /* Sunday with USD */
		t.setCur(cur);
		t.setSettlementDate("04 Mar 2018");

		/* then */
		assertEquals("05 Mar 2018", t.getSettlementDate());

		/* when */ /* Saturday with SAR */
		t.setCur(new Currency("SAR", 0.10));
		t.setSettlementDate("03 Mar 2018");

		/* then */
		assertEquals("04 Mar 2018", t.getSettlementDate());

		/* when */ /* Friday with AED */
		t.setCur(new Currency("AED", 0.10));
		t.setSettlementDate("02 Mar 2018");

		/* then */
		assertEquals("04 Mar 2018", t.getSettlementDate());
	}

	@Test
	public void testDataAreValid() {

		/* Given */
		TradeData t = mock(TradeData.class);
		Currency cur = new Currency("USD", 1.0);

		/* Input of an invalid data */
		Entity ent = new Entity("");
		TradeType tt = new TradeType(TypeOfTrade.B);
		String instructionDate = "01 Jan 2016";
		String settlementDate = "02 Jan 2016";
		int units = 300;
		Double pricePerUnit = 50.25;

		/* When */
		when(t.getCur()).thenReturn(cur);
		when(t.getEnt()).thenReturn(ent);
		when(t.getTradeType()).thenReturn(tt);
		when(t.getInstructionDate()).thenReturn(instructionDate);
		when(t.getSettlementDate()).thenReturn(settlementDate);
		when(t.getUnits()).thenReturn(units);
		when(t.getPricePerUnit()).thenReturn(pricePerUnit);
		Trade trade = new Trade(t);

		/* Then */
		assertEquals(false, trade.getTradeData().validateInputData());
	}

}
