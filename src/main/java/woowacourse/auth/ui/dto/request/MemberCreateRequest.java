package woowacourse.auth.ui.dto.request;

import javax.validation.constraints.NotBlank;
import woowacourse.auth.application.dto.request.MemberCreateServiceRequest;

public class MemberCreateRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;

    public MemberCreateRequest() {
    }

    public MemberCreateRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public MemberCreateServiceRequest toServiceDto() {
        return new MemberCreateServiceRequest(email, password, nickname);
    }
}
