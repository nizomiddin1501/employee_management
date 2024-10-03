package zeroone.developers.employee.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response wrapper for API responses")
public class CustomApiResponse<T> {


    @Schema(description = "Message of the response")
    private String message;

    @Schema(description = "Indicates if the operation was successful")
    private boolean success;

    private T data;

    public CustomApiResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public CustomApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.data = null;
    }



    public CustomApiResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "CustomApiResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
