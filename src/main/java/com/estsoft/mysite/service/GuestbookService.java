package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.GuestbookDao;
import com.estsoft.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	GuestbookDao guestbookDao;

	public List<GuestbookVo> getMessageList() {
		System.out.println( guestbookDao );
		return guestbookDao.getList();
	}

	public List<GuestbookVo> getMessageList( int page ) {
		return guestbookDao.getList( page );
	}
	
	public boolean deleteMessage( GuestbookVo vo ) {
		return guestbookDao.delete( vo ) == 1;
	}
	
	public GuestbookVo insertMessage( GuestbookVo vo ) {
		Long no = guestbookDao.insert( vo );
		if( no == 0 ) {
			return null;
		}
		
		GuestbookVo guestbookVo = guestbookDao.get( no );
		return guestbookVo;
	}
}
