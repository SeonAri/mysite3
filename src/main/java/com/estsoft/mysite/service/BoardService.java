package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;

public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public void writeBoard( BoardVo vo ){
		boardDao.insert( vo );
	}
}
