package stock;
//
import java.util.Scanner;

import Trade.TradeService;

public class StockService {
	private StockDao dao;
	private TradeService t;
	public StockService() {
		dao = new StockDao();
		t=new TradeService();
	}
	public void addStock(Scanner sc,String name) {
		System.out.println("상장 가격: ");
		int price = sc.nextInt();
		dao.insert(new Stock(0,name,price,0));
		t.addSellAdimin(dao.findByname(name).getStock_id());
	}
	
	
	public void delStock(Scanner sc) {
		System.out.println("주식 삭제");
		String name=sc.next();
		dao.delete(name);
	}
	public void findByStock_id(Scanner sc) {
		System.out.println("stock id: ");
		int id = sc.nextInt();
		System.out.println(dao.findByNum(id));
	}
	public void findByStock_name(Scanner sc) {
		System.out.println("stock name:");
		String name = sc.next();
		System.out.println(dao.findByName(name));
	}
	public void findStockList() {
		for (Stock s : dao.findAll()) {
			System.out.println(s);
		}
	}
	public void changeStock() {
		for (Stock s : dao.findAll()) {
			int random0to100P = (int) (Math.random() * 1000);
			int random0to100M = (int) (Math.random() * -1000);
			int m = s.getTotal()+(random0to100P+random0to100M);
			System.out.println(m);
			double c = (double) (m-s.getTotal())/s.getTotal()*100;
			s.setTotal(m);
			s.setPrice_number(Math.round(c)*1000/1000.0);
			System.out.println(Math.round(c)*1000/1000.0);
			System.out.println(s);
			dao.update(s);
			
		}
	}
	public void up() {
		if(!dao.findByPrice_Change().isEmpty()) {
		for (Stock s : dao.findByPrice_Change()) {
			System.out.println(s.getStock_name()+" 5%이상 상승");
		}
		}
	}
	public void down() {
		if(!dao.findByPrice_ChangeD().isEmpty()) {
			for (Stock s : dao.findByPrice_ChangeD()) {
				System.out.println(s.getStock_name()+" 5%이상 하락");
			}
		
		}
	}
	 
}
