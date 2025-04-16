package com.xiaoxipeng.api.utils;

import com.xiaoxipeng.common.vo.TreeVo;
import com.xiaoxipeng.dict.entity.DictType;

import java.util.List;
import java.util.Objects;

public class TreeUtils<S extends TreeVo<T>, T extends TreeVo<T>> {


    public <O> O buildTree(Long nodeId, List<T> all) {
        all.stream().filter(item -> Objects.equals(item.getParentId(), nodeId)).forEach(item -> {
            item.setChildren(buildTree(item.getId(), all));
        });

        return null;
    }

    public static Boolean recursiveExistsChildren(Long id, List<DictType> all) {
        List<DictType> children = all.stream().filter(item -> Objects.equals(item.getParentId(), id)).toList();
        if (children.isEmpty()) {
            return false;
        }
        for (DictType child : children) {
            recursiveExistsChildren(child.getId(), all);
        }
        return true;
    }

    public static Boolean existsChildren(Long id, List<DictType> all) {
        return all.stream().anyMatch(item -> Objects.equals(item.getParentId(), id));
    }
}
