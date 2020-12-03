package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	@RequestMapping("list.bo")	// list.bo?currentPage=xx
	public String selectBoardList(@RequestParam(value="currentPage", defaultValue="1")int currentPage,
								Model model) {
		
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectBoardList(pi);
		
		model.addAttribute("pi",pi);
		model.addAttribute("list",list);
		
		
		return "board/boardListView";
	
		
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile,HttpSession session,Model model) {
		
		
		// 첨부파일 업로드 기능을 하기 위해서 라이브러리 2개 추가, 빈으로 등록해야함!
		
		// 전달된 파일 있을 때 -> 서버 업로드 => 원본명, 저장경로 b 담기
		
		if(!upfile.getOriginalFilename().equals("")) {
			
			
		String changeName = saveFile(session, upfile);	
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName); // resources/uploadFiles 추가해서 찾기 편하게 
			
		}
	
		int result = bService.insertBoard(b);
			

		
		
		if(result>0) {
			
			session.setAttribute("alertMsg", "게시글 등록!!");
			return "redirect:list.bo";
			
			
		}else {
			
			model.addAttribute("errorMsg","게시글 작성");
			return "common/errorPage";
		}
		
		
	}
	
	@RequestMapping("detail.bo")
	public String selectBoard(int bno, Model model) {
		
		int result = bService.increaseCount(bno);
		
		if(result>0) { //유효한 게시글
			
			Board b = bService.selectBoard(bno);
			
			model.addAttribute("b",b);
			
			return "board/boardDetailView";
			
			
			
		}else { // 유효하지 않는 게시글
			
			model.addAttribute("errorMsg","존재하지 않거나, 삭제한 게시글이다!");
			return "common/errorPage";
			
		}
		
	}
	
	
	
	
	
	// 첨부파일 업로드 시켜주는 메소드
	public String saveFile(HttpSession session, MultipartFile upfile) {
		
		
		// 파일을 업로드 시킬 폴더의 물리적 경로
		
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		
		// 어떤 이름으로 업로드 시킬껀지 수정명 (changeName)
		String originName = upfile.getOriginalFilename();
		
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int ranNum = (int)(Math.random() * 90000 + 10000);
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext; // 2020120217323045236;
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			
			e.printStackTrace();
		}
		
		return changeName;
	}
	
	
	
}
