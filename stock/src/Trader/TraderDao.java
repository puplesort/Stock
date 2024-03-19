package Trader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnect;

public class TraderDao {
	private DBConnect db;
	
	public TraderDao() {
		db = DBConnect.getInstance();
	}
	
	public void insert(Trader t) {
		Connection conn = db.conn();
		
		String sql = "INSERT INTO trader VALUES(seq_trader.nextval, ?, ?, ?, ? ,? ,?, ? ,? ,?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, t.getTrader_id());
			pstmt.setString(2, t.getPwd());
			pstmt.setInt(3, t.getCash());
			pstmt.setInt(4, t.getTotal());
			pstmt.setString(5, t.getAccount_number());
			pstmt.setString(6, t.getBank_name());
			pstmt.setString(7, t.getList());
			pstmt.setInt(8, t.getProfit());
			pstmt.setInt(9, t.getAuthority());
			
			int cnt = pstmt.executeUpdate();
			
			System.out.println(cnt + " 명 추가 완료");
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
	
	public int updateCash(Trader t) {
		Connection conn = db.conn();
		
		String sql = "UPDATE trader SET cash = ?,total =?,profit=?  WHERE trader_num=?";
		int cnt = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, t.getCash());
			pstmt.setInt(2,t.getTotal());
			pstmt.setInt(3,t.getProfit());
			pstmt.setInt(4, t.getTrader_num());
			
			cnt = pstmt.executeUpdate();
			System.out.println(cnt + "명 수정 완료");
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
		return cnt;
	}
	
	
	public int update(Trader t) {
		Connection conn = db.conn();
		
		String sql = "UPDATE trader SET pwd = ? WHERE trader_num=?";
		int cnt = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, t.getPwd());
			pstmt.setInt(2, t.getTrader_num());
			
			cnt = pstmt.executeUpdate();
			System.out.println(cnt + "명 수정 완료");
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
		return cnt;
	}
	
	public int updateList(Trader t) {
		Connection conn = db.conn();
		
		String sql = "UPDATE trader SET list = ? WHERE trader_num=?";
		int cnt = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, t.getList());
			pstmt.setInt(2, t.getTrader_num());
			
			cnt = pstmt.executeUpdate();
			System.out.println(cnt + "명 수정 완료");
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
		return cnt;
	}
	
	
	public int delete(int trader_num) {
		Connection conn = db.conn();
		
		String sql = "DELETE FROM trader WHERE trader_num = ?";
		int cnt = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, trader_num);
			cnt = pstmt.executeUpdate();
			
			System.out.println(cnt + "명 삭제 완료");
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
		return cnt;
	}
	
	public Trader selectByNum(int trader_num) {
		Connection conn = db.conn();
		
		String sql = "SELECT * FROM trader WHERE trader_num = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, trader_num);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println(rs.getInt(1));
				
				
				return new Trader(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10));
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
		return null;
	}
	
	
	public Trader select(String trader_id) {
		Connection conn = db.conn();
		
		String sql = "SELECT * FROM trader WHERE trader_id = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, trader_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return new Trader(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10));
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
		return null;
	}
	
	public ArrayList<Trader> selectAll() {
		Connection conn = db.conn();
		
		String sql = "SELECT * FROM trader WHERE authority = 0";
		
		ArrayList<Trader> list = new ArrayList<Trader>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new Trader(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10)));
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
		return list;
	}
}
