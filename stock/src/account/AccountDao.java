package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnect;
import Trade.Trade;

public class AccountDao {
	private DBConnect db;
	// Account DAO  
	public AccountDao() { 
		db = DBConnect.getInstance();
	}
	public void insert(Account a) {
		Connection conn = db.conn();
		String sql = "insert into Account values(?,?,?,?)";
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setString(1, a.getAccountNumber());
			ptsmt.setInt(2, a.getBankId());
			ptsmt.setInt(3, a.getCash());
			ptsmt.setString(4, a.getBankName());
			int i = ptsmt.executeUpdate();
			if (i < 1) {
				System.out.println("실행 실패!");
			}else {
				System.out.println(i+"줄 실행");
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
	
	public void update(Account a) {
		Connection conn = db.conn();
		String sql = "update Account set cash=? where account_number=?";
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setInt(1, a.getCash());
			ptsmt.setString(2, a.getAccountNumber());
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
	public void delete(Account a) {
		Connection conn = db.conn();
		String sql = "delete Account where account_number=?";
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setString(1, a.getAccountNumber());
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
	
	public Account selectByAcNum(String num) {
		Connection conn = db.conn();
		String sql = "select * from account where account_number=?";
		Account a = null;
		try {
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			ptsmt.setString(1,num);
			ResultSet rs = ptsmt.executeQuery();
			if (rs.next()) {
				a = new Account(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
			}
			if (a == null) {
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
		return a;
	}
	
	
	
}
