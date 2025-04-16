package com.xiaoxipeng.api.controller;

import com.xiaoxipeng.api.service.IDictDataService;
import com.xiaoxipeng.common.vo.R;
import com.xiaoxipeng.dict.dto.SDictDataDto;
import com.xiaoxipeng.dict.dto.UDictDataDto;
import com.xiaoxipeng.dict.vo.DictDataVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-14
 */
@Controller
@RequestMapping("/dictData")
public class DictDataController {

    private final IDictDataService dictDataServiceImpl;

    @Autowired
    public DictDataController(IDictDataService dictDataServiceImpl) {
        this.dictDataServiceImpl = dictDataServiceImpl;
    }

    /**
     * 查询字典表详情
     */
    @GetMapping("getDictData/{id}")
    public R<DictDataVo> getDictData(@PathVariable Long id) {
        return R.fromS(dictDataServiceImpl.getDictData(id));
    }

    /**
     * 查询字典表集合
     */
    @GetMapping("getDictData/{dictTypeId}")
    public R<List<DictDataVo>> listDictDataByDictTypeId(@PathVariable Long dictTypeId) {
        return R.fromS(dictDataServiceImpl.listDictDataByDictTypeId(dictTypeId));
    }

    /**
     * 查询字典表集合
     */
    @GetMapping("getDictData/{dictTypeCode}")
    public R<List<DictDataVo>> listDictDataByDictTypeCode(@PathVariable String dictTypeCode) {
        return R.fromS(dictDataServiceImpl.listDictDataByDictTypeCode(dictTypeCode));
    }

    /**
     *  字典表更新
     */
    @PostMapping("updateDictData")
    public R<Void> updateDictData(@Valid @RequestBody UDictDataDto UDictDataDto) {
        return R.fromS(dictDataServiceImpl.updateDictData(UDictDataDto));
    }

    /**
     *  字典表新增
     */
    @PostMapping("saveDictData")
    public R<Void> saveDictData(@Valid @RequestBody SDictDataDto sDictDataDto) {
        return R.fromS(dictDataServiceImpl.saveDictData(sDictDataDto));
    }

    /**
     *  字典表删除
     */
    @PostMapping("removeDictData/{id}")
    public R<Void> removeDictData(@PathVariable Long id) {
        return R.fromS(dictDataServiceImpl.removeDictData(id));
    }

}
