package com.myspring.pro30.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro30.member.vo.MemberVO;

public interface MemberController {
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView addMember(@ModelAttribute("member") MemberVO memberVO,HttpServletRequest request, HttpServletResponse response) throws Exception;	
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView modMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView infomationMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView mod(@ModelAttribute("member") MemberVO member,
            RedirectAttributes rAttr,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
                              RedirectAttributes rAttr,
                              HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public String idCheck(@RequestParam("id") String id) throws Exception;
	public String nameCheck(@RequestParam("name") String name) throws Exception;
}