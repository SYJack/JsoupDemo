package com.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.core.zhihu.model.ZhihuUserInfo;
import com.mysql.jdbc.PreparedStatement;

public class UsersDAOImpl implements UserDao {

	private Connection con = DBStatement.getCon();

	public boolean insert(ZhihuUserInfo userInfo) {
		String sql = "insert into zhihuuserinfo "
				+ "(username,gender,business,company,position,education,major,answersNum,starsNum,thxNum,followingNum,followersNum,url) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, userInfo.getUserName());
			pstmt.setInt(2, userInfo.getGender());
			pstmt.setString(3, userInfo.getBusiness());
			pstmt.setString(4, userInfo.getCompany());
			pstmt.setString(5, userInfo.getPosition());
			pstmt.setString(6, userInfo.getEducation());
			pstmt.setString(7, userInfo.getMajor());
			pstmt.setString(8, userInfo.getAnswersNum());
			pstmt.setString(9, userInfo.getStarsNum());
			pstmt.setString(10, userInfo.getThxNum());
			pstmt.setString(11, userInfo.getFollowingNum());
			pstmt.setString(12, userInfo.getFollowersNum());
			pstmt.setString(13, userInfo.getUrl());
			// 存储user
			int i = pstmt.executeUpdate();
			if (i > 0)
				return flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean delete(int id) {
		String sql = "delete from user where id =" + id;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			if (i > 0)
				return flag = true;
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean update(User user) {
		return false;
	}

	public List<User> query() {
		return null;
	}

	public User query(String id) {
		return null;
	}

	// public static void main(String[] args) {
	// UsersDAOImpl daoImpl = new UsersDAOImpl();
	// User user1 = new User();
	// user1.setName("jack");
	// user1.setAge(20);
	// boolean b1 = daoImpl.insert(user1);
	// User user2 = new User();
	// user2.setName("jack");
	// user2.setAge(20);
	//
	// boolean b2 = daoImpl.insert(user2);
	// System.out.println(b2);
	// }
}
