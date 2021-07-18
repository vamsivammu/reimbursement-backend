package io.mavq.reimbursementbackend.dto;

public class ResponseDto {
    private boolean status;
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
