package scesi.org.check.core.model.response;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
