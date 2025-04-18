package warren.myblog.vo;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * author: Warren
 */
@Data
public class SysUserVo {
    private String account;
    private String avatar;
    private String mobilePhoneNumber;

    private LocalDateTime createDate;
    private LocalDateTime lastLogin;
    private String email;
    private String nickname;

}
