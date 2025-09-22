package com.lbraz.lms.exception;

import com.lbraz.lms.util.MessageUtil;

public class InvalidTaskCategoryException extends RuntimeException {
    public InvalidTaskCategoryException(String message) {
        super(message);
    }

    public InvalidTaskCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InvalidTaskCategoryException forTaskCategoryId(String id) {
        return new InvalidTaskCategoryException(MessageUtil.get("error.taskCategory.notFound", id));
    }
}
