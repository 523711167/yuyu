package com.xiaoxipeng.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxipeng.common.vo.S;
import com.xiaoxipeng.dict.dto.SDictDataDto;
import com.xiaoxipeng.dict.dto.UDictDataDto;
import com.xiaoxipeng.dict.entity.DictData;
import com.xiaoxipeng.dict.vo.DictDataVo;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
public interface IDictDataService extends IService<DictData> {

    S<List<DictDataVo>> listDictDataByDictTypeId(Long dictTypeId);

    S<List<DictDataVo>> listDictDataByDictTypeCode(String dictTypeCode);

    S<DictDataVo> getDictData(Long id);

    S<Void> updateDictData(UDictDataDto uDictDataDto);

    S<Void> saveDictData(SDictDataDto sDictDataDto);

    S<Void> removeDictData(Long id);

}
