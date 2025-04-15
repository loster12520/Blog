package warren.myblog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {

    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    private Integer deleted;

    private String email;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;

}
