package com.xiaoxipeng.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoxipeng.api.mapper.DictDataMapper;
import com.xiaoxipeng.api.service.IDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxipeng.api.service.IDictTypeService;
import com.xiaoxipeng.common.exception.RedundantDataException;
import com.xiaoxipeng.common.exception.YuyuException;
import com.xiaoxipeng.common.vo.S;
import com.xiaoxipeng.dict.dto.SDictDataDto;
import com.xiaoxipeng.dict.dto.UDictDataDto;
import com.xiaoxipeng.dict.entity.DictData;
import com.xiaoxipeng.dict.entity.DictType;
import com.xiaoxipeng.dict.vo.DictDataVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.xiaoxipeng.common.constant.Global.ENABLE;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {

    private final IDictTypeService dictTypeServiceImpl;

    @Autowired
    public DictDataServiceImpl(IDictTypeService dictTypeServiceImpl) {
        this.dictTypeServiceImpl = dictTypeServiceImpl;
    }

    @Override
    public S<List<DictDataVo>> listDictDataByDictTypeId(Long dictTypeId) {
        List<DictData> dictDatas = list(
                Wrappers.<DictData>lambdaQuery().eq(DictData::getDictTypeId, dictTypeId));
        List<DictDataVo> data = dictDatas.stream().map(dictData -> {
            DictDataVo dictDataVo = new DictDataVo();
            BeanUtils.copyProperties(dictData, dictDataVo);
            return dictDataVo;
        }).toList();
        return S.success(data);
    }

    @Override
    public S<List<DictDataVo>> listDictDataByDictTypeCode(String dictTypeCode) {
        Optional<DictType> dictTypeOp = dictTypeServiceImpl.getOneOpt(
                Wrappers.<DictType>lambdaQuery().eq(DictType::getDictTypeCode, dictTypeCode));
        if (dictTypeOp.isPresent()) {
            List<DictData> dictDatas =
                    list(Wrappers.<DictData>lambdaQuery().eq(DictData::getDictTypeId, dictTypeOp.get().getId()));
            List<DictDataVo> data = dictDatas.stream().map(dictData -> {
                DictDataVo dictDataVo = new DictDataVo();
                BeanUtils.copyProperties(dictData, dictDataVo);
                return dictDataVo;
            }).toList();
            return S.success(data);
        }
        return S.success(new ArrayList<>());
    }

    @Override
    public S<DictDataVo> getDictData(Long id) {
        DictDataVo dictDataVo = new DictDataVo();
        DictData dictData = getById(id);
        BeanUtils.copyProperties(dictData, dictDataVo);
        return S.success(dictDataVo);
    }

    @Override
    public S<Void> updateDictData(UDictDataDto uDictDataDto) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(uDictDataDto, dictData);
        updateById(dictData);
        return S.<Void>success();
    }

    @Override
    public S<Void> saveDictData(SDictDataDto sDictDataDto) {
        //判断标签值不能重复
        Optional<DictData> dictDataOp = getOneOpt(
                Wrappers.<DictData>lambdaQuery()
                        .eq(DictData::getStatus, ENABLE)
                        .eq(DictData::getDictTypeId, sDictDataDto.getDictTypeId())
                        .eq(DictData::getDictLabel, sDictDataDto.getDictValue()));
        //如果存在则抛出异常
        if (dictDataOp.isPresent()) {
            throw new RedundantDataException("枚举值重复");
        }

        DictData dictData = new DictData();
        BeanUtils.copyProperties(sDictDataDto, dictData);
        save(dictData);
        return S.<Void>success();
    }

    @Override
    public S<Void> removeDictData(Long id) {
        removeById(id);
        return S.<Void>success();
    }
}
