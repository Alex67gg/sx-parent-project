package com.shengxun.service.impl;

import com.shengxun.entity.IndustryAll;
import com.shengxun.mapper.IndustryAllMapper;
import com.shengxun.mapper.TemplateAllMapper;
import com.shengxun.service.IndustryAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ldh on 2018/6/11.
 */
@Service
public class IndustryAllServiceImpl implements IndustryAllService {

    @Autowired
    private IndustryAllMapper industryAllMapper;
    @Autowired
    private TemplateAllMapper templateAllMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return industryAllMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IndustryAll record) {
        return industryAllMapper.insert(record);
    }

    @Override
    public IndustryAll selectByPrimaryKey(Integer id) {
        return industryAllMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(IndustryAll record) {
        return industryAllMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<IndustryAll> selectList() {
        return industryAllMapper.selectList();
    }

    /**
     * 查询全局行业树形列表
     *
     * @return
     */
    @Override
    public List<IndustryAll> selectIndustryTreeList() throws Exception {
        List<IndustryAll> qjhies = null;
        try {
            //查询全局行业树列表
            qjhies = selectList();
            if (qjhies != null && qjhies.size() > 0) {
                for (int i = 0; i < qjhies.size(); i++) {
                    qjhies.get(i).setTemplateAlls(templateAllMapper.selectByIndustryId(qjhies.get(i).getId()));
                }
                return qjhies;
            }
        } catch (Exception e) {
            throw e;
        }
        return qjhies;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        industryAllMapper.updateStatus(id, status);
    }

    @Override
    public void updateReNameById(Long id, String name) {
        industryAllMapper.updateNameById(id, name);
    }

}
