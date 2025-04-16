package com.xiaoxipeng.api.service.impl;

import com.xiaoxipeng.api.mapper.DictTypeMapper;
import com.xiaoxipeng.api.service.IDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxipeng.common.vo.S;
import com.xiaoxipeng.dict.entity.DictType;
import com.xiaoxipeng.dict.vo.DictTypeTreeVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {

    @Override
    public S<List<DictTypeTreeVo>> tree() {
        List<DictType> all = list();
        List<DictType> root = all.stream().filter(item -> Objects.isNull(item.getParentId())).toList();

        List<DictTypeTreeVo> rootContainer = new ArrayList<>(root.size());

        root.stream().map(item -> {
            DictTypeTreeVo node = new DictTypeTreeVo();
            node.setId(item.getId());
            node.setParentId(item.getParentId());
            node.setType(item.getType());
            node.setDictTypeCode(item.getDictTypeCode());
            node.setDictTypeName(item.getDictTypeName());
            node.setStatus(item.getStatus());
//
//            if (existsChildren(item.getId(), all)) {
//
//            }

            return node;
        }).toList();

        return null;
    }




}
