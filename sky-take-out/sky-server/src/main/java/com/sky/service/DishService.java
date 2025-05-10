package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;


public interface DishService {

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void saveWithflavor(DishDTO dishDTO);

    void updateWithFlavor(DishDTO dishDTO);

    DishVO getByIdWithFlavor(Long id);

    void deleteBatch(List<Long> ids);

    void startOrStop(Integer status, Long id);
}
