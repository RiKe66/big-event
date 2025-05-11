package com.project.bigevent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.bigevent.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);
}
