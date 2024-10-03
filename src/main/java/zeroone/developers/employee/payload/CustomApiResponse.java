package zeroone.developers.employee.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response wrapper for API responses")
public class CustomApiResponse {


    @Schema(description = "Message of the response")
    private String message;

    @Schema(description = "Indicates if the operation was successful")
    private boolean success;

    public CustomApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
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

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
