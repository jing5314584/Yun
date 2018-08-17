package com.yun.mybatis.mapper;

import com.yun.mybatis.model.V_UserWx;
import com.yun.mybatis.model.V_UserWxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface V_UserWxMapper {
    int countByExample(V_UserWxExample example);

    int deleteByExample(V_UserWxExample example);

    int insert(V_UserWx record);

    int insertSelective(V_UserWx record);

    List<V_UserWx> selectByExampleWithRowbounds(V_UserWxExample example, RowBounds rowBounds);

    List<V_UserWx> selectByExample(V_UserWxExample example);

    int updateByExampleSelective(@Param("record") V_UserWx record, @Param("example") V_UserWxExample example);

    int updateByExample(@Param("record") V_UserWx record, @Param("example") V_UserWxExample example);
}