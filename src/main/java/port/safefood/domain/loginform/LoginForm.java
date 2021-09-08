package port.safefood.domain.loginform;

import lombok.Data;

import java.io.Serializable;


@Data
// 변경사항 1: implements Serializable
public class LoginForm {
    private String loginId;
    private String password;
}
