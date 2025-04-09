package com.xiaoxipeng.api.service.impl;

import com.xiaoxipeng.api.pojo.Article;
import com.xiaoxipeng.api.mapper.ArticleMapper;
import com.xiaoxipeng.api.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-09
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
