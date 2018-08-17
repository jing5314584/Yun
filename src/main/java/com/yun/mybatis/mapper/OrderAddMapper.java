package com.yun.mybatis.mapper;

import com.yun.mybatis.model.OrderAdd;
import com.yun.mybatis.model.OrderAddExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrderAddMapper {
    int countByExample(OrderAddExample example);

    int deleteByExample(OrderAddExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderAdd record);

    int insertSelective(OrderAdd record);

    List<OrderAdd> selectByExampleWithRowbounds(OrderAddExample example, RowBounds rowBounds);

    List<OrderAdd> selectByExample(OrderAddExample example);

    OrderAdd selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderAdd record, @Param("example") OrderAddExample example);

    int updateByExample(@Param("record") OrderAdd record, @Param("example") OrderAddExample example);

    int updateByPrimaryKeySelective(OrderAdd record);

    int updateByPrimaryKey(OrderAdd record);
}