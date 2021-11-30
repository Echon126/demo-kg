package com.example.demo.mapper;

import com.example.demo.domain.entity.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-06-25
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    @Select("select * from tb_vehicle_info")
    List<Map<String, Object>> getVehicle();
}
