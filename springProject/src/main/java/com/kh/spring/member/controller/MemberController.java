package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // 어노테이션을 붙여주면 빈 스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
	@Autowired
	private MemberService mService;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	// * 요청시 전달값 (파라미터)을 전송받는 방법 (요청시 전달되는 값들 처리)
	
	/*
	 * 1. HttpServletRequest를 통해 전송받기 (기존 jsp/servlet 때의 방식)
	 * 
	 */
	
	/*
	@RequestMapping("login.me")	// HandlerMapping으로 등록
	public void loginMember(HttpServletRequest request) {
		
		Member m = new Member();
		
		m.setUserId(request.getParameter("id"));
		m.setUserPwd(request.getParameter("pwd"));
		
		System.out.println(m.getUserId());
		System.out.println(m.getUserPwd());
		
	}
	*/
	
	
	/*
	 *  2. @RequestParam 어노테이션 방식 (내부적으로 HttpServletRequet로 작업되고 있을 것)
	 *  
	 */
	
	/*
	@RequestMapping("login.me")
	public void loginMember(@RequestParam(value="id", defaultValue="ssss") String userId, 
							@RequestParam(value="pwd", defaultValue="ssss") String userPwd) {
		
		
		Member m = new Member();
		
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		
		System.out.println(m.getUserId());
		System.out.println(m.getUserPwd());
		
		
	}
	*/
	
	/*
	 *  3. @RequestParam 어노테이션 생략하는 방법
	 *  
	 *  	요청시 전달되는 키값과 받아주는 매개변수명과 일치하면
	 *  	해당 그 매개변수에 값이 바로 담기게 됨!!
	 *  
	 *  
	 *  	어노테이션을 생략했기 때문에 defaultValue같은 속성은 이용할 수 없음!!ㅠㅠ
	 */
	
	/*
	@RequestMapping("login.me")
	public void loginMember (String userId, String userPwd) {
		
		System.out.println(userId);
		System.out.println(userPwd);
		
		Member m = new Member();
		
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		
	}
	*/
	
	/*
	 * 
	 * 4. 바로 객체의 필드에 담는 방법
	 * 
	 * 커맨드 객체 방식이라고 이야기함!
	 * 스프링 컨테이너가 해당 객체를 기본 생성자로 생성한 후
	 * setter 메소드로 값을 주입해줌
	 * 
	 * (주의 : 반드시 name의 속성값을 내가 담고자하는 객체의 필드명이어야 한다!)
	 * 
	 */
	
	/*
	@RequestMapping("login.me")
	public void loginMember(Member m) {
		

		// 직접 객체 생성을 하면 => 결합도 높음!!
		// 결합도가 높을때 발생하는 문제
		// 1. 클래스명이 변경됐을 경우 => 에러 발생
		// 2. 매번 새로 생성, 소멸이 반복 (즉, 1만건 요청 1만건 객체 생성...)
		
		// 우의 문제점을 해소하고자 한다면 => 결합도를 낮게 (직접 객체 생성 안하면됨!!)
		
		// 스프링 컨테이너가 해당 객체를 관리할 수 있도록 "빈으로 등록"
		// DI(의존성 주입을 통해서 생성된 객체 받아다 씀)
		
		Member loginUser = mService.loginMember(m);
		*/
		
		// 이제부터 요청 처리 후 응답페이지로 어떻게 포워딩하는지
		// 이때 응답데이터가 있다면 어디에 어떤식으로 담으면 되는지 대해 배워보자!
		
	
		/*
		 *   1. Model 이라는 객체 사용하는 방법
		 *   
		 *   Model이라는 객체를 이용해서 응답뷰에 전달하려는
		 *   응답데이터를 맵 형식 (key, value)으로 담을 수 있음!
		 *   scope requestScope이다. == 포워딩 된 응답페이지 에서만 꺼낼 수 있음!
		 */
		
	/*
		@RequestMapping("login.me")
		public String loginMember(Member m, HttpSession session, Model model) {
			
			Member loginUser = mService.loginMember(m);
			
			if(loginUser != null) {	//로그인 성공
				
				
				session.setAttribute("loginUser", loginUser);
				
				return "redirect:/";
				
			}else {// 로그인 실패
				
				model.addAttribute("errorMsg", "로그인실패!");
				
				//	/WEB-INF/views/common/errorPage.jsp로 포워딩하고싶어!
				
				return "common/errorPage";
				
				// 즉 여기서 리턴된 문자열의 앞에는 /WEB-INF/views/가 붙고
				//					뒤에는 .jsp가 붙어서
				// 					해당 뷰로 포워딩 됨					
			}
		
	}
	*/
	
	/*
	 *  2. ModelAndView 객체 사용하는법
	 * 
	 *  첫번째 방법의 Model이라는 객체는 응답하고자 하는 데이터 맵형식(key,value)으로 담는 공간이라고 한다면
	 *  View는 포워딩할 뷰에 대한 그 뷰페이지 대한 정보를 담을 수 있는 공간
	 *  
	 *  ModelAndView는 이 두가지 합쳐놓은 객체
	 *  즉, 응답데이터 담을 수 있고, 응답페이지에 대한 정보도 담을 수 있는 공간
	 *  
	 */
	
	
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		
		Member loginUser = mService.loginMember(m);
		// loginUser.getUserPwd() == db로부터 아이디 가지고 조회한 암호문 담김 (암호문)
		// m.getUserPwd() == 로그인 요청시 입력했떤 비밀번호 값 (평문)
		
		
		
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd()) ) {	//로그인 성공
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
			
			
		}else {// 로그인 실패
			mv.addObject("errorMsg", "로그인 실패!");
			mv.setViewName("common/errorPage");		
		}
		
		return mv;
	}
	
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
		
		
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		
		return "member/memberEnrollForm";
	}
	
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model) {
		
		
		//System.out.println(m);
		
		/*
		 * 
		 * 문제1. 한글 값 깨져있음! => 인코딩 작업해야함!
		 * 해결1. 스프링에서 제공하는 인코딩 필터 등록할거임! => web.xml 등록
		 * 		 어떤 요청이던 간에 해당 인코딩 필터 거쳐가게끔
		 * 문제2. 만약에 나이를 입력하지 않고, 요청하게되면 "" 빈 문자열이 넘어오기때문에 파싱 x
		 * 		 만약에 20이라는 값을 입력하고 요청하게 되면 "20"이 자동으로 파싱되서 int형 변수에 담김
		 * 		 단, "" 은 파싱이 될 수 없음! => NumberFormatException 발생
		 * 해결2. int age => String age 타입 바꾸기!
		 * 
		 * 문제3. 비밀번호가 사용자가 입력한 있는 그대로의 평문임!
		 * 해결3. 암호화 과정을 거쳐서 db에 저장해야됨!
		 * 
		 * 		* bcrypt방식
		 * 		사용자가 입력한 비밀번호(평문) + salt값(랜덤값)	-------> 암호문
		 * 
		 * 
		 * 		BCryptPasswordEncoder(스프링 시큐리티 모듈에서 제공함)
		 * 
		 */
		//System.out.println("암호화 전 : " +  m.getUserPwd());
		
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		
		m.setUserPwd(encPwd);
		
		//System.out.println("암호화 후 : " + encPwd);
		
		int result = mService.insertMember(m);
		
		if(result > 0) { // 회원가입 성공
			
			return "redirect:/";
			
		}else {// 회원가입 실패
			
			model.addAttribute("errorMsg","회원가입 실패");
			return "common/errorPage";
		}
		
		
	}

}

