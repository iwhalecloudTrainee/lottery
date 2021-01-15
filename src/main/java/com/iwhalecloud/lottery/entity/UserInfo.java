package com.iwhalecloud.lottery.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "user_info")
public class UserInfo {
	/**
	 * 用户id
	 */
	@Id
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

}