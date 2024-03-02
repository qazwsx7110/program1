package com.myspring.pro30.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.PrintWriter;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.dao.MemberDAO;
import com.myspring.pro30.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl   implements MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO ;	
	@Autowired
	private MemberDAO memberDAO ;
	
	//메인,뷰 이동
	@RequestMapping(value = { "/","/main.do"}, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	//회원목록 불러오기
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String viewName = (String)request.getAttribute("viewName");				
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}
	
	//회원 추가
	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		
		MemberVO duplication;
	    
		//아이디 중복 확인
		duplication= memberDAO.duplicationId(member.getId());
		
		if(duplication==null) {
		result = memberService.addMember(member);
				
        response.setContentType("text/html; charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        out.println("<script>alert('계정이 등록 되었습니다'); location.href='"+request.getContextPath()+"/main.do'</script>");
         
        out.flush();
		}
		else {
			response.setContentType("text/html; charset=UTF-8");
	        
	        PrintWriter out = response.getWriter();
	        
	        out.println("<script>alert('계정이 중복됩니다'); location.href='"+request.getContextPath()+"/member/memberForm.do'</script>");
	         
	        out.flush();
			
		}
		
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/main.do");
		return mav;
	}
	
	//회원 삭제
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
				
		HttpSession session = request.getSession();
		MemberVO m= (MemberVO)session.getAttribute("member");
		
		//접속 아이디 일시 로그아웃
		if(m.getId().equals(id)) {
	 		session.removeAttribute("isLogOn");
	 		}
		
		memberService.removeMember(id);
		
		 response.setContentType("text/html; charset=UTF-8");
	        
	     PrintWriter out = response.getWriter();
	         	     
	     if(m.getId().equals(id)) {
	     out.println("<script>alert('계정이 삭제 되었습니다'); location.href='"+request.getContextPath()+"/main.do'</script>");
	     }
	     else {
	     out.println("<script>alert('계정이 삭제 되었습니다'); location.href='"+request.getContextPath()+"/member/listMembers.do'</script>");
	     }
	     out.flush();
	     
		ModelAndView mav = new ModelAndView("redirect:/member/main.do");
		return mav;
	}
	
	//회원 정보 수정
	@Override
	@RequestMapping(value="/member/modMember.do" ,method = RequestMethod.GET)
	public ModelAndView modMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");				
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}
	
	//회원 정보 
	@Override
	@RequestMapping(value="/member/infomationMember.do" ,method = RequestMethod.GET)
	public ModelAndView infomationMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");			
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}	
	
	
	//아이디 중복 체크
	@Override
	@ResponseBody
	@RequestMapping(value="/member/idCheck.do" ,method = RequestMethod.POST)
	public String idCheck(@RequestParam("id") String id) throws Exception {
		
		int check = 0;
		check= memberService.idCheck(id);
		String cnt = "0";
		
		if(check==0) {
			cnt="0";
			
		}
		else {			
			cnt="1";
		}
		
		return cnt;
		
	}
		
	//이름 중복체크
	@Override
	@ResponseBody
	@RequestMapping(value="/member/nameCheck.do" ,method = RequestMethod.POST)
	public String nameCheck(@RequestParam("name") String name) throws Exception {
		
		int check = 0;
		check= memberService.nameCheck(name);
		String cnt = "0";
		
		if(check==0) {
			cnt="0";
			
		}
		else {			
			cnt="1";
		}
		
		return cnt;
		
	}
	
	//회원 정보 수정
	@Override
	@RequestMapping(value = "/member/mod.do", method = RequestMethod.POST)
	public ModelAndView mod(@ModelAttribute("member") MemberVO member,
            RedirectAttributes rAttr,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		memberService.modMember(member);
		HttpSession session = request.getSession();
		session.setAttribute("member", member);
		mav.setViewName("redirect:/main.do");
		return mav;
	}
			
	//로그인
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
				              RedirectAttributes rAttr,
		                       HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView();
	memberVO = memberService.login(member);
	if(memberVO != null) {
		    HttpSession session = request.getSession();
		    session.setAttribute("member", memberVO);
		    session.setAttribute("isLogOn", true);
		    String action = (String)session.getAttribute("action");
		    session.removeAttribute("action");
		    if(action!= null) {
		       mav.setViewName("redirect:"+action);
		    }else {
		       mav.setViewName("redirect:/main.do");	
		    }

		}else {
		   rAttr.addAttribute("result","loginFailed");
		   mav.setViewName("redirect:/member/loginForm.do");
		}
	
	return mav;
	}
			
	//로그아웃
	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/main.do");
		return mav;
	}	

	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
			  @RequestParam(value= "action", required=false) String action,
		       HttpServletRequest request, 
		       HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");

		HttpSession session = request.getSession();
		session.setAttribute("action", action);  
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
}

}
