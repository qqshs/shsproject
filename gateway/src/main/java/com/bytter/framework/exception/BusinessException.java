package com.bytter.framework.exception;

import com.bytter.framework.responsException.ResultCode;
import lombok.Getter;


/**
 * 
 * @ClassName: BusinessException
 * @Description: TODO
 * @version v1.0
 */
public final class BusinessException extends RuntimeException {
	@Getter
	private final ResultCode resultCode;

	public BusinessException(String message) {
		super(message);
		this.resultCode = ResultCode.FAILURE;
	}

	public BusinessException(ResultCode resultCode) {
		super(resultCode.getMsg());
		this.resultCode = resultCode;
	}

	public BusinessException(ResultCode resultCode, String msg) {
		super(msg);
		this.resultCode = resultCode;
	}

	public BusinessException(ResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
		this.resultCode = ResultCode.FAILURE;
	}

	/**
	 * for better performance
	 *
	 * @return Throwable
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}
    
    

}
