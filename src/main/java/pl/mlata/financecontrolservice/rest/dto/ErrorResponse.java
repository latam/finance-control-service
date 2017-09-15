package pl.mlata.financecontrolservice.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String devMessage;
    private ErrorCode errorCode;
}