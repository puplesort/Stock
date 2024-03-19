package stock;

import java.util.ArrayList;
import java.util.Scanner;

public class StockService {
	private StockDao dao;
	public StockService() {
		dao = new StockDao();
	}
	public void addStock(Scanner sc,String name) {
		System.out.println("상장 가격: ");
		int price = sc.nextInt();
		dao.insert(new Stock(0,name,price,0));
	}
//	public void updatestock(int id, int cur, int change) {
//		System.out.println("주가 갱신");
//		dao.update(new Stock(id,"",cur,change));
//	}
	public void delStock(int num) {
		System.out.println("주식 삭제");
		dao.delete(num);
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
			double c = (double) (m-s.getTotal())/s.getTotal()*100;
			s.setTotal(m);
			s.setPrice_number(Math.round(c)*1000/1000.0);
			System.out.println(Math.round(c)*1000/1000.0);
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
