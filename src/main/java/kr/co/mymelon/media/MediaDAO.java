package kr.co.mymelon.media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.utility.DBClose;
import net.utility.DBOpen;

@Component
public class MediaDAO {
	@Autowired
	DBOpen dbopen;
	
	@Autowired
	DBClose dbclose;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql = null;
	ArrayList<MediaDTO> list = null;
	  
	public MediaDAO() {
		System.out.println("----- MediaDAO()객체 생성됨...");
	}
	
    public ArrayList<MediaDTO> list(MediaDTO dto){
	    try{
	      con = dbopen.getConnection();
	      sql = new StringBuffer();
	      sql.append(" SELECT mediano, title, rdate, poster, filename, filesize, mview, mediagroupno");
	      sql.append(" FROM media");
	      sql.append(" WHERE mview='Y' AND mediagroupno = ?");
	      sql.append(" ORDER BY mediano DESC");
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, dto.getMediagroupno());
	      rs = pstmt.executeQuery();
	      if(rs.next()){
	        list = new ArrayList<MediaDTO>();
	        do {
	          dto = new MediaDTO();
	          dto.setMediano(rs.getInt("mediano"));
	          dto.setTitle(rs.getString("title"));
	          dto.setRdate(rs.getString("rdate"));
	          dto.setPoster(rs.getString("poster"));
	          dto.setFilename(rs.getString("filename"));
	          dto.setFilesize(rs.getInt("filesize"));
	          dto.setMview(rs.getString("mview"));
	          dto.setMediagroupno(rs.getInt("mediagroupno"));
	          list.add(dto);
	        } while(rs.next());
	      }else{
	        list = null;
	      }//if end

	    }catch(Exception e){
	      System.out.println(e.toString());
	    }finally{
	      dbclose.close(con, pstmt, rs);
	    }
	    return list;
    }//list() end	
    
    public int create(MediaDTO dto) {
        int cnt = 0;
        try {
          con = dbopen.getConnection();
          sql = new StringBuffer();
          sql.append(" INSERT INTO media(mediano, title, rdate, poster, filename, filesize, mediagroupno)");
          sql.append(" VALUES((SELECT NVL(MAX(mediano), 0)+1 as mediano FROM media), ?, sysdate, ?, ?, ?, ?)"); 
          pstmt = con.prepareStatement(sql.toString());
          pstmt.setString(1, dto.getTitle());
          pstmt.setString(2, dto.getPoster());
          pstmt.setString(3, dto.getFilename());
          pstmt.setLong(4, dto.getFilesize());
          pstmt.setInt(5, dto.getMediagroupno());
          cnt = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbclose.close(con, pstmt, rs);
        }
        return cnt;
    }//create() end    
    
    
    public MediaDTO read(int mediano) {
        MediaDTO dto = null;
        try {
          con = dbopen.getConnection();
          sql = new StringBuffer();
          sql.append(" SELECT mediano, title, rdate, poster, filename, filesize, mview, mediagroupno");
          sql.append(" FROM media");
          sql.append(" WHERE mediano = ?"); 
          pstmt = con.prepareStatement(sql.toString());
          pstmt.setInt(1, mediano);
          rs = pstmt.executeQuery();
          if(rs.next()) {
            dto = new MediaDTO();
            dto.setMediano(rs.getInt("mediano"));
            dto.setTitle(rs.getString("title"));
            dto.setRdate(rs.getString("rdate"));
            dto.setPoster(rs.getString("poster"));
            dto.setFilename(rs.getString("filename"));
            dto.setFilesize(rs.getLong("filesize"));
            dto.setMview(rs.getString("mview"));
            dto.setMediagroupno(rs.getInt("mediagroupno"));
          }//if end

        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          dbclose.close(con, pstmt, rs);
        }
        return dto;
      }//read() end
    
    
      public int delete(int mediano) {
        int cnt = 0;
        try {
          con = dbopen.getConnection();
          sql = new StringBuffer();
          sql.append(" DELETE FROM media");
          sql.append(" WHERE mediano=?");  
          pstmt = con.prepareStatement(sql.toString());
          pstmt.setInt(1, mediano);
          cnt = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbclose.close(con, pstmt);
        }
        return cnt;
      }//delete() end

      public int update(MediaDTO dto) {
        int cnt = 0; 

        try {
          con = dbopen.getConnection();
          sql = new StringBuffer();
          sql.append(" UPDATE media");
          sql.append(" SET title=?, poster=?, filename=?, filesize=?");
          sql.append(" WHERE mediano=?"); 
          pstmt = con.prepareStatement(sql.toString());
          pstmt.setString(1, dto.getTitle());
          pstmt.setString(2, dto.getPoster());
          pstmt.setString(3, dto.getFilename());
          pstmt.setLong(4, dto.getFilesize());
          pstmt.setInt(5, dto.getMediano());
          cnt = pstmt.executeUpdate();

        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          dbclose.close(con, pstmt, rs);
        }
        return cnt;
      }//update() end
    
}//class end
