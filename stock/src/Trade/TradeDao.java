package Trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnect;

public class TradeDao {

	private DBConnect db;

	public TradeDao() {
		db = DBConnect.getInstance();
	}

	public void insert(Trade t) {
		Connection conn = db.conn();
		String sql = "insert into trade values(seq_trade.nextval,?,?,?,?,?,sysdate)";
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, t.getStockId());
			ptsmt.setInt(2, t.getSell());
			ptsmt.setInt(3, t.getBuy());
			ptsmt.setInt(4, t.getTraderNum());
			ptsmt.setInt(5, t.getPrice());
			int i = ptsmt.executeUpdate();
			if (i < 1) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateBuy(Trade t) {
		Connection conn = db.conn();
		String sql = "update trade set buy=? where trade_id=?";
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, t.getBuy());
			ptsmt.setInt(2, t.getTradeId());
			int i = ptsmt.executeUpdate();
			if (i < 1) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	public void update(Trade t) {
		Connection conn = db.conn();
		String sql = "update trade set sell=? where trade_id=?";
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, t.getSell());
			ptsmt.setInt(2, t.getTradeId());
			int i = ptsmt.executeUpdate();
			if (i < 1) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void updatePrice(int StockId, double priceChange) {
		Connection conn = db.conn();
		String sql = "update trade set price=price+price*? where stock_id=?";
		System.out.println(priceChange);
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setDouble(1, priceChange);
			ptsmt.setInt(2, StockId);
			int i = ptsmt.executeUpdate();
			System.out.println("실행 완료!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void delete(Trade t) {
		Connection conn = db.conn();
		String sql = "delete trade where trade_id=?";
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, t.getTradeId());
			int i = ptsmt.executeUpdate();
			if (i < 1) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Trade selectById(int TradeId) {
		Connection conn = db.conn();
		String sql = "select * from trade where trade_id=?";
		Trade t = null;
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, TradeId);
			ResultSet rs = ptsmt.executeQuery();
			if (rs.next()) {
				t = new Trade(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
						rs.getDate(7));
			}
			if (t == null) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	public ArrayList<Trade> selectByStockId(int StockId) {
		Connection conn = db.conn();
		String sql = "select * from trade where Stock_id=? order by trade_id";
		ArrayList<Trade> t = new ArrayList<>();
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, StockId);
			ResultSet rs = ptsmt.executeQuery();
			while (rs.next()) {
				t.add(new Trade(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
						rs.getDate(7)));
			}
			if (t.size() < 1) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	public ArrayList<Trade> selectByTraderNum(int TraderNum) {
		Connection conn = db.conn();
		String sql = "select * from trade where trader_num=? order by trade_id";
		ArrayList<Trade> t = new ArrayList<>();
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, TraderNum);
			ResultSet rs = ptsmt.executeQuery();
			while (rs.next()) {
				t.add(new Trade(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
						rs.getDate(7)));
			}
			if (t.size() < 1) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	public ArrayList<Trade> selectAll(){
		Connection conn = db.conn();
		String sql = "select * from trade order by trade_id";
		ArrayList<Trade> t = new ArrayList<>();
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ResultSet rs = ptsmt.executeQuery();
			while (rs.next()) {
				t.add(new Trade(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
						rs.getDate(7)));
			}
			if (t.size() < 1) {
				System.out.println("실행 실패!");
			} else {
				System.out.println("실행 완료!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}
}
	
	
