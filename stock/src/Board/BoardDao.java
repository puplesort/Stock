package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnect;

public class BoardDao {
	private DBConnect db;
	public BoardDao() {
		db = DBConnect.getInstance();
	}

	public void insert(Board b) {
		Connection conn = db.conn();

		String sql = "INSERT INTO board VALUES(seq_board.nextval, ?, sysdate, ?, ?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, b.getWriter());
			pstmt.setString(2, b.getTitle());
			pstmt.setString(3, b.getContent());
			pstmt.setString(4, b.getCompany_name());

			int cnt = pstmt.executeUpdate();

			System.out.println(cnt + " 개의 게시글 작성 완료");
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

	public Board select(String writer) {
		Connection conn = db.conn();

		String sql = "SELECT * FROM board WHERE writer = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Board(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6));
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

	public ArrayList<Board> selectByTitle(String title) {
		Connection conn = db.conn();

		String sql = "SELECT * FROM board WHERE title LIKE ? ORDER BY num";
		ArrayList<Board> list = new ArrayList<Board>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Board(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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
	
	public ArrayList<Board> selectByCompanyName(String company_name) {
		Connection conn = db.conn();
		
		String sql = "SELECT * FROM board WHERE company_name LIKE ? ORDER BY num";
		ArrayList<Board> list = new ArrayList<Board>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + company_name + "%");
			
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				list.add(new Board(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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

	public ArrayList<Board> selectAll() {
		Connection conn = db.conn();

		String sql = "SELECT * FROM board ORDER BY num";

		ArrayList<Board> list = new ArrayList<Board>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new Board(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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

	public int update(Board b) {
		Connection conn = db.conn();

		String sql = "UPDATE board SET title = ?, content = ? WHERE writer = ?";
		int cnt = 0;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getContent());
			pstmt.setString(3, b.getWriter());

			cnt = pstmt.executeUpdate();
			System.out.println(cnt + " 개의 게시글 수정 완료");
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

	public int delete(String writer) {
		Connection conn = db.conn();

		String sql = "DELETE FROM board WHERE writer = ?";
		int cnt = 0;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, writer);
			cnt = pstmt.executeUpdate();

			System.out.println(cnt + " 개의 게시글 삭제 완료");
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

}
