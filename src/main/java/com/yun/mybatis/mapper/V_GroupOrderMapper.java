package com.yun.mybatis.mapper;

import com.yun.mybatis.model.V_GroupOrder;
import com.yun.mybatis.model.V_GroupOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface V_GroupOrderMapper {
    int countByExample(V_GroupOrderExample example);

    int deleteByExample(V_GroupOrderExample example);

    int insert(V_GroupOrder record);

    int insertSelective(V_GroupOrder record);

    List<V_GroupOrder> selectByExampleWithRowbounds(V_GroupOrderExample example, RowBounds rowBounds);

    List<V_GroupOrder> selectByExample(V_GroupOrderExample example);

    int updateByExampleSelective(@Param("record") V_GroupOrder record, @Param("example") V_GroupOrderExample example);

    int updateByExample(@Param("record") V_GroupOrder record, @Param("example") V_GroupOrderExample example);
}