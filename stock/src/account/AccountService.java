package account;

import java.util.Scanner;

import Trader.Trader;
import Trader.TraderDao;

public class AccountService {

	private AccountDao dao;
	private String num;
	private TraderDao trader;
	public AccountService() {
		dao=new AccountDao();
	}
	public AccountService(String num,TraderDao t) {
		dao=new AccountDao();
		this.num=num;
		this.trader=t;
	}
	public void addAccount(Scanner sc) {
		System.out.println("---------계좌 등록---------");
		System.out.println("계좌번호 입력:");
		String num=sc.next();
		System.out.println("잔액 입력:");
		int cash=sc.nextInt();
		System.out.println("은행명 입력:");
		String name=sc.next();
		if(dao.selectByAcNum(num)!=null) {
			System.out.println("이미 등록된 계좌!");
			return;
		}
		dao.insert(new Account(num,cash,name));
		System.out.println("등록 완료!");
	}
	
	public void withdrawal(Scanner sc) {
		System.out.println("---------출금---------");
		System.out.println("출금할 금액 입력:");
		int cash=sc.nextInt();
		Account target=dao.selectByAcNum(num);
		if(target==null) {
			System.out.println("로그인 부터 하시오");
			return;
		}
		int re=target.getCash()-cash;
		if(re<0) {
			System.out.println("잔액 부족");
		}
		target.setCash(re);
		dao.update(target);
		for(Trader t:trader.selectAll()) {
			if(t.getAccount_number().equals(num)) {
				t.setCash(t.getCash()+cash);
				trader.updateCash(t);
			}
		}
		System.out.println("출금 완료!");
	}
	
	public void deposit(Scanner sc) {
		System.out.println("---------입금---------");
		System.out.println("입금할 금액 입력:");
		int cash=sc.nextInt();
		Account target=dao.selectByAcNum(num);
		if(target==null) {
			System.out.println("로그인 부터 하시오");
			return;
		}
		int re=target.getCash()+cash;
		target.setCash(re);
		dao.update(target);
		System.out.println("입금 완료!");
		for(Trader t:trader.selectAll()) {
			if(t.getAccount_number().equals(num)) {
				t.setCash(t.getCash()-cash);
				trader.update(t);
			}
		}
	}
	public void delete() {
		System.out.println("---------삭제---------");
		Account target=dao.selectByAcNum(num);
		if(target==null) {
			System.out.println("로그인 부터 하시오");
			return;
		}
		dao.delete(target);
		System.out.println("삭제 완료!");
		
	}
	public void selecByAcNum() {
		System.out.println("---------잔액 조회---------");
		Account target=dao.selectByAcNum(num);
		if(target==null) {
			System.out.println("로그인 부터 하시오");
			return;
		}
		System.out.println("잔액: "+target.getCash());
	}
}
