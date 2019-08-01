package com.policy.exception;

import org.springframework.stereotype.Component;

@Component
public class PolicyException extends Exception{


	private static final long serialVersionUID = 6147204535194846372L;

	public PolicyException() {
		super();
	}

	public PolicyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PolicyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PolicyException(String arg0) {
		super(arg0);
	}

	public PolicyException(Throwable arg0) {
		super(arg0);
	}

	
	
	
}
