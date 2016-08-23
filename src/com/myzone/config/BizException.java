package com.myzone.config;

/**   
 * @Title: BizException.java 
 * @Package com.myzone.config 
 * @Description: 系统业务异常 throw new BizException("XXXX")
 * @author jiangyf   
 * @date 2016年1月8日 下午4:07:52 
 * @version V1.0   
 */
public class BizException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
