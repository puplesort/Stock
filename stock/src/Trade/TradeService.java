package Trade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Trader.Trader;
import Trader.TraderDao;
import company.Company;
import company.CompanyDao;
import stock.Stock;
import stock.StockDao;
import stock.StockService;
// Trade Service
public class TradeService {
	private TradeDao dao;
	private StockDao stdao;
	private int login;
	private TraderDao tdao;
	private CompanyDao cdao;

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public TradeService() {
		dao = new TradeDao();
		stdao = new StockDao();
		tdao = new TraderDao();
		cdao = new CompanyDao();

	}

	public void login(int login) {
		this.login = login;
	}

	public void logout() {
		this.login = 0;
	}

	public void addSellAdimin(int stockId) {
		Stock s = stdao.findByNum(stockId);
		int sell = 100;
		dao.insert(new Trade(0, stockId, sell, 0, 1, s.getTotal(), new Date(0)));
		System.out.println("매도 등록 완료");
	}

	public void addSell(Scanner sc) {
		System.out.println("---------매도--------");
		Trader target = tdao.selectByNum(login);
		String list = target.getList();
		printAllMyBuy();
		if (list.length() == 0) {
			System.out.println("주식을 먼저 매수 하시오");
			return;
		}
		System.out.println(list);
		System.out.println("매도할 주식의 id를 입력하시오");
		int stockId = sc.nextInt();
		if (!list.contains(stockId + "/")) {
			System.out.println("해당 주식은 당신이 가지고 있지 않습니다.");
			return;
		}
		System.out.println("1. 매도    2.취소");
		int cmd = sc.nextInt();
		if (cmd == 2) {
			System.out.println("매도 취소");
			return;
		}
		ArrayList<Trade> t = dao.selectByStockId(stockId);
		Trade trade = null;
		for (Trade i : t) {
			if (i.getTraderNum() == login) {
				trade = i;
			}
		}
		System.out.println("매도할 주식의 양를 입력하시오");
		int sell = sc.nextInt();
		// 보유 주식의 양과 판매할 양 비교 ,, price 주식 테이블에서 가져옴
		if (trade.getBuy() < sell) {
			System.out.println("보유양보다 많은 양입니다");
			return;
		}
		trade.setBuy(trade.getBuy() - sell);
		dao.update(trade);
		if (trade.getBuy() == 0) {
			dao.delete(trade);
		}
		dao.insert(new Trade(0, stockId, sell, 0, login, trade.getPrice(), new Date(0)));
		System.out.println("매도 등록 완료");
	}

	public void addBuy(Scanner sc) {
		Trader buyer = tdao.selectByNum(login);
		System.out.println("---------매수--------");
		printByStockSell(sc);
		System.out.println("매수할 거래의 id를 입력하시오");
		int TradeId = sc.nextInt();
		Trade t = dao.selectById(TradeId);
		if (t == null) {
			System.out.println("해당 거래가 존재하지 않음!");
			return;
		}
		if (t.getSell() <= 0) {
			System.out.print("매도 물량이 없음!");
			return;
		}
		System.out.println("거래 id: " + t.getTradeId() + "를 선택 하셨습니다.");
		System.out.println("한 주에" + t.getPrice() + "원" + "\t  총" + t.getSell() + "주");
		System.out.println("1: 매수   2: 취소");
		int cmd = sc.nextInt();
		switch (cmd) {
		case 1: {
			System.out.println("매수할 주식의 양를 입력하시오");
			System.out.println("현재 예치금: " + buyer.getCash());
			int buy = sc.nextInt();
			if (t.getSell() < buy) {
				System.out.println("매수할 양이 매도할 양 보다 많음!");
				return;
			}
			Trader seller = tdao.selectByNum(dao.selectById(TradeId).getTraderNum());
			int cash = buy * t.getPrice();
			if (buyer.getCash() < cash) {
				System.out.println("예치금이 부족함!");
				return;
			}
			Stock s = stdao.findByNum(t.getStockId());
			Company c = cdao.findByName(s.getStock_name());
			c.setVolume(c.getVolume() + buy);
			cdao.updateVol(c);
			if (t.getSell() == buy) {
				dao.delete(t);
				seller.setCash(seller.getCash() + cash);
				String stocklist = seller.getList().replace(s.getStock_name() + " : " + s.getStock_id() + "/", "/");
				if (stocklist.indexOf("/") == 0) {
					stocklist = stocklist.substring(1, stocklist.length());
				}
				seller.setList(stocklist);
				if (buyer.getList()== null || !(buyer.getList().contains(s.getStock_name()))) {
					buyer.setList(buyer.getList() + s.getStock_name() + " : " + s.getStock_id() + "/");
					dao.insert(new Trade(0, t.getStockId(), 0, buy, login, t.getPrice(), new Date(0)));
				} else {
					for (Trade T : dao.selectByTraderNum(buyer.getTrader_num())) {
						if (T.getStockId() == s.getStock_id() && T.getSell()==0) {
							T.setBuy(T.getBuy() + buy);
							dao.updateBuy(T);
						}
					}
				}
				buyer.setCash(buyer.getCash() - cash);
				buyer.setTotal(buyer.getTotal() + (s.getTotal() * buy));
				tdao.updateCash(buyer);
				tdao.updateList(buyer);
				seller.setCash(seller.getCash() + cash);
				seller.setTotal(seller.getTotal() - (s.getTotal() * buy));
				tdao.updateList(seller);
				tdao.updateCash(seller);
				System.out.println("매수완료");
				return;
			}
			if (buyer.getList()==null || !buyer.getList().contains(s.getStock_name())) {
				buyer.setList(buyer.getList() + s.getStock_name() + " : " + s.getStock_id() + "/");
				dao.insert(new Trade(0, t.getStockId(), 0, buy, login, t.getPrice(), new Date(0)));
			}
			else {
				for (Trade T : dao.selectByTraderNum(buyer.getTrader_num())) {
					if (T.getStockId() == s.getStock_id() && T.getSell()==0) {
						T.setBuy(T.getBuy() + buy);
						dao.updateBuy(T);
					}
				}
			}
			buyer.setCash(buyer.getCash() - cash);
			buyer.setTotal(buyer.getTotal() + s.getTotal() * buy);
			t.setSell(t.getSell() - buy);
			tdao.updateCash(buyer);
			tdao.updateList(buyer);
			dao.update(t);
			seller.setCash(seller.getCash() + cash);
			seller.setTotal(seller.getTotal() - s.getTotal() * buy);
			tdao.updateList(seller);
			tdao.updateCash(seller);
			System.out.println("매수완료");
		}
		default:
			return;
		}
	}

	public void printByStockSell(Scanner sc) {
		System.out.println("------주식별 목록-------");
		for (Stock stock : stdao.findAll()) {
			System.out.println(stock);
		}
		System.out.println("찾을 주식 id를 입력하시오");
		int stockId = sc.nextInt();
		ArrayList<Trade> list = dao.selectByStockId(stockId);
		for (Trade t : list) {
			if (t.getSell() > 0) {
				System.out.println(t);
			}
		}
	}

	public void printByStock(Scanner sc) {
		System.out.println("------주식별 목록-------");
		for (Stock stock : stdao.findAll()) {
			System.out.println(stock);
		}
		System.out.println("찾을 주식 id를 입력하시오");
		int stockId = sc.nextInt();
		ArrayList<Trade> list = dao.selectByStockId(stockId);
		for (Trade t : list) {
			System.out.println(t);
		}
	}

	public void printAllBuy() {
		System.out.println("---------전체 매수 목록--------");
		ArrayList<Trade> t = dao.selectAll();
		for (Trade i : t) {
			if (i.getBuy() > 0)
				System.out.println(i.getTradeId() + " " + i.getStockId() + " " + i.getBuy() + " " + i.getPrice() + " "
						+ i.getTradeDate());
		}
	}

	public void printAllMyBuy() {
		System.out.println("---------나의 매수 목록--------");
		ArrayList<Trade> t = dao.selectAll();
		for (Trade i : t) {
			if (i.getBuy() > 0 && i.getTraderNum() == login)
				System.out.println(i.getTradeId() + " " + i.getStockId() + " " + i.getBuy() + " " + i.getPrice() + " "
						+ i.getTradeDate());
		}
	}

	public void printAllMySell() {
		System.out.println("---------나의 매도 목록--------");
		ArrayList<Trade> t = dao.selectAll();
		for (Trade i : t) {
			if (i.getSell() > 0 && i.getTradeId() == login)
				System.out.println(i.getTradeId() + " " + i.getStockId() + " " + i.getSell() + " " + i.getPrice() + " "
						+ i.getTradeDate());
		}
	}

	public void printAllSell() {
		System.out.println("---------전체 매도 목록--------");
		ArrayList<Trade> t = dao.selectAll();
		for (Trade i : t) {
			if (i.getSell() > 0)
				System.out.println(i.getTradeId() + " " + i.getStockId() + " " + i.getSell() + " " + i.getPrice() + " "
						+ i.getTradeDate());
		}
	}

	public void updatePrice() {
		ArrayList<Stock> list = stdao.findAll();
		Random r = new Random();
		double i = 0;
		double j = 0;
		for (Stock s : list) {
			i = r.nextDouble(0.1);
			j = r.nextDouble(0.1);
			dao.updatePrice(s.getStock_id(), i - j);
			s.setPrice_number(i - j);
			int total = s.getTotal();
			s.setTotal(s.getTotal() + (int) (s.getTotal() * (i - j)));
			updateTotal(s, total);
			stdao.update(s);
		}
	}

	public void updateTotal(Stock s, int total) {
		ArrayList<Trader> list = tdao.selectAll();
		System.out.println(total);
		for (Trader t : list) {
			if (t.getList().contains(s.getStock_name())) {

				t.setProfit(t.getProfit() + s.getTotal() - total);
				t.setTotal(t.getTotal() + s.getTotal() - total);
				tdao.updateCash(t);
			}
		}

	}

}
