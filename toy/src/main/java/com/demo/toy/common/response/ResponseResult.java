package com.demo.toy.common.response;

/**
 * 공통 응답 코드 Enum
 */
public enum ResponseResult {
    SUCCESS_SAVE        (201, "저장이 완료되었습니다."),
    SUCCESS_READ        (200, "조회가 완료되었습니다."),
    SUCCESS_UPDATE      (200, "수정이 완료되었습니다."),
    SUCCESS_DELETE      (200, "삭제가 완료되었습니다."),
    SUCCESS_NO_DATA     (204, "데이터가 없습니다."),
    SUCCESS_UPLOAD      (201, "파일 업로드가 완료되었습니다."),
    ERROR_DUPLICATE     (400, "중복된 아이디 입니다."),
    ERROR_NOT_FOUND     (404, "데이터를 찾을 수 없습니다."),
    ERROR_UPLOAD        (500, "파일 업로드에 실패했습니다."),
    ERROR_SERVER        (500, "서버 에러가 발생했습니다.");

    private final int code;
    private final String message;

    ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}