package stock;

import java.sql.*;
import java.util.ArrayList;

import DBConnect.DBConnect;
//



public class StockDao {
	private DBConnect db;
	public StockDao() {
		db = DBConnect.getInstance();
	}
	public void insert(Stock s) {
		Connection conn = db.conn();
		String sql = "insert into stock values(seq_stock.nextval,?,?,?)";
		int cnt = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getStock_name());
			ps.setInt(2, s.getTotal());
			ps.setDouble(3, s.getPrice_change());
			cnt = ps.executeUpdate();
			System.out.println(cnt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void update(Stock s) {
		Connection conn = db.conn();
		String sql = "update stock set total = ?, price_change = ? where stock_id = ?";
		int cnt = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, s.getTotal());
			ps.setDouble(2, s.getPrice_change());
			ps.setInt(3, s.getStock_id());
			cnt = ps.executeUpdate();
			System.out.println(cnt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public int delete(String name) {
		Connection conn = db.conn();
		String sql = "delete stock where stock_name = ?";
		int cnt = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cnt;
	}
	public Stock findByNum(int num) {
		Connection conn = db.conn();
		String sql = "select * from stock where stock_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new Stock(rs. getInt(1),rs.getString(2),rs.getInt(3),rs.getDouble(4));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public ArrayList<Stock> findAll(){
		Connection conn = db.conn();
		String sql = "select * from stock";
		ArrayList<Stock> list = new ArrayList<Stock>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Stock(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getDouble(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public Stock findByname(String name){
		Connection conn = db.conn();
		String sql = "select * from stock where stock_name like ?";
		Stock s = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				s=new Stock(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getDouble(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s;
	}
	
	
	public ArrayList<Stock> findByName(String name){
		Connection conn = db.conn();
		String sql = "select * from stock where stock_id like ?";
		ArrayList<Stock> list = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Stock(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getDouble(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public ArrayList<Stock> findByPrice_Change() {
		Connection conn = db.conn();
		String sql = "select * from stock where price_change >= 0.05";
		ArrayList<Stock> list = new ArrayList<Stock>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Stock(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getDouble(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public ArrayList<Stock> findByPrice_ChangeD() {
		Connection conn = db.conn();
		String sql = "select * from stock where price_change <= -0.05";
		ArrayList<Stock> list = new ArrayList<Stock>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Stock(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getDouble(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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

































