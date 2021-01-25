package com.zht.examination.device;

public class ErrorData {

    private String errorType;

    private String epc;

    public ErrorData() {
    }

    public ErrorData(String errorType, String epc) {
        this.errorType = errorType;
        this.epc = epc;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }


}
