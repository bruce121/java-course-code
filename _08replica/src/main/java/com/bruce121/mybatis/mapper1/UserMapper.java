package com.bruce121.mybatis.mapper1;

import com.bruce121.mybatis.BaseMapper;
import com.bruce121.mybatis.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper extends BaseMapper<UserEntity> {

}
