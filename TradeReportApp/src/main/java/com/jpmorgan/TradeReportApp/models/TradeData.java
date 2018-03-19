package com.jpmorgan.TradeReportApp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.jpmorgan.TradeReportApp.interfaces.ICurrency;
import com.jpmorgan.TradeReportApp.interfaces.IEntity;
import com.jpmorgan.TradeReportApp.interfaces.ITradeData;
import com.jpmorgan.TradeReportApp.interfaces.ITradeType;
import com.jpmorgan.TradeReportApp.models.TradeType.TypeOfTrade;

/* Software Design */
/*This class holds all the data for a trade. It implements an interface so that if there are more 
 * methods in the future they can be kept there.*/
public class TradeData implements ITradeData {

	private ICurrency cur;
	private IEntity ent;
	private ITradeType tradeType;
	private Date instructionDate;
	private Date settlementDate;
	private int units;
	private Double pricePerUnit;
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
	private Calendar c = Calendar.getInstance();

	/* a constructor that initialises all the trade elements */
	public TradeData(ICurrency cur, IEntity ent, ITradeType tradeType, String instructionDate, String settlementDate, int units,
			double pricePerUnit) {
		super();
		this.cur = cur;
		this.ent = ent;
		this.tradeType = tradeType;

		try {

			this.instructionDate = this.sdf.parse(instructionDate);

			/*
			 * I used the setSettlementDate to initialise the param to force
			 * settlement date not to fall on weekend
			 */

			this.setSettlementDate(settlementDate);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		this.units = units;

		this.pricePerUnit = pricePerUnit;

	}

	/* a constructor that set elements while reading the data from the file */
	public TradeData(String[] tradeElements) {

		setElements(tradeElements);
	}

	/*
	 * a default constructor which is used by one Junit test to test dates of
	 * trades
	 */
	public TradeData() {

	}

	/*
	 * Function to save the settlement date. It receives a String but saves an
	 * object of class Date.
	 * 
	 * @param settlementDate String
	 */
	public void setSettlementDate(String settlementDate) {

		try {

			this.settlementDate = this.sdf.parse(settlementDate);

			this.c.setTime(this.settlementDate);

			int iDayOfWeek = this.c.get(Calendar.DAY_OF_WEEK);

			/*
			 * We add 1 day to settlement date until the settlement date not
			 * fall in weekend
			 */
			while (!((iDayOfWeek >= this.cur.getWeekStart()) && (iDayOfWeek <= this.cur.getWeekEnds()))) {

				this.c.add(Calendar.DATE, 1);

				iDayOfWeek = this.c.get(Calendar.DAY_OF_WEEK);
			}

			this.settlementDate = this.c.getTime();

		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	/*
	 * Function to save the instruction date. It receives a String but save a
	 * object of class Date.
	 * 
	 * @param instructionDate String
	 */
	public void setInstructionDate(String instructionDate) {

		try {

			this.instructionDate = this.sdf.parse(instructionDate);

		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	/* Error handling */
	@SuppressWarnings({ "unlikely-arg-type" })
	public boolean validateInputData() {

		boolean allDataAreValid = false;
		String emptyString = "";

		/*
		 * there is no point to check for units as units as int is initialised
		 * as 0
		 */
		if (cur != null && ent != null && tradeType != null && instructionDate != null && settlementDate != null
				&& pricePerUnit != null) {

			/*
			 * there is no point to check if units is int because if you omit
			 * units they will be initialised as 0
			 */
			if (!this.cur.equals(emptyString) || !this.ent.equals(emptyString) || !this.tradeType.equals(emptyString)
					|| !this.instructionDate.equals(emptyString) || !this.settlementDate.equals(emptyString)
					|| (this.pricePerUnit instanceof Double)) {

				allDataAreValid = true;
			}
		}

		return allDataAreValid;

	}

	/*
	 * This function iterates through all the trade elements in a line of the
	 * file and sets them accordingly using the setter methods.
	 */
	public void setElements(String[] tradeElements) {

		/* iterate through every trade element */
		for (String tradeElement : tradeElements) {

			/* to get the value after the = */
			String[] tradeNameValue = tradeElement.split("=");

			/*
			 * Error handling: i check that the tradeNameValue string array has
			 * size 2 before i set values to trade data to avoid array index out
			 * of bound exceptions
			 */
			Currency cur = new Currency("", 1.0);

			if (tradeNameValue[0].equals("cur") && tradeNameValue.length == 2) {

				cur = new Currency(tradeNameValue[1], 1.0);
				this.setCur(cur);

			}

			if (tradeNameValue[0].equals("agreededFx") && tradeNameValue.length == 2) {

				cur.setAgreededFx(Double.valueOf(tradeNameValue[1]));
				this.setCur(cur);
			}

			if (tradeNameValue[0].equals("ent") && tradeNameValue.length == 2) {

				Entity ent = new Entity(tradeNameValue[1].replace("entity", ""));
				this.setEnt(ent);
			}

			if (tradeNameValue[0].equals("tt") && tradeNameValue.length == 2) {

				TypeOfTrade typeOfTrade = TypeOfTrade.S;

				if (tradeNameValue[1].equals("buyTradeType")) {

					typeOfTrade = TypeOfTrade.B;
				}

				this.setTradeType(new TradeType(typeOfTrade));

			}

			if (tradeNameValue[0].equals("instructionDate") && tradeNameValue.length == 2) {

				this.setInstructionDate(tradeNameValue[1].trim());
			}

			if (tradeNameValue[0].equals("settlementDate") && tradeNameValue.length == 2) {

				this.setSettlementDate(tradeNameValue[1].trim());
			}

			if (tradeNameValue[0].equals("units") && tradeNameValue.length == 2) {

				int units = Integer.parseInt(tradeNameValue[1].trim());
				this.setUnits(units);
			}

			if (tradeNameValue[0].equals("pricePerUnit") && tradeNameValue.length == 2) {

				Double pricePerUnit = Double.valueOf(tradeNameValue[1].trim());
				this.setPricePerUnit(pricePerUnit);
			}

		}
	}

	/* Getters and setters */
	public int getUnits() {

		return this.units;
	}

	public void setUnits(int units) {

		this.units = units;
	}

	public double getPricePerUnit() {

		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {

		this.pricePerUnit = pricePerUnit;
	}

	public Date getSettlementDateAsDate() {

		return this.settlementDate;
	}

	public String getSettlementDate() {

		return this.sdf.format(settlementDate);
	}

	public ICurrency getCur() {

		return this.cur;
	}

	public void setCur(Currency cur) {

		this.cur = cur;
	}

	public IEntity getEnt() {

		return this.ent;
	}

	public void setEnt(IEntity ent) {

		this.ent = ent;
	}

	public ITradeType getTradeType() {

		return this.tradeType;
	}

	public void setTradeType(TradeType tt) {

		this.tradeType = tt;
	}

	public String getInstructionDate() {

		return this.sdf.format(instructionDate);
	}

}
