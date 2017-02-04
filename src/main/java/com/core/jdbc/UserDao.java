package com.core.jdbc;

import java.util.List;

import com.core.zhihu.model.ZhihuUserInfo;

public interface UserDao {

	public boolean insert(ZhihuUserInfo user);// 插入数据

	public boolean delete(int id);

	public boolean update(User user); // 修改

	public List<User> query(); // 全部查询

	public User query(String id); // 单记录查询

}
