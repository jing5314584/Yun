package com.yun.mybatis.mapper;

import com.yun.mybatis.model.Slides;
import com.yun.mybatis.model.SlidesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SlidesMapper {
    int countByExample(SlidesExample example);

    int deleteByExample(SlidesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Slides record);

    int insertSelective(Slides record);

    List<Slides> selectByExampleWithRowbounds(SlidesExample example, RowBounds rowBounds);

    List<Slides> selectByExample(SlidesExample example);

    Slides selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Slides record, @Param("example") SlidesExample example);

    int updateByExample(@Param("record") Slides record, @Param("example") SlidesExample example);

    int updateByPrimaryKeySelective(Slides record);

    int updateByPrimaryKey(Slides record);
}