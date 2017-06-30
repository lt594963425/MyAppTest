

package com.example.administrator.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationModel {

    @SerializedName("resultcode")
    @Expose
    private String resultcode;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;

    /**
     * @return The resultcode
     */
    public String getResultcode() {
        return resultcode;
    }

    /**
     * @param resultcode The resultcode
     */
    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    /**
     * @return The reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason The reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * @return The errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The error_code
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}



