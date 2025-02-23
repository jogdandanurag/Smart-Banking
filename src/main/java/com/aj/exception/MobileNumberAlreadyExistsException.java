package com.aj.exception;

public class MobileNumberAlreadyExistsException extends RuntimeException {
           public MobileNumberAlreadyExistsException(String message) {
        	   super("this mobilenumber already exists");
           }
}
