package com.yun.mybatis.mapper;

import com.yun.mybatis.model.UserWx;
import com.yun.mybatis.model.UserWxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserWxMapper {
    int countByExample(UserWxExample example);

    int deleteByExample(UserWxExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserWx record);

    int insertSelective(UserWx record);

    List<UserWx> selectByExampleWithRowbounds(UserWxExample example, RowBounds rowBounds);

    List<UserWx> selectByExample(UserWxExample example);

    UserWx selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserWx record, @Param("example") UserWxExample example);

    int updateByExample(@Param("record") UserWx record, @Param("example") UserWxExample example);

    int updateByPrimaryKeySelective(UserWx record);

    int updateByPrimaryKey(UserWx record);
}