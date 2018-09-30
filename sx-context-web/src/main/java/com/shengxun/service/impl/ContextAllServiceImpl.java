package com.shengxun.service.impl;

import com.shengxun.entity.ContextAll;
import com.shengxun.mapper.ContextAllMapper;
import com.shengxun.result.vo.ContextAllVo;
import com.shengxun.result.vo.ContextAllsVo;
import com.shengxun.service.ContextAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by ldh on 2018/6/12.
 */
@Service
public class ContextAllServiceImpl implements ContextAllService {


    @Autowired
    private ContextAllMapper contextAllMapper;
    private static final Logger logs = Logger.getLogger("ContextAllServiceImpl");



    /**
     * 查询所有的全局语境
     *
     * @return
     */
    @Override
    public List<ContextAll> selectAll() {
        return contextAllMapper.selectAll();
    }

    /**
     * 修改单个全局语境
     *
     * @param contextAll
     */
    @Override
    public void updateContextAll(ContextAll contextAll) throws Exception {
        try {
            contextAllMapper.updateByPrimaryKey(contextAll);
        } catch (Exception e) {
            logs.info("修改单个全局语境报错 ：" + e.getMessage());
            throw e;
        }
    }

    /**
     * 添加 修改 全局语境
     *
     * @param contextAlls
     */
    @Override
    public void updateContextAlls(HashMap<Integer, ContextAllsVo> contextAlls) throws Exception {
        try {
            Long start = System.currentTimeMillis();
            if (contextAlls != null && contextAlls.size() > 0) {
                logs.info("添加全局语境的层数："+contextAlls.size());
                List<ContextAll> contextAllLists = new ArrayList<ContextAll>();
                for (int k = 1; k <= contextAlls.size(); k++) {
                    ContextAllsVo contexts = contextAlls.get(k);//获取不同的语境板块；
                    List<List<ContextAll>> layers = contexts.getLayer();
                    if (layers!=null){
                        for (List<ContextAll> cons:layers) {
                            if (cons!=null){
                                for (ContextAll con:cons){
                                    contextAllLists.add(con);
                                }
                            }
                        }
                    }

//                    if (layers != null && layers.size() > 0) {
//                        for (int i = 0; i < layers.size(); i++) {  //每一个板块的层级
//                            List<ContextAll> cons = layers.get(i); //每一层中的次数列表
//                            if (cons != null && cons.size() > 0) {
//                                for (ContextAll con : cons) {
//                                    contextAllLists.add(con);
//                                }
//                            }
//                        }
//                    }
                }
                logs.info("个数为：" + contextAllLists.size());
                //循环批处理
                for (ContextAll con : contextAllLists) {
                    if(null == con.getId()){
                        contextAllMapper.insertContextAll(con);
                    }else{
                        contextAllMapper.updateByPrimaryKey(con);
                    }
                }
            }
            logs.info("修改全局语境业务处理时间：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * 添加一个全局语境
     *
     * @param contextAlls
     */
    @Override
    public void addContextAll(HashMap<Integer, ContextAllsVo> contextAlls) throws Exception {
        try {
            Long start = System.currentTimeMillis();
            if (contextAlls != null && contextAlls.size() > 0) {
                List<ContextAll> contextAllLists = new ArrayList<ContextAll>();
                for (int k = 1; k < contextAlls.size(); k++) {
                    ContextAllsVo contexts = contextAlls.get(k);//获取不同的语境；
                    List<List<ContextAll>> layers = contexts.getLayer();
                    if (layers != null && layers.size() > 0) {
                        for (int i = 0; i < layers.size(); i++) {
                            List<ContextAll> cons = layers.get(i); //每一层中的次数列表
                            if (cons != null && cons.size() > 0) {
                                for (ContextAll con : cons) {
                                    con.setCreate_times(new Date());
                                    contextAllLists.add(con);
                                }
                            }
                        }
                    }
                }
                logs.info("个数为：" + contextAllLists.size());
                //循环批处理
                for (ContextAll con : contextAllLists) {
                    contextAllMapper.insertContextAll(con);
                }
            }
            logs.info("添加全局语境业务处理时间：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据id删除全局语境
     *
     * @param sx_context_qjyj
     */
    @Override
    public void delContextAll(ContextAll sx_context_qjyj) {
        contextAllMapper.delContextAll(sx_context_qjyj);
    }

    @Override
    public HashMap<Integer, Object> selectContextAllByAll(Long temp_id, Integer type, Integer flag) {
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        try {
            for (int i = 1; i < 6; i++) {
                ContextAllsVo vo = new ContextAllsVo();
                List<Object> all = new ArrayList<Object>();
                List<ContextAll> contextAlls = contextAllMapper.selectContextAllByFlag(temp_id, type, i); // 查询出的是1 下面所有的

                //查询一下特定语境下的最高层次；
                if (contextAlls != null && contextAlls.size() > 0) {
                    List<Integer> list = new ArrayList<Integer>();
                    for (int j = 0; j < contextAlls.size(); j++) {
                        if (contextAlls.get(j).getContext_Layer() != null) {
                            list.add(contextAlls.get(j).getContext_Layer());
                        }
                    }
                    Integer max = Collections.max(list); //获取最大层次

                    List<List<ContextAll>> context = new ArrayList<List<ContextAll>>();
                    for (int k = 1; k <= max; k++) {

                        List<ContextAll> lists = new ArrayList<ContextAll>();

                        for (int a = 0; a < contextAlls.size(); a++) {  //遍历所有
                            if (contextAlls.get(a).getContext_Layer() != null && k == contextAlls.get(a).getContext_Layer()) {
                                lists.add(contextAlls.get(a));
                            }
                        }
                        context.add(lists);
                    }
                    vo.setLayer(context);
                }
                map.put(i, vo);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ContextAll selectContextById(Long id) {
        return contextAllMapper.selectContextAllById(id);

    }

    @Override
    public void updateFileName(Long id, String path, String filename) {
        ContextAll contextQjyj = new ContextAll();
        contextQjyj.setId(id);
        contextQjyj.setVideo_url(path);
        contextQjyj.setVideo_name(filename);
        contextQjyj.setVideo_edit_time(new Date());
        contextAllMapper.updateByPrimaryKey(contextQjyj);
    }
}