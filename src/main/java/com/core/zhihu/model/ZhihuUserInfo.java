package com.core.zhihu.model;

public class ZhihuUserInfo {

	private int id;// id

	private String userName;// 姓名

	private int gender;// 性别

	private String business;// 行业

	private String company;// 公司

	private String position;// 职位;

	private String education;// 大学

	private String major;// 专业

	private String answersNum;// 回答数量

	private String starsNum;// 获得赞同数

	private String thxNum;// 获得感谢数

	private String followingNum;// 关注的人

	private String followersNum;// 关注者数量

	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getAnswersNum() {
		return answersNum;
	}

	public void setAnswersNum(String answersNum) {
		this.answersNum = answersNum;
	}

	public String getStarsNum() {
		return starsNum;
	}

	public void setStarsNum(String starsNum) {
		this.starsNum = starsNum;
	}

	public String getThxNum() {
		return thxNum;
	}

	public void setThxNum(String thxNum) {
		this.thxNum = thxNum;
	}

	public String getFollowingNum() {
		return followingNum;
	}

	public void setFollowingNum(String followingNum) {
		this.followingNum = followingNum;
	}

	public String getFollowersNum() {
		return followersNum;
	}

	public void setFollowersNum(String followersNum) {
		this.followersNum = followersNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ZhihuUserInfo [id=" + id + ", 姓名=" + userName + ", 性别=" + gender + ", 行业=" + business + ", 公司="
				+ company + ", 职位=" + position + ", 大学=" + education + ", 专业=" + major + ", 回答数=" + answersNum
				+ ", 赞同数=" + starsNum + ", 感谢数=" + thxNum + ", 关注数=" + followingNum + ", 被关注人数=" + followersNum + "]";
	}

}
