package com.rookie.stack.im.domain.vo.req.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Name：PageBaseReq
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
@Data
@Schema(description = "翻页请求的基础请求体")
public class PageBaseReq {

    @Schema(description = "页面大小，默认值为 10")
    @Min(0)
    @Max(50)
    private Integer pageSize = 10;

    @Schema(description = "页面索引（从 1 开始）")
    private Integer pageNo = 1;

    /**
     * 获取mybatisPlus的page
     * @return
     */
    public Page plusPage() {
        return new Page(pageNo, pageSize);
    }
}
