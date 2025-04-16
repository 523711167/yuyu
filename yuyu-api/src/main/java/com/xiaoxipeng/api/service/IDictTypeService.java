package com.xiaoxipeng.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxipeng.common.vo.S;
import com.xiaoxipeng.dict.entity.DictType;
import com.xiaoxipeng.dict.vo.DictTypeTreeVo;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
public interface IDictTypeService extends IService<DictType> {


    S<List<DictTypeTreeVo>> tree();

}
