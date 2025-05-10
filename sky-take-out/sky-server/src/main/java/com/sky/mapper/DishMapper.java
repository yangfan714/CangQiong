package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select count(id) from dish where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId) ;


    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


    @AutoFill(value= OperationType.INSERT)
    void insert(Dish dish);

    @AutoFill(value= OperationType.UPDATE)
    void update(Dish dish);

    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);


    void deleteBatch(List<Long> ids);

    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    void deleteByIds(List<Long> ids);
}
