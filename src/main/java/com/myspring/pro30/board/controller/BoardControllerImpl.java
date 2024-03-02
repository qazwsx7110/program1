package com.myspring.pro30.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import com.myspring.pro30.member.vo.MemberVO;
import com.myspring.pro30.board.dao.BoardDAO;


//게시판 컨트롤러
@Controller("boardController")
public class BoardControllerImpl  implements BoardController{
	private static final String ARTICLE_IMAGE_REPO = "D:\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	@Autowired
	BoardDAO boardDAO;
	
	
	//글목록 보여주기
	@Override
	@RequestMapping(value= "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		List articlesList = boardService.listArticles();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articlesList", articlesList);
		return mav;		
	}
		
	//글 보여주기
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
			  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		Map articleMap=boardService.viewArticle(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("articleMap", articleMap);
		
		HttpSession session = request.getSession();
		//답글 작성시 부모글 번호추가
		session.setAttribute("parentNO", articleNO);
		
		return mav;
	}
   	
	
  //글 수정하기
  @RequestMapping(value="/board/modArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  
    HttpServletResponse response) throws Exception{
    multipartRequest.setCharacterEncoding("utf-8");
	Map articleMap = new HashMap();
	Enumeration enu=multipartRequest.getParameterNames();
	while(enu.hasMoreElements()){
		String name=(String)enu.nextElement();
		String value=multipartRequest.getParameter(name);
		articleMap.put(name,value);
	}
	
	//바꿀 이미지 업로드
	Map<String, String> fileList= changeupload(multipartRequest);
	List<ImageVO> modimageFileList = new ArrayList<ImageVO>();
	if(fileList!= null && fileList.size()!=0) {
		for(String fileNO : fileList.keySet()) {
			ImageVO imageVO = new ImageVO();
			int cNo = Integer.parseInt(fileNO);
			imageVO.setImageFileNO(cNo);
			String fileName= fileList.get(fileNO);
			imageVO.setImageFileName(fileName);
			modimageFileList.add(imageVO);
		}
		articleMap.put("modimageFileList", modimageFileList);
	}	
		
	String articleNO=(String)articleMap.get("articleNO");
	int articleNO2= Integer.parseInt(articleNO);
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    try {
    	                     
         if(modimageFileList!=null && modimageFileList.size()!=0) {
 			for(ImageVO  cimageVO:modimageFileList) {
 				int cimageNO = cimageVO.getImageFileNO(); 				 				
 				List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO2);
 				
 				for(ImageVO imageVO:imageFileList) {
 					 
 					 int imageNO = imageVO.getImageFileNO();
 					 
 					 //바꿀이미지 번호 확인후 교체
 					 if(imageNO==cimageNO){
 						 
 						File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+imageVO.getImageFileName());
 						oldFile.delete();
 						File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+cimageVO.getImageFileName());
 		 				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
 		 				
 		 				FileUtils.moveFileToDirectory(srcFile, destDir,true);
 						 
 					 }
 				 } 				 		
 			}
 		}
         
       boardService.modArticle(articleMap);
                          
       message = "<script>";
	   message += " alert('글을 수정했습니다.');";
	   message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	   message +=" </script>";
       resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }catch(Exception e) {
    	
      message = "<script>";
	  message += " alert('오류가 발생했습니다.다시 수정해주세요');";
	  message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	  message +=" </script>";
      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }
    return resEnt;
  }
  	
  
  //글 삭제
  @Override
  @RequestMapping(value="/board/removeArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setContentType("text/html; charset=UTF-8");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	try {
		boardService.removeArticle(articleNO);
		File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
		//이미지 파일삭제
		FileUtils.deleteDirectory(destDir);
		
		message = "<script>";
		message += " alert('글을 삭제했습니다.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	       
	}catch(Exception e) {
		message = "<script>";
		message += " alert('작업중 오류가 발생했습니다.다시 시도해 주세요.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    e.printStackTrace();
	}
	return resEnt;
  }  
  
  //글 추가
  @Override
  @RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
	multipartRequest.setCharacterEncoding("utf-8");
	String imageFileName=null;
	
	Map articleMap = new HashMap();
	Enumeration enu=multipartRequest.getParameterNames();
	while(enu.hasMoreElements()){
		String name=(String)enu.nextElement();
		String value=multipartRequest.getParameter(name);
		articleMap.put(name,value);
	}
	
	
	HttpSession session = multipartRequest.getSession();
	MemberVO memberVO = (MemberVO) session.getAttribute("member");
	String id = memberVO.getId();
	articleMap.put("id",id);
	articleMap.put("parentNO", 0);
	
	//이미지 업로드
	List<String> fileList =upload(multipartRequest);
	List<ImageVO> imageFileList = new ArrayList<ImageVO>();
	if(fileList!= null && fileList.size()!=0) {
		for(String fileName : fileList) {
			ImageVO imageVO = new ImageVO();
			imageVO.setImageFileName(fileName);
			imageFileList.add(imageVO);
		}
		articleMap.put("imageFileList", imageFileList);
	}
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	try {
		
		int articleNO = boardService.addNewArticle(articleMap);
		//이미지파일 임시파일에서 옮김
		if(imageFileList!=null && imageFileList.size()!=0) {
			for(ImageVO  imageVO:imageFileList) {
				imageFileName = imageVO.getImageFileName();
				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);			
				FileUtils.moveFileToDirectory(srcFile, destDir,true);
			}
		}
		    
		message = "<script>";
		message += " alert('새글을 추가했습니다.');";
		message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    
		 
	}catch(Exception e) {
		if(imageFileList!=null && imageFileList.size()!=0) {
		  for(ImageVO  imageVO:imageFileList) {
		  	imageFileName = imageVO.getImageFileName();
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
		 	srcFile.delete();
		  }
		}
		
		message = " <script>";
		message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
		message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
		message +=" </script>";
		resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		e.printStackTrace();
	}
	return resEnt;
  }
 
  
  //답글추가
  @Override
  @RequestMapping(value="/board/addReply.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity addNewReplyArticle(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws Exception {
	  
	  multipartRequest.setCharacterEncoding("utf-8");		
		
		Map articleMap = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name,value);
		}
				
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("id",id);
		
		//부모글 번호 가져오기
		String parentNO = session.getAttribute("parentNO").toString();
		articleMap.put("parentNO",parentNO);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {			
			int articleNO = boardService.addNewReplyArticle(articleMap);
						    
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);		    
			 
		}catch(Exception e) {
						
			message = " <script>";
			message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
	  	  	 
	  return resEnt;
  }
  
	@RequestMapping(value = "/board/*Form.do", method = { RequestMethod.POST, RequestMethod.GET} )
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	//다중 이미지 업로드하기
	private List<String> upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList= new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName=mFile.getOriginalFilename();
			fileList.add(originalFileName);
			File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName);
			if(mFile.getSize()!=0){ 
				if(!file.exists()){ 
					file.getParentFile().mkdirs();  //경로에 해당하는 디렉토리들을 생성
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+originalFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송
				}
			}
		}
		return fileList;
	}
	
	//교체할 이미지 업로드하기
	private Map<String, String> changeupload(MultipartHttpServletRequest multipartRequest) throws Exception{
		Map cfileList= new HashMap();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			if(!mFile.isEmpty()){
					//
			String originalFileName=mFile.getOriginalFilename();
			String cfileNo=mFile.getName();
			cfileList.put(cfileNo,originalFileName);	
									
			 File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName);
			 if(mFile.getSize()!=0){ 
				if(!file.exists()){ 
					file.getParentFile().mkdirs();  //경로에 해당하는 디렉토리들을 생성
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+originalFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송
				}
			}
			
			}
			 
		}
		
		return cfileList;
	}
	
	
}
