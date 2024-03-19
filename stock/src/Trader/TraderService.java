package Trader;

import java.util.ArrayList;
import java.util.Scanner;

import Trade.TradeDao;

public class TraderService {
	private TraderDao dao;
	private String loginId;
	private int authority;
	private int loginnum;

	public TraderDao getDao() {
		return dao;
	}

	public void setDao(TraderDao dao) {
		this.dao = dao;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public int getLoginnum() {
		return loginnum;
	}

	public void setLoginnum(int loginnum) {
		this.loginnum = loginnum;
	}

	public TraderService() {
		dao = new TraderDao();
	}

	// 회원가입
	public void addTrader(Scanner sc) {
		System.out.println("=== 회원가입 ===");
		String trader_id = "";

		do {
			System.out.print("아이디 : ");
			trader_id = sc.next();
		} while (dao.select(trader_id) != null);

		System.out.print("비밀번호 : ");
		String pwd = sc.next();
		System.out.print("계좌번호 : ");
		sc.nextLine();
		String account_number = sc.nextLine();
		System.out.print("은행명 : ");
		String bank_name = sc.next();
		System.out.print("(회원:0 / 관리자:1) : ");
		int authority = sc.nextInt();
		Trader t = new Trader(trader_id, pwd, account_number, bank_name, authority);
		dao.insert(t);
	}

	// 로그인
	public boolean login(Scanner sc) {
		System.out.println("=== 로그인 ===");

		System.out.print("아이디 : ");
		String trader_id = sc.next();
		System.out.print("비밀번호 : ");
		String pwd = sc.next();

		Trader t = dao.select(trader_id);
		boolean flag = false;

		if (t == null) {
			System.out.println("없는 아이디 입니다.");
		} else {
			if (pwd.equals(t.getPwd())) {

				if (t.getAuthority() == 1) {
					System.out.println("관리자 로그인 성공");
					loginId = trader_id;
					loginnum = t.getTrader_num();
					authority = 1;
					flag = true;
				} else {
					System.out.println("회원 로그인 성공");
					loginId = trader_id;
					loginnum = t.getTrader_num();
					authority = 0;
					flag = true;
				}
			} else {
				System.out.println("비밀번호가 틀렸습니다.");
			}
		}
		return flag;
	}

	// 로그아웃
	public void logout() {
		System.out.println("=== 로그아웃됨 ===");
		loginId = null;
		loginnum = 0;
	}

	// 탈퇴
	public void delTrader(Scanner sc) {
		System.out.println("=== 탈퇴 ===");
		System.out.println("탈퇴시 예치금 및 주식 반환 불가!");
		System.out.println("정말 탈퇴 하시겠습니까?   1.O    2.X");
		int i = sc.nextInt();
		if (i == 1) {
			int cnt = dao.delete(loginnum);
			if (cnt > 0) {
				System.out.println("탈퇴 완료");
				logout();
			} else {
				System.out.println("탈퇴 실패");
			}
		}

	}

	// 내정보 확인(보유 자산, 주식)
	public void printTrader() {
		System.out.println("=== 보유주식/자산 확인 ===");

		Trader t = dao.select(loginId);
		if (t == null) {
			System.out.println("정보 없음");
		} else {
			System.out.println("보유 주식 목록 : " + t.getList());
			System.out.println("보유 자산 : " + t.getTotal());
			System.out.println("예치금 :" + t.getCash());
			System.out.println("총수익: " + t.getProfit());
		}
	}

	// 정보 수정(pwd)
	public void editPwdTrader(Scanner sc) {
		System.out.println("=== 비밀번호 변경 ===");

		System.out.print("새로운 비밀번호 : ");
		String pwd = sc.next();

		int cnt = dao.update(new Trader(0, loginId, pwd));

		if (cnt > 0) {
			System.out.println("변경 완료");
		} else {
			System.out.println("변경 실패");
		}
	}

	// 모든 회원정보 열람 - 관리자
	public void printAll() {
		System.out.println("=== 회원 목록 ===");

		ArrayList<Trader> list = dao.selectAll();
		for (Trader t : list) {
			System.out.println(t);
		}
	}

	// 내 계좌 조회 -> 은행 테이블에서 계봐전호로 찾아 cash 등 읽어오기

	// 보유 주식

	// 보유 자산

	// 알림(보유 주식 중 5% 이상 변위만) -> 주식 목록 불러와 주식 테이블의 변화량중 5% 이상인 것 알려줌

	// 매수 : 매도된 목록 보여주고 골라서 사기) -> 거래 테이블 매도 목록 보여주기

	// 매도(거래 테이블에 내 주식이 현재가 기준으로 올라감)

}
