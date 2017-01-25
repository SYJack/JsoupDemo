package com.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class UsersDAOImpl implements UserDao {

	private Connection con = DBStatement.getCon();

	public boolean insert(User user) {
		String sql = "insert into user (name,age) values(?,?)";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setInt(2, user.getAge());
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

	public static void main(String[] args) {
		UsersDAOImpl daoImpl = new UsersDAOImpl();
		User user1 = new User();
		user1.setName("jack");
		user1.setAge(20);
		boolean b1 = daoImpl.insert(user1);
		User user2 = new User();
		user2.setName("jack");
		user2.setAge(20);

		boolean b2 = daoImpl.insert(user2);
		System.out.println(b2);
	}
}
