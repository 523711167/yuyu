package com.xiaoxipeng.api.pojo;

import com.xiaoxipeng.common.entity.Base;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author xiaoxipeng
 * @since 2025-04-09
 */
@Getter
@Setter
@ToString
public class Article extends Base {

    /**
     * 章节
     */
    private String section;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;


    private Long bookId;

}
