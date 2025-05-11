package com.project.bigevent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.bigevent.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
