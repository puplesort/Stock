package Board;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import Trader.TraderService;

public class BoardService {
	private BoardDao dao;
	private TraderService tservice;
	private String login;
	public BoardService() {
		dao = new BoardDao();
		tservice=new TraderService();
	}
	public void login(String login) {
		this.login=login;
	}
	
	
	// 게시글 작성
	public void addBoard(Scanner sc) {
		System.out.println("=== 게시글 작성 ===");
		
		System.out.print("게시글 제목 : ");
		sc.nextLine();
		String title = sc.nextLine();
		System.out.print("게시글 내용");
		System.out.print("글 내용 입력 ( 완료시 /end)  :");
		String content="";
		while(!content.contains("/end")) {
		content = content+sc.nextLine()+"\n";
		if(content.length()>105) {
			System.out.println("too long text!");
			content=content.substring(0,105);
		}
		}
		content=content.substring(0,content.length()-5 );		
		System.out.println("회사 이름 : ");
		String company_name = sc.nextLine();
		
		dao.insert(new Board(0, login, null, title, content, company_name));
	}
	
	// ID로 검색
	public void getByWriter(Scanner sc) {
		System.out.println("=== 게시글 검색(ID) ===");
		
		System.out.print("작성자 id : ");
		String writer = sc.next();
		Board b = dao.select(writer);
		
		if(b == null) {
			System.out.println(writer + " 님이 작성한 게시물이 없습니다");
		} else {
			System.out.println(b);
			
			if(login.equals(b.getWriter()) || tservice.getAuthority() == 1) {
				System.out.print("1.수정 2.삭제 3.상세페이지 종료 : ");
				int x = sc.nextInt();
				
				switch(x) {
				case 1:
					editBoard(sc, writer);
					break;
				case 2:
					delBoard(writer);
					break;
				}
			}
		}
	}
	
	// 제목으로 검색
	public void getByTitle(Scanner sc) {
		System.out.println("=== 게시글 제목 검색 ===");
		
		System.out.print("게시글 제목 : ");
		String title = sc.next();
		ArrayList<Board> list = dao.selectByTitle(title);
		
		System.out.println("--- 검색 결과 ---");
		if(list.isEmpty()) {
			System.out.println("검색된 결과가 없습니다.");
		} else {
			for(Board b : list) {
				System.out.println(b);
			}
		}
	}
	
	// 게시글 회사이름으로 검색
	public void getByCompanyName(Scanner sc) {
		System.out.println("=== 게시글 회사 이름으로 검색 ===");
		
		System.out.println("검색할 회사 이름 : ");
		sc.nextLine();
		String company_name = sc.nextLine();
		ArrayList<Board> list = dao.selectByCompanyName(company_name);
		
		System.out.println("--- 검색 결과 ---");
		
		if(list.isEmpty()) {
			System.out.println(company_name + " 의 관한 게시글이 없습니다.");
		} else {
			for(Board b : list) {
				System.out.println(b);
			}
		}
	}
	
	// 게시글 전체 목록
	public void getAll() {
		System.out.println("=== 게시글 전체 목록 ===");
		
		ArrayList<Board> list = dao.selectAll();
		
		if(list.isEmpty()) {
			System.out.println("작성된 게시글이 없습니다.");
		} else {
			for(Board b : list) {
				System.out.println(b);
			}
		}	
	}
	
	// 수정 -> 번호로 검색
	public void editBoard(Scanner sc, String writer) {
		System.out.println("=== 게시글 수정 ===");
		
		System.out.print("새 제목 : ");
		sc.nextLine();
		String title = sc.nextLine();
		System.out.print("새 내용 : ");
		String content = sc.nextLine();
		System.out.print("새 회사이름 : ");
		String company_name = sc.nextLine();
		
		dao.update(new Board(0, writer, null, title, content, company_name));
	}
	
	// 삭제 -> 번호로 검색
	public void delBoard(String writer) {
		System.out.println("=== 게시글 삭제 ===");
		dao.delete(writer);
	}
	
}
