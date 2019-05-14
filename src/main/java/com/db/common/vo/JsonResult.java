package com.db.common.vo;
import java.io.Serializable;
/**
 * 借助此对象封装控制层数据
 * 1)业务层返回的数据
 * 2)状态码
 * 3)状态信息
 */
public class JsonResult implements Serializable{
	private static final long serialVersionUID = 5427856250973673757L;
	/**状态码：1表示ok,0表示error*/
	private int state=1;
	/**状态码对应的状态信息*/
	private String message="ok";
	/**正确数据(输出到客户端的数据)*/
	private Object data;
	public JsonResult(String message){

		this.message=message;
	}

	public JsonResult() {
	}

	public JsonResult(Object data) {
		this.data=data;
	}

	public JsonResult(Throwable t) {
		this.state=0;
		this.message=t.getMessage();
	}
	public static JsonResult error(String message){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setState(0);
		jsonResult.setMessage(message);
		return jsonResult;
	}
	public static JsonResult ok(Object data,String message){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setMessage(message);
		jsonResult.setState(1);
		jsonResult.setData(data);
		return jsonResult;
	}
	public static JsonResult ok(String message){
		JsonResult jsonResult=new JsonResult();
		jsonResult.setMessage(message);
		return jsonResult;
	}
	public static JsonResult ok(Object data){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(data);
		return jsonResult;
	}
	public static JsonResult ok(){
		JsonResult jsonResult=new JsonResult();
		return jsonResult;

	}

	@Override
	public String toString() {
		return "JsonResult{" +
				"state=" + state +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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
}
