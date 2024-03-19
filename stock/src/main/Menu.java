package main;

import java.util.Scanner;

import Board.BoardService;
import Trade.TradeService;
import Trader.TraderService;
import account.AccountService;
import company.CompanyService;
import stock.StockService;

public class Menu {
	private TraderService tservice;
	private BoardService bservice;
	private AccountService aservice;
	private TradeService tradeservice;
	private CompanyService cservice;
	private StockService stservice;
	
	public Menu() {
		tservice = new TraderService();
		bservice = new BoardService();
		aservice = new AccountService();
		tradeservice = new TradeService();
		cservice =new CompanyService();
		stservice=new StockService();
	}
	
	public void run(Scanner sc) {
		while(true) {
			System.out.print("계속하려면 아무 번호나 눌러주세요 (종료는 0) : ");
			
			int m = sc.nextInt();
			if(m == 0) {
				tradeservice.updatePrice();
				break;
			}
			
			// 제일 먼저 계좌 개설을 하는 단계를 거쳐야함
			// 어떤 사람이든 계좌를 개설해야 회원가입을 하는 상황에서 계좌번호와 은행명을 입력할 수 있음
			// account, trader 테이블 수정
			// board 테이블 회사이름 컬럼 추가 후 company랑 비교해서 참조할지 말지 결정
			
			if(tservice.getLoginId() == null) {
				runTraderLogout(sc);
			} else {
				if(tservice.getAuthority() == 1) {
					runMasterLogin(sc);
				} else {
					runTraderLogin(sc);
				}
			}
		}
	}
	
	// 로그인 X
	public void runTraderLogout(Scanner sc) {
		boolean flag = true;
		
		while(flag) {
			System.out.println("1.계좌개설 2.로그인 3.회원가입 4.종료");
			System.out.print("선택 : ");
			int m = sc.nextInt();
			
			switch(m) {
			case 1:
				aservice.addAccount(sc);
				break;
			case 2:
				flag = !tservice.login(sc);
				tradeservice.login(tservice.getLoginnum());
				bservice.login(tservice.getLoginId());
				break;
			case 3: 
				tservice.addTrader(sc);
				break;
			case 4:
				flag = false;
				break;
			}
		}
	}
	
	// 로그인 -> 회원 메뉴(authority = 0)
	public void runTraderLogin(Scanner sc) {
		
		// 알림 추기 위치 -> 로그인 했을 때 모든 주식 상승률/하락률 출력
		stservice.up();
		stservice.down();
		boolean flag = true;
		while(flag) {
			System.out.println("1.내정보 확인(보유 주식/자산) 2.내계좌 관리 3.내정보 수정 4.매도 5.매수 6.게시판 7.로그아웃 8.탈퇴");
			// 1. 내정보 확인 메뉴 -> 보유 주식 목록, 보유 자산 (한번에 출력)
			// 2. 내계좌 조회 -> 1. 잔액조회 2.입금 3.출금 4.계좌 삭제
			
			// 3. 매도 -> 내 주식목록 -> 매도할 주식 id 선택
			// 4. 매수 -> 1. 매도 목록 (1. 매수할 주식(거래 trade_id) 2.뒤로가기)
			System.out.print(": ");
			int m = sc.nextInt();
			
			switch(m) {
			case 1:
				tservice.printTrader();
				break;
			case 2:
				aservice=new AccountService(tservice.getDao().select(tservice.getLoginId()).getAccount_number(),tservice.getDao());
				runAccount(sc);
				break;
			case 3:
				tservice.editPwdTrader(sc);
				break;
			case 4:
				tradeservice.addSell(sc);
				break;
			case 5:
				tradeservice.addBuy(sc);
				break;
			case 6:
				runBoard(sc);
				break;
			case 7:
				tservice.logout();
				tradeservice.logout();
				return;
			case 8:
				tservice.delTrader(sc);
				return;		
			}
		}
	}
	
	// 로그인 -> 관리자 메뉴(authority = 1)
	public void runMasterLogin(Scanner sc) {
		boolean flag = true;
		
		while(flag) {
			System.out.println("1.회사관리 2.모든 회원정보열람 3.게시글 관리 4.로그아웃");
			// (완료)게시글 관리 -> 1.게시글 목록 2.게시글 제목으로 검색(수정, 삭제) 3.상세페이지 종료
			System.out.print(": ");
			int m = sc.nextInt();
			
			switch(m) {
			case 1:
				// 회사 관리 -> 1.회사등록(주식 자동생성), 2.회사수정(회사 정보수정)
				runCompany(sc);
				break;
			case 2:
				tservice.printAll();
				break;
			case 3:
				runMasterBoard(sc);
				break;
			case 4:
				tservice.logout();
				return;
			}
		}
	}
	
	// 게시판 메뉴 - 회원
	public void runBoard(Scanner sc) {
		boolean flag = true;
		while(flag) {
			System.out.println("1.게시물 작성 2.ID로 검색 3.제목으로 검색 4. 회사명으로 검색 5.전체목록 6.게시판 종료");
			System.out.print(": ");
			int m = sc.nextInt();
			
			switch(m) {
			case 1:
				bservice.addBoard(sc);
				break;
			case 2:
				bservice.getByWriter(sc);
				break;
			case 3:
				bservice.getByTitle(sc);
			case 4:
				bservice.getByCompanyName(sc);
				break;
			case 5:
				bservice.getAll();
				break;
			case 6:
				flag = false;
				break;
			}
		}
	}
	
	// 게시글 관리 - 관리자용 
	public void runMasterBoard(Scanner sc) {
		boolean flag = true;
		
		while(flag) {
			System.out.println("1.게시글 목록 2.게시글 검색(ID) 3.게시글 관리 종료");
			System.out.print(": ");
			int m = sc.nextInt();
			
			switch(m) {
			case 1:
				bservice.getAll();
				break;
			case 2:
				bservice.getByTitle(sc);
				break;
			case 3:
				flag = false;
				break;
			}
		}
	}
	
	public void runCompany(Scanner sc) {
		boolean flag = true;
		
		while(flag) {
			System.out.println("1.회사등록(주식 자동생성) , 2.회사정보수정 3.회사관리 종료");
			System.out.print(": ");
			int m = sc.nextInt();
			
			switch(m) {
			case 1:
				cservice.addCom(sc);;
				break;
			case 2:
				cservice.editCom(sc);
				break;
			case 3:
				flag = false;
				break;
			}
		}
	}
	
	//계좌관리
	public void runAccount(Scanner sc) {
		boolean flag = true;

		while(flag) {
			System.out.println(" 1.잔액조회 2.입금 3.출금 4.계좌삭제 5.계좌관리 종료");
			System.out.print(": ");
			int m = sc.nextInt();
			
			switch (m) {
			case 1: 
				aservice.selecByAcNum();
				break;
			case 2:	
				aservice.deposit(sc);
				break;
			case 3:
				aservice.withdrawal(sc);
				break;
			case 4:
				aservice.delete();
				break;
			case 5:
				flag = false;
				break;
			}
			
		}
	}
}
