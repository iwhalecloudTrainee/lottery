package com.iwhalecloud.lottery.parmas.vo;

import java.util.List;

/**
 * @author by W4i
 * @date 2021/1/15 20:21
 */
public class Result {
	private boolean isSuccess;
	private Object data;
	private int code;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 默认构造方法为成功
	 */
	public Result() {
		this.isSuccess = true;
		this.data = null;
		this.code = 1;
	}

	public Result(boolean isSuccess, Object data, int code) {
		this.isSuccess = isSuccess;
		this.data = data;
		this.code = code;
	}

	/**
	 * 成功无返回值
	 *
	 * @return
	 */
	public static Result getSuccess() {
		return getSuccess(null);
	}

	/**
	 * 成功又返回值
	 *
	 * @param data
	 * @return
	 */
	public static Result getSuccess(Object data) {
		return new Result(true, data, 1);
	}
	/**
	 * 成功又返回值
	 *
	 * @param data
	 * @return
	 */
	public static Result getSuccess(Object data,int code) {
		return new Result(true, data, code);
	}
	/**
	 * 失败无返回值
	 *
	 * @return
	 */
	public static Result getFalse() {
		return getFalse(null);
	}

	/**
	 * 失败有返回值
	 *
	 * @param data
	 * @return
	 */
	public static Result getFalse(Object data) {
		return new Result(false, data, -1);
	}

	/**
	 * 返回特定success code
	 *
	 * @param code
	 * @return
	 */
	public static Result getSuccessCode(int code) {
		return new Result(true, null, code);
	}

	/**
	 * 返回特定false code
	 *
	 * @param code
	 * @return
	 */
	public static Result getFalseCode(int code) {
		return new Result(false, null, code);
	}

}

