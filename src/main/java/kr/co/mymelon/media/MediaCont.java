package kr.co.mymelon.media;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.utility.UploadSaveManager;
import net.utility.Utility;

@Controller
public class MediaCont {

	@Autowired
	MediaDAO dao;

	public MediaCont() {
		System.out.println("----- MediaCont()객체 생성됨...");
	}
	
	@RequestMapping("/media/list.do")
	public ModelAndView list(MediaDTO dto) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("media/list");
		mav.addObject("list", dao.list(dto));
		mav.addObject("root", Utility.getRoot());// /mymelon
		//부모글번호
		mav.addObject("mediagroupno", dto.getMediagroupno());
		return mav;
	}//list() end
	
	
	@RequestMapping(value="/media/create.do", method=RequestMethod.GET)
	public ModelAndView createForm(MediaDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("media/createForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("mediagroupno", dto.getMediagroupno());    
	    return mav;
	}//createForm() end
	
	@RequestMapping(value="/media/create.do", method=RequestMethod.POST)
	public ModelAndView createProc(MediaDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("media/msgView");
	    mav.addObject("root", Utility.getRoot());
//-----------------------------------------------------	    
//      전송된 파일 처리 
//      -> 실제 파일은 /media/stroage폴더에 저장
//      -> 저장된 파일관련 정보는 media테이블에 저장
	    
//      실제 물리적인 경로
	    String basePath = req.getRealPath("/media/storage");
	    
//      1)<input type='file' name='posterMF'>
	    //->파일 가져오기
	    MultipartFile posterMF = dto.getPosterMF();
	    //->파일 저장하고 리네임된 파일명 반환
	    String poster = UploadSaveManager.saveFileSpring30(posterMF, basePath);	    
	    //->파일명 dto객체에 담기
	    dto.setPoster(poster);
	    
//      2)<input type='file' name='filenameMF'>
	    MultipartFile filenameMF = dto.getFilenameMF();
	    String filename = UploadSaveManager.saveFileSpring30(filenameMF, basePath);
	    dto.setFilename(filename);
	    dto.setFilesize(filenameMF.getSize());	    
//-----------------------------------------------------	    
	    
	    int cnt=dao.create(dto);
	    if(cnt==0) {
	      mav.addObject("msg1",  "<p>음원 등록 실패</p>");
	      mav.addObject("img",   "<img src='../images/fail.png'>");
	      mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
	      mav.addObject("link2", "<input type='button' value='목록으로' onclick='location.href=\"./list.do?mediagroupno=" + dto.getMediagroupno() + "\"'>");
	    }else {
	      mav.addObject("msg1",  "<p>음원 등록 성공</p>");
	      mav.addObject("img",   "<img src='../images/sound.png'>");
	      mav.addObject("link1", "<input type='button' value='목록으로' onclick='location.href=\"./list.do?mediagroupno=" + dto.getMediagroupno() + "\"'>");      
	    }//if end
	    
	    return mav;   
	}//createProc() end
	
	
	@RequestMapping(value="/media/read.do", method=RequestMethod.GET)
	public ModelAndView read(int mediano) {
		ModelAndView mav = new ModelAndView();
		MediaDTO dto = dao.read(mediano);
		if(dto!=null) {
			String filename = dto.getFilename();
			//미디어파일명을 전부 소문자로 치환
			filename = filename.toLowerCase();
			if(filename.endsWith(".mp3")) {
				mav.setViewName("media/readMP3");
			}else if(filename.endsWith(".mp4")) {
				mav.setViewName("media/readMP4");
			}//if end
		}//if end
		
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dto);    
	    return mav;
	}//read() end
	
	
	@RequestMapping(value="/media/delete.do", method=RequestMethod.GET)
	public ModelAndView deleteForm(MediaDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("media/deleteForm");
	    mav.addObject("root", Utility.getRoot());
	    //삭제관련정보 가져오기
	    mav.addObject("dto", dao.read(dto.getMediano()));
	    return mav;
	}//deleteForm() end

	
	@RequestMapping(value="/media/delete.do", method=RequestMethod.POST)
	public ModelAndView deleteProc(MediaDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("media/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    //삭제하고 하는 정보 가져오기
	    MediaDTO oldDTO = dao.read(dto.getMediano());
	    
	    int cnt = dao.delete(dto.getMediano());
	    if(cnt==0) {
	    	mav.addObject("msg1",  "<p>음원파일 삭제 실패!!</p>");
	        mav.addObject("img",   "<img src='../images/fail.png'>");
	        mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
	        mav.addObject("link2", "<input type='button' value='음원목록' onclick='location.href=\"./list.do?mediagroupno="+dto.getMediagroupno()+"\"'>");
	    }else {
	    	//관련 파일 삭제
	    	String basepath = req.getRealPath("/media/storage");
	    	UploadSaveManager.deleteFile(basepath, oldDTO.getPoster());
	    	UploadSaveManager.deleteFile(basepath, oldDTO.getFilename());    	
	    	mav.addObject("msg1",  "<p>음원파일이 삭제 되었습니다</p>");
	        mav.addObject("img",   "<img src='../images/sound.png'>");
	        mav.addObject("link1", "<input type='button' value='음원목록' onclick='location.href=\"./list.do?mediagroupno="+dto.getMediagroupno()+"\"'>");     
	    }//if end	    
	    return mav;
	}//deleteProc() end
	

	@RequestMapping(value="/media/update.do", method=RequestMethod.GET)  
	public ModelAndView updateForm(MediaDTO dto) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("media/updateForm");
	    mav.addObject("root", Utility.getRoot());
	    mav.addObject("dto", dao.read(dto.getMediano()));
	    return mav;
	}//updateForm() end
	  
	  
	@RequestMapping(value="/media/update.do", method=RequestMethod.POST)  
	public ModelAndView updateProc(MediaDTO dto, HttpServletRequest req) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("media/msgView");
	    mav.addObject("root", Utility.getRoot());
	    
	    @SuppressWarnings("deprecation")
		String basePath = req.getRealPath("/media/storage");
	    
	    //기존에 저장된 정보 가져오기
	    MediaDTO oldDTO = dao.read(dto.getMediano());    
	//---------------------------------------------------------    
	//  파일을 수정할것인지?
	//  1)
	    MultipartFile posterMF = dto.getPosterMF();
	    if(posterMF.getSize()>0) { //포스터 파일이 전송된 경우
	      //기존 파일 삭제
	      UploadSaveManager.deleteFile(basePath, oldDTO.getPoster());      
	      //신규 파일 저장
	      String poster = UploadSaveManager.saveFileSpring30(posterMF, basePath);
	      dto.setPoster(poster);
	    }else { //포스터 파일을 수정하지 않는 경우
	      dto.setPoster(oldDTO.getPoster());
	    }//if end
	    
	//  2)
	    MultipartFile filenameMF = dto.getFilenameMF();
	    if(filenameMF.getSize()>0){
	      UploadSaveManager.deleteFile(basePath, oldDTO.getFilename());
	      String filename = UploadSaveManager.saveFileSpring30(filenameMF, basePath);
	      dto.setFilename(filename);             
	      dto.setFilesize(filenameMF.getSize());  
	      
	    }else {
	      dto.setFilename(oldDTO.getFilename());
	      dto.setFilesize(oldDTO.getFilesize());
	    }
	//---------------------------------------------------------    
	    int cnt = dao.update(dto);
	    if(cnt == 0) {
	      mav.addObject("msg1", "음원 파일 수정 실패");
	      mav.addObject("img",  "<img src='../images/fail.png'>");
	      mav.addObject("link1", "<input type='button' value='다시시도' onclick='javascript:history.back()'>");
	      mav.addObject("link2", "<input type='button' value='목록' onclick='location.href=\"./list.do?mediagroupno="+ dto.getMediagroupno()+"\"'>");
	    }
	    else {
	      mav.addObject("msg1", "음원 파일이 수정 되었습니다");
	      mav.addObject("img",  "<img src='../images/sound.png'>");
	      mav.addObject("link1", "<input type='button' value='목록' onclick='location.href=\"./list.do?mediagroupno="+ dto.getMediagroupno()+"\"'>");
	    }
	    
	    return mav;
	}//updateProc() end
	
}//class end
