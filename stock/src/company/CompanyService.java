package company;

import java.util.Scanner;

import Trade.TradeService;
import stock.StockDao;
import stock.StockService;

public class CompanyService {
	private CompanyDao dao;
	private StockService sservice;
	private StockDao sdao;
	public CompanyService() {
		dao = new CompanyDao();
		sservice = new StockService();
		sdao=new StockDao();
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
	
	public void deleteCom(Scanner sc) {
		System.out.println("=== 회사 제거 ===");
		int num=sc.nextInt();
		if(dao.findByNum(num)==null) {
			System.out.println("not found");
			return;
	}
		Company c=dao.findByNum(num);
		dao.delete(c.getCompany_id());
		sdao.delete(c.getCompany_name());
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
