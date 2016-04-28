package com.estsoft.mysite.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.service.GuestbookService;
import com.estsoft.mysite.vo.GuestbookVo;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
	
	@Autowired
	GuestbookService guestbookService;

	@RequestMapping( "" )
	public String index( Model model ) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute( "list", list );
		return "guestbook/list";
	}
	
	@RequestMapping( "/deleteform/{no}" )
	public String deletefrom( @PathVariable( "no" ) Long no, Model model ) {
		model.addAttribute( "no", no );
		return "guestbook/deleteform";
	}

	@RequestMapping( "/delete" )
	public String delete( @ModelAttribute GuestbookVo vo ) {
		guestbookService.deleteMessage(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping( "/insert" )
	public String insert( @ModelAttribute GuestbookVo vo ) {
		guestbookService.insertMessage(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping( "/ajax" )
	public String ajax() {
		return "guestbook/ajax-main";
	}

	@RequestMapping( "/ajaxlist" )
	@ResponseBody
	public Object ajaxList( @RequestParam( value="p", required=true, defaultValue="1" ) int page ) {
		List<GuestbookVo> list = guestbookService.getMessageList( page );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", list );		
		
		return map;
	}
	
	@RequestMapping( value="/ajaxinsert", method=RequestMethod.POST )
	@ResponseBody
	public Object ajaxInsert( @ModelAttribute GuestbookVo vo ) {
		GuestbookVo guestbookVo = guestbookService.insertMessage(vo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", guestbookVo );		
		
		return map;
	}

	@RequestMapping( value="/ajaxdelete", method=RequestMethod.POST )
	@ResponseBody
	public Object ajaxDelete( @ModelAttribute GuestbookVo vo ) {
		boolean result = guestbookService.deleteMessage( vo );

		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", result ? vo.getNo() : null );		

		return map;
	}
	
}
