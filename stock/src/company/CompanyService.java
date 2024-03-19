package company;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import stock.StockService;

public class CompanyService {
	private CompanyDao dao;
	private StockService sservice;
	public CompanyService() {
		dao = new CompanyDao();
		sservice = new StockService();
	}
	public void addCom(Scanner sc) {
		System.out.println("=== 회사 추가 ===");
		System.out.println("company name: ");
		String name = sc.next();
		System.out.println("ceo name:");
		String ceo_name = sc.next();
		System.out.print("Enter the date (yyyy-MM-dd): ");
		String dateString = sc.next();
		System.out.println("holding: ");
		int holding = sc.nextInt();
		int volume = 0;
		System.out.println("info: ");
		String info = sc.next();
		sc.nextLine();
		dao.insert(new Company(0,name,ceo_name,dateString,holding,volume,info));
		sservice.addStock(sc,name);
	}
	
	public void editCom(Scanner sc) {
		System.out.println("=== 회사정보 수정 ===");
		System.out.println("company id: ");
		int id = sc.nextInt();
		if(dao.findByNum(id)==null) {
			System.out.println("not found");
			return;
		}
		System.out.println("new company name: ");
		String name = sc.next();
		System.out.println("new ceo name:");
		String ceo_name = sc.next();
		System.out.println("update holding: "); 
		int holding = sc.nextInt();
		System.out.println("update information: ");
		String info = sc.next();
		dao.updateInfo(new Company(id,name,ceo_name,null,holding,0,info));
	}
	
	public void editVol(int id, int vol) {
		if(dao.findByNum(id)==null) {
			System.out.println("not found");
			return;
		}
		dao.updateVol(new Company(id,"","",null,0,vol,""));
	}
	
	public void deleteCom(int id) {
		System.out.println("=== 회사 제거 ===");
		if(dao.findByNum(id)==null) {
			System.out.println("not found");
			return;
	}
		dao.delete(id);
		sservice.delStock(id);
	}
	public Company findByCom(int id) {
		if(dao.findByNum(id)==null) {
			System.out.println("not found");
			return null;
	}
		System.out.println(dao.findByNum(id));
		return dao.findByNum(id);
	}
	public void comList() {
		for (Company c : dao.findAll()) {
			System.out.println(c);
		}
	}
}
