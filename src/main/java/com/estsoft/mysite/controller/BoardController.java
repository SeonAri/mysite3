package com.estsoft.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping( "" )
	public String list(
		@RequestParam( value="p", required=true, defaultValue="1" )  Long page,
		@RequestParam( value="kwd", required=true, defaultValue="" )  String keyword,
		Model model ) {
		
		Map<String, Object> map = boardService.listBoard( keyword, page );
		model.addAttribute( "map", map );
		
		return "/board/list";
	}

	@Auth
	@RequestMapping("/write")
	public String write() {
		return "/board/write";
	}

	@Auth
	@RequestMapping("/insert")
	public String insert(@ModelAttribute BoardVo vo,	@AuthUser UserVo authUser ) {
		
		System.out.println( authUser );
		
		vo.setUserNo( authUser.getNo() );
		boardService.writeBoard( vo );
		
		return "redirect:/board";
	}
	
}
