package kr.co.mymelon.mediagroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.utility.DBClose;
import net.utility.DBOpen;
import net.utility.Utility;

@Component
public class MediagroupDAO {

	@Autowired // ← @Component 어노테이션으로 자동생성된 객체를 사용하려면 객체간 서로 연결이 되어야 한다
	private DBOpen dbopen;

	@Autowired
	private DBClose dbclose;

	Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<MediagroupDTO> list = null;

	public MediagroupDAO() {
		System.out.println("-----MediagroupDAO() 객체 생성");
	}

	public int create(MediagroupDTO dto) { // 미디어 그룹 등록
		int cnt = 0;
		try {
			con = dbopen.getConnection(); // DB연결
			sql = new StringBuffer();
			sql.append(" INSERT INTO  mediagroup(mediagroupno, title)");
			sql.append(" VALUES((SELECT nvl(max(mediagroupno),0)+1 FROM mediagroup), ?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getTitle());
			cnt = pstmt.executeUpdate();
			// System.out.println(res);
		} catch (Exception e) {
			System.out.println("행추가실패 : " + e);
		} finally {
			dbclose.close(con, pstmt);
		} // try end
		return cnt;
	}// create() end//////////////////////////////////////////////////////////////////

	public ArrayList<MediagroupDTO> list() {
		ArrayList<MediagroupDTO> list = null;
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" SELECT mediagroupno, title");
			sql.append(" FROM mediagroup");
			sql.append(" ORDER BY mediagroupno DESC");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<MediagroupDTO>();
				do {
					MediagroupDTO dto = null;
					dto = new MediagroupDTO();
					dto.setMediagroupno(rs.getInt("mediagroupno"));
					dto.setTitle(rs.getString("title"));
					list.add(dto);
				} while (rs.next());
			} // if end
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return list;
	}//list() end//////////////////////////////////////////////////////////////////
	
	public int delete(MediagroupDTO dto) {
		int cnt=0;
		try {
			con=dbopen.getConnection();	//DB연결
				
			sql = new StringBuffer();
			sql.append(" DELETE FROM mediagroup");
			sql.append(" WHERE mediagroupno=?");
				
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, dto.getMediagroupno());
				
			cnt=pstmt.executeUpdate();
				
		}catch(Exception e) {
			System.out.println("삭제실패 : "+e);
		}finally {
			dbclose.close(con, pstmt);
		}//try end
		return cnt;
	}//delete() end//////////////////////////////////////////////////////////////////
	
	public MediagroupDTO updateForm(MediagroupDTO dto) {
		try {
			con = dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" SELECT title");
			sql.append(" FROM mediagroup");
			sql.append(" WHERE mediagroupno=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, dto.getMediagroupno());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setTitle(rs.getString("title"));
			} // if end
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			dbclose.close(con, pstmt, rs);
		}
		return dto;
	}//updateForm() end//////////////////////////////////////////////////////////////////
	
	public int update(MediagroupDTO dto) {
		int cnt=0;		
		try {
			con=dbopen.getConnection();
			sql = new StringBuffer();
			sql.append(" UPDATE mediagroup");
			sql.append(" SET title=?");
			sql.append(" WHERE mediagroupno=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getTitle());
			pstmt.setInt(2, dto.getMediagroupno());
			cnt=pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("수정실패 : "+e);
		}finally {
			dbclose.close(con, pstmt);
		}//try end
		return cnt;
	}//update() end//////////////////////////////////////////////////////////////////
	

}// class end
