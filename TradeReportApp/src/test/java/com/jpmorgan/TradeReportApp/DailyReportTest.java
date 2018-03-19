package com.jpmorgan.TradeReportApp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import org.junit.Test;

import com.jpmorgan.TradeReportApp.models.Currency;
import com.jpmorgan.TradeReportApp.models.DayAmounts;
import com.jpmorgan.TradeReportApp.models.Entity;
import com.jpmorgan.TradeReportApp.models.Trade;
import com.jpmorgan.TradeReportApp.models.TradeData;
import com.jpmorgan.TradeReportApp.models.TradeType;
import com.jpmorgan.TradeReportApp.models.TradeType.TypeOfTrade;
import com.jpmorgan.TradeReportApp.reports.TradeAppMain;

/*This tests the daily report, takes 3 trades using mockito populates the set 
  and then checks that the map in the daily report has the right report data*/

public class DailyReportTest {

	private static final double DELTA = 1e-15;

	@Test
	public void testDailyReport() {

		LinkedHashSet<Trade> trades = new LinkedHashSet<Trade>();
		TradeAppMain.getDailyReport().getMap().clear();

		// Given
		TradeData t = mock(TradeData.class);
		Currency cur = new Currency("USD", 1.0);
		Entity ent = new Entity("Two");
		TradeType tt = new TradeType(TypeOfTrade.B);
		String instructionDate = "01 Jan 2018";
		Date settlementDate = null;
		SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			Date d = f.parse("01-Jan-2018");
			settlementDate = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int units = 300;
		Double pricePerUnit = 100.25;

		// When
		when(t.getCur()).thenReturn(cur);
		when(t.getEnt()).thenReturn(ent);
		when(t.getTradeType()).thenReturn(tt);
		when(t.getInstructionDate()).thenReturn(instructionDate);
		when(t.getSettlementDateAsDate()).thenReturn(settlementDate);
		when(t.getUnits()).thenReturn(units);
		when(t.getPricePerUnit()).thenReturn(pricePerUnit);
		Trade trade = new Trade(t);
		trades.add(trade);

		// Given
		TradeData t2 = mock(TradeData.class);
		Currency cur2 = new Currency("AED", 0.2);
		Entity ent2 = new Entity("Three");
		TradeType tt2 = new TradeType(TypeOfTrade.S);
		String instructionDate2 = "02 Jan 2018";
		Date settlementDate2 = null;
		try {
			Date d = f.parse("03-Jan-2018");
			settlementDate2 = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int units2 = 200;
		Double pricePerUnit2 = 115.5;

		// When
		when(t2.getCur()).thenReturn(cur2);
		when(t2.getEnt()).thenReturn(ent2);
		when(t2.getTradeType()).thenReturn(tt2);
		when(t2.getInstructionDate()).thenReturn(instructionDate2);
		when(t2.getSettlementDateAsDate()).thenReturn(settlementDate2);
		when(t2.getUnits()).thenReturn(units2);
		when(t2.getPricePerUnit()).thenReturn(pricePerUnit2);
		Trade trade2 = new Trade(t2);
		trades.add(trade2);

		// Given
		TradeData t3 = mock(TradeData.class);
		Currency cur3 = new Currency("SAR", 0.5);
		Entity ent3 = new Entity("Seven");
		TradeType tt3 = new TradeType(TypeOfTrade.S);
		String instructionDate3 = "04 Jan 2018";
		Date settlementDate3 = null;
		try {
			Date d = f.parse("04-Jan-2018");
			settlementDate3 = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int units3 = 400;
		Double pricePerUnit3 = 105.5;

		// When
		when(t3.getCur()).thenReturn(cur3);
		when(t3.getEnt()).thenReturn(ent3);
		when(t3.getTradeType()).thenReturn(tt3);
		when(t3.getInstructionDate()).thenReturn(instructionDate3);
		when(t3.getSettlementDateAsDate()).thenReturn(settlementDate3);
		when(t3.getUnits()).thenReturn(units3);
		when(t3.getPricePerUnit()).thenReturn(pricePerUnit3);
		Trade trade3 = new Trade(t3);
		trades.add(trade3);

		// Then
		Iterator<Trade> itr = trades.iterator();

		while (!trades.isEmpty() && itr.hasNext()) {

			Trade singleTrade = itr.next();

			TradeAppMain.processTrade(singleTrade);

		}

		int count = 0;
		for (Entry<String, DayAmounts> entry : TradeAppMain.getDailyReport().getMap().entrySet()) {

			if (count == 0) {
				assertEquals("2018-01-04", entry.getKey());
				assertEquals(21100.0, entry.getValue().getIncoming(), DELTA);
				assertEquals(0.0, entry.getValue().getOutgoing(), DELTA);

			}

			if (count == 1) {
				assertEquals("2018-01-01", entry.getKey());
				assertEquals(0.0, entry.getValue().getIncoming(), DELTA);
				assertEquals(30075.0, entry.getValue().getOutgoing(), DELTA);

			}

			if (count == 2) {
				assertEquals("2018-01-03", entry.getKey());
				assertEquals(4620.0, entry.getValue().getIncoming(), DELTA);
				assertEquals(0.0, entry.getValue().getOutgoing(), DELTA);

			}
			count++;
		}

	}
}
