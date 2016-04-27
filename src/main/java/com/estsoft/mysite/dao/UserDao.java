package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.db.DBConnection;
import com.estsoft.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private DBConnection dbConnection;

	@Autowired
	private SqlSession sqlSession;

	public UserVo get(String email) {
		UserVo vo = sqlSession.selectOne("user.selectByEmail", email);
		return vo;
	}

	public UserVo get(Long userNo) {
		UserVo vo = sqlSession.selectOne("user.selectByNo", userNo);
		return vo;
	}

	public UserVo get(UserVo vo) {
		UserVo userVo = sqlSession.selectOne("user.selectByEmailAndPassword", vo);
		return userVo;
	}

	public void update(UserVo userVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();
			if ("".equals(userVo.getPassword())) {
				String sql = "UPDATE user SET  name=?, gender=? WHERE no = ?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getGender());
				pstmt.setLong(3, userVo.getNo());
			} else {
				String sql = "UPDATE user SET  name=?, gender=?, passwd=password(?) WHERE no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getGender());
				pstmt.setString(3, userVo.getPassword());
				pstmt.setLong(4, userVo.getNo());
			}

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();
			String sql = "INSERT INTO user VALUES (null, ?, ?, password(?), ? )";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
