package com.shengxun.result;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class JsonResult implements Serializable {

	private static HttpServletResponse response;

	private static final long serialVersionUID = 1L;


	/**
	 * 返回的编码
	 */
	private String code;
	
	/**
	 * 返回的信息
	 */
	private String message;
	
	/***
	 * 返回的对象
	 */
	private Object data;

	public JsonResult() {
		super();
	}
	
	public JsonResult(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public JsonResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 操作成功
	 */
	public static JsonResult success(Object data) {
		return  new JsonResult(JsonResultCode.SUCCESS,"操作成功",data);
	}

	/**
	 * 操作成功
	 */
	public static JsonResult success(String message,Object data) {
		return  new JsonResult(JsonResultCode.SUCCESS,message,data);
	}

	/**
	 * 操作成功
	 */
	public static JsonResult success(String message) {
		return  new JsonResult(JsonResultCode.SUCCESS,message);
	}

	/**
	 * 操作失败
	 */
	public static JsonResult failure() {
		response.setStatus(500);
		return  new JsonResult(JsonResultCode.FAILURE,"操作失败");
	}

	/**
	 * 操作失败
	 */
	public static JsonResult failure(String message) {
		response.setStatus(500);
		return  new JsonResult(JsonResultCode.FAILURE,message);
	}

	/**
	 * 操作失败
	 */
	public static JsonResult failure(String message,Object data) {
		response.setStatus(500);
		return  new JsonResult(JsonResultCode.FAILURE,message,data);
	}

	/**
	 * 未查找到
	 */
	public static JsonResult unFound() {
		response.setStatus(500);
		return  new JsonResult(JsonResultCode.UNFOUND,"未查找到");
	}

	/**
	 * 未查找到
	 */
	public static JsonResult unFound(String message) {
		response.setStatus(500);
		return  new JsonResult(JsonResultCode.UNFOUND,message);
	}

	/**
	 * 系统错误
     */
	public static JsonResult error(String message) {
		response.setStatus(500);
		return  new JsonResult(JsonResultCode.ERROR,message);
	}

	/**
	 * 系统错误
	 */
	public static JsonResult error() {
		response.setStatus(500);
		return  new JsonResult(JsonResultCode.ERROR,"系统错误");
	}

	@Override
	public String toString() {
		return "JsonResult{" +
				"code='" + code + '\'' +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}

	public static HttpServletResponse getResponse() {
		return response;
	}

	public static void setResponse(HttpServletResponse response) {
		JsonResult.response = response;
	}
}