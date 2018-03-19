package com.jpmorgan.TradeReportApp.reports;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.jpmorgan.TradeReportApp.models.DailyReport;
import com.jpmorgan.TradeReportApp.models.Ranking;
import com.jpmorgan.TradeReportApp.models.Trade;
import com.jpmorgan.TradeReportApp.models.TradeData;
import com.jpmorgan.TradeReportApp.models.TradeType.TypeOfTrade;

public class TradeAppMain {

	/* A set for trades */
	private static LinkedHashSet<Trade> trades;
	/* Maps for rankings */
	public static Ranking rankingIncoming = new Ranking("Incoming Ranking:");
	public static Ranking rankingOutgoing = new Ranking("Outgoing Ranking:");
	private static DailyReport dr = new DailyReport();
	private static TradeData tradeData;

	public static void main(String[] args) {

		/* load all the data for the trades from the file testInput */
		loadTradeDataFromFile();

		Iterator<Trade> itr = trades.iterator();

		while (!trades.isEmpty() && itr.hasNext()) {

			Trade t = itr.next();

			TradeAppMain.processTrade(t);

		}

		/* print the daily report */
		dr.printDailyReport();

		/* print incoming and outgoing ranking */
		TradeAppMain.rankingIncoming.printRanking();
		TradeAppMain.rankingOutgoing.printRanking();
	}

	/*
	 * this method loads all the trade data from the testInput file and
	 * populates the trades set
	 */
	public static void loadTradeDataFromFile() {

		trades = new LinkedHashSet<Trade>();

		try {

			String line;
			BufferedReader inputFile = new BufferedReader(new FileReader("testFolder/testInput.txt"));

			while ((line = inputFile.readLine()) != null) {

				/* split every trade element by comma */
				String[] tradeElements = line.split(",");

				tradeData = new TradeData(tradeElements);

				/* Error Handling */
				/*
				 * check if all the data are valid before adding them to the
				 * trade. if they are not, log a message and terminate the
				 * program
				 */
				if (tradeData.validateInputData()) {

					Trade t = new Trade(tradeData);

					trades.add(t);

				}

				else {

					System.out.println("Some of the trade data have errors or are empty, check your input data and try again");

					System.exit(1);
				}

			}

			inputFile.close();

		} catch (java.io.IOException e) {

			e.printStackTrace();
		}

	}

	/*
	 * This function receives a Trade object calculate its amount and pass it to
	 * rankings and daily reports.
	 */
	public static void processTrade(Trade trade) {

		TradeData data = trade.getTradeData();

		Double amount = trade.getAmount();

		if (data.getTradeType().getTradeType() == TypeOfTrade.B) {

			dr.addOutgoing(data.getSettlementDateAsDate(), amount);

			TradeAppMain.rankingOutgoing.addAmount(data.getEnt(), amount);

		} else {

			dr.addIncoming(data.getSettlementDateAsDate(), amount);

			TradeAppMain.rankingIncoming.addAmount(data.getEnt(), amount);
		}

	}

	/* Getters methods */
	public static LinkedHashSet<Trade> getTrades() {

		return trades;
	}

	public static DailyReport getDailyReport() {

		return dr;
	}

}
