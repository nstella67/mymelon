package net.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

@Component
public class DBClose {
	// �����ͺ��̽� ���� �ڿ� �ݳ�

	public DBClose() {
		System.out.println("-----DBClose() ��ü ����");
	}

	public void close(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
		}
	}// end

	public void close(Connection con, PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
		}
	}// end

	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}

		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
		}
	}// end
}// class end
