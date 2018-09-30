package com.shengxun.service.impl;

import com.shengxun.entity.IndustryUser;
import com.shengxun.entity.TemplateUser;
import com.shengxun.mapper.IndustryUserMapper;
import com.shengxun.mapper.TemplateUserMapper;
import com.shengxun.service.IndustryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ASUS on 2018/6/9.
 */
@Service
public class IndustryUserServiceImpl implements IndustryUserService {
    private Logger log = Logger.getLogger("IndustryUserServiceImpl");
    @Autowired
    private IndustryUserMapper industryUserMapper;

    @Autowired
    private TemplateUserMapper templateUserMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return industryUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(IndustryUser record) {
        return industryUserMapper.insert(record);
    }

    @Override
    public IndustryUser selectByPrimaryKey(Long id) {
        return industryUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IndustryUser> selectListByUserId(Long userId) {
        return industryUserMapper.selectListByUserId(userId);
    }

    @Override
    public int update(IndustryUser record) {
        return industryUserMapper.update(record);
    }


    @Override
    public List<IndustryUser> getUserTreeListById(Long id) {
        List<IndustryUser> folderUsers = industryUserMapper.selectSeqListByUserId(id);
        log.info("查询用户行业树，父节点个数：" + folderUsers.size());
        if (folderUsers != null && folderUsers.size() > 0) {
            for (int i = 0; i < folderUsers.size(); i++) {
                List<TemplateUser> templates = templateUserMapper.selectSeqByFolderId(folderUsers.get(i).getId());
                folderUsers.get(i).setList(templates);
            }
        }
        return folderUsers;

    }
}
