package Trade;

import java.sql.Date;
//VO
public class Trade {
	private int tradeId;
	private int stockId;
	private int sell;
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", stockId=" + stockId + ", sell=" + sell + ", buy=" + buy + ", price="
				+ price + ", tradeDate=" + tradeDate + "]";
	}
	private int buy;
	private int traderNum;
	private int price;
	private Date tradeDate;
	public Trade(int tradeId, int stockId, int sell, int buy, int traderNum, int price, Date tradeDate) {
		super();
		this.tradeId = tradeId;
		this.stockId = stockId;
		this.sell = sell;
		this.buy = buy;
		this.traderNum = traderNum;
		this.price = price;
		this.tradeDate = tradeDate;
	}
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public int getSell() {
		return sell;
	}
	public void setSell(int sell) {
		this.sell = sell;
	}
	public int getBuy() {
		return buy;
	}
	public void setBuy(int buy) {
		this.buy = buy;
	}
	public int getTraderNum() {
		return traderNum;
	}
	public void setTraderNum(int traderNum) {
		this.traderNum = traderNum;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	
}
