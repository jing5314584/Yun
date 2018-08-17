package com.yun.mybatis.mapper;

import com.yun.mybatis.model.BillingRecord;
import com.yun.mybatis.model.BillingRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BillingRecordMapper {
    int countByExample(BillingRecordExample example);

    int deleteByExample(BillingRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BillingRecord record);

    int insertSelective(BillingRecord record);

    List<BillingRecord> selectByExampleWithRowbounds(BillingRecordExample example, RowBounds rowBounds);

    List<BillingRecord> selectByExample(BillingRecordExample example);

    BillingRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BillingRecord record, @Param("example") BillingRecordExample example);

    int updateByExample(@Param("record") BillingRecord record, @Param("example") BillingRecordExample example);

    int updateByPrimaryKeySelective(BillingRecord record);

    int updateByPrimaryKey(BillingRecord record);
}