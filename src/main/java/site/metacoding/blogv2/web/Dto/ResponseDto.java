package site.metacoding.blogv2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private int code; // 1성공 -1실패 (enum)
    private String msg;
    private T body;
}
