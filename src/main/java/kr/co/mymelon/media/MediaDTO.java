package kr.co.mymelon.media;

import org.springframework.web.multipart.MultipartFile;

public class MediaDTO {
    private int mediano;
    private String title;
    private String poster;
    private String filename;
    private long filesize;
    private String mview;
    private String rdate;
    private int mediagroupno;    
//----------------------------------------------    
    //스프링 파일 객체 멤버변수 
    
    //<input type='file' name='posterMF'> 
    private MultipartFile posterMF;
    
    //<input type='file' name='filenameMF'>
    private MultipartFile filenameMF;    
//----------------------------------------------
    
	public MediaDTO() {}
	
	public int getMediano() {
		return mediano;
	}
	public void setMediano(int mediano) {
		this.mediano = mediano;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getMview() {
		return mview;
	}
	public void setMview(String mview) {
		this.mview = mview;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public int getMediagroupno() {
		return mediagroupno;
	}
	public void setMediagroupno(int mediagroupno) {
		this.mediagroupno = mediagroupno;
	}
	
	public MultipartFile getPosterMF() {
		return posterMF;
	}

	public void setPosterMF(MultipartFile posterMF) {
		this.posterMF = posterMF;
	}

	public MultipartFile getFilenameMF() {
		return filenameMF;
	}

	public void setFilenameMF(MultipartFile filenameMF) {
		this.filenameMF = filenameMF;
	}

	@Override
	public String toString() {
		return "MediaDTO [mediano=" + mediano + ", title=" + title + ", poster=" + poster + ", filename=" + filename
				+ ", filesize=" + filesize + ", mview=" + mview + ", rdate=" + rdate + ", mediagroupno=" + mediagroupno
				+ "]";
	}	  
	  
}//class end
