package com.create.entity;

import com.create.entity.TblTeacherInf;

public interface TblTeacherInfMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblTeacherInf record);

    int insertSelective(TblTeacherInf record);

    TblTeacherInf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblTeacherInf record);

    int updateByPrimaryKey(TblTeacherInf record);
}