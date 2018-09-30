package com.shengxun.service;

import com.shengxun.entity.Plate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ldh on 2018/6/12.
 */
public interface PlateService {

    int deleteByPrimaryKey(Long id);

    int insert(Plate record);

    Plate selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Plate record);

    List<Plate> selectListByTempId(Long tempId);

    List<Plate> updateOrInsertPlates(List<Plate> plates, Integer isSys, Long templateId, AtomicInteger ai, String videoName);

    void InsertPlates(List<Plate> plates, Integer isSys, Long templateId, AtomicInteger ai, String videoName);
}