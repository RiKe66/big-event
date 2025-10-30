package com.project.bigevent.pojo;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
//lombok 编译阶段，自动添加getter和setter方法
@Data
public class User {

    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore//spring mvc把当前对象转换成json字符串时会自动忽略，最终json中就没有密码
    private String password;//密码
    private String nickname;//昵称
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间



}
