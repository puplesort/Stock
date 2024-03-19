package stock;

public class Stock {
	private int stock_id;
	private String stock_name;
	private int total;
	private double price_change;
	public int getStock_id() {
		return stock_id;
	}
	public void setStock_id(int stock_id) {
		this.stock_id = stock_id;
	}
	
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public double getPrice_change() {
		return price_change;
	}
	public void setPrice_number(double price_number) {
		this.price_change = price_number;
	}
	public Stock(int stock_id, String stock_name, int total, double price_number) {
		super();
		this.stock_id = stock_id;
		this.stock_name = stock_name;
		this.total = total;
		this.price_change = price_number;
	}
	@Override
	public String toString() {
		return "Stock [stock_id=" + stock_id + ", stock_name=" + stock_name + ", total="
				+ total + ", price_number=" + price_change + "]";
	}
	public Stock() {
	}
	
}
