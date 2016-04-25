package com.estsoft.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.vo.BoardVo;

@Controller()
@RequestMapping("/board")
public class BoardController {

	@RequestMapping( "" )
	public String list(
		@RequestParam( value="p", required=true, defaultValue="1" )  Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="" )  String keyword ) {
		return "/board/list";
	}

	@RequestMapping("/write")
	public String write() {
		return "/board/write";
	}

	@RequestMapping("/insert")
	@ResponseBody
	public String insert( @ModelAttribute BoardVo vo ) {
		System.out.println( vo );
		return "/board/insert";
	}
	
}
