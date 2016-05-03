package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	private final int LIST_PAGESIZE = 5;   // 리스팅되는 게시물의 수
	private final int LIST_BLOCKSIZE = 5;  // 페이지 리스트에서 표시되는 페이지 수
	
	public Map<String, Object> listBoard( String keyword, Long page ){
		//1. calculate pager's basic data 
		long totalCount = boardDao.getTotalCount( keyword );
		long pageCount = (long)Math.ceil( (double)totalCount / LIST_PAGESIZE );
		long blockCount = (long)Math.ceil( (double)pageCount / LIST_BLOCKSIZE );
		long currentBlock = (long)Math.ceil( (double)page / LIST_BLOCKSIZE ); 
		
		//2. page validation
		if( page < 1 ) {
			page = 1L;
			currentBlock = 1;
		} else if( page > pageCount ) {
			page = pageCount;
			currentBlock = (int)Math.ceil( (double)page / LIST_BLOCKSIZE );
		}
		
		//3. calculate pager's data
		long startPage = (currentBlock == 0 ) ? 1 : ( currentBlock - 1 ) * LIST_BLOCKSIZE + 1;
		long endPage = ( startPage - 1 ) + LIST_BLOCKSIZE;
		long prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * LIST_BLOCKSIZE : 0;
		long nextPage = ( currentBlock < blockCount ) ? currentBlock * LIST_BLOCKSIZE + 1 : 0;

		//4. fetch list
		List<BoardVo> list = boardDao.getList( keyword, page, LIST_PAGESIZE );
		
		//5. pack all information of list
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "list", list );
		map.put( "totalCount",  totalCount);
		map.put( "keyword", keyword );
		map.put( "sizeList",  LIST_PAGESIZE );
		map.put( "firstPage", startPage );
		map.put( "lastPage", endPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "currentPage", page );
		
		return map;
	}
	
	public void writeBoard( BoardVo vo ){
		boardDao.insert( vo );
	}
}
