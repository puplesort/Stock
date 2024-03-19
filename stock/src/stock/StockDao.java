package stock;

import java.sql.*;
import java.util.ArrayList;

import DBConnect.DBConnection;




public class StockDao {
	private DBConnection db;
	public StockDao() {
		db = DBConnection.getInstance();
	}
	public void insert(Stock s) {
		Connection conn = db.conn();
		String sql = "insert into stock values((select MAX(company_id) from company),?,?,?)";
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
	public int delete(int num) {
		Connection conn = db.conn();
		String sql = "delete stock where stock_id = ?";
		int cnt = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
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
		String sql = "select * from stock where price_change >= 5";
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
		String sql = "select * from stock where price_change <= -5";
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