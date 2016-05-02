package com.estsoft.mysite.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.estsoft.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public GuestbookVo get( Long no ) {
		
		GuestbookVo vo = sqlSession.selectOne( "guestbook.selectByNo", no );
		
		return vo;
	}
	
	public Long insert( GuestbookVo vo ) {
		int count = sqlSession.insert( "guestbook.insert", vo );
		if( count == 0 ) {
			return 0L;
		}
		return vo.getNo();
	}
	
	public int delete( GuestbookVo vo ) {
		return sqlSession.delete( "guestbook.delete", vo );
	}
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = sqlSession.selectList( "guestbook.selectList" );
		return list;
	}

	public List<GuestbookVo> getList( int page ) {
		List<GuestbookVo> list = sqlSession.selectList( "guestbook.selectPageList", page );
		return list;
	}
}