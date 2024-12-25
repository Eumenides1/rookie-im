package com.rookie.stack.common.domain.dto.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Name：PageBaseResp
 * Author：eumenides
 * Created on: 2024/12/21
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "基础翻页返回")
public class PageBaseResp<T> {

    @Schema(description = "当前页数")
    private Integer pageNo;

    @Schema(description = "每页查询数量")
    private Integer pageSize;

    @Schema(description = "总记录数")
    private Long totalRecords;

    @Schema(description = "是否最后一页")
    private Boolean isLast = Boolean.FALSE;

    @Schema(description = "数据列表")
    private List<T> list;

    public static <T> PageBaseResp<T> empty() {
        PageBaseResp<T> r = new PageBaseResp<>();
        r.setPageNo(1);
        r.setPageSize(0);
        r.setIsLast(true);
        r.setTotalRecords(0L);
        r.setList(new ArrayList<>());
        return r;
    }
    /**
     * 手动传入所有分页信息，包括 isLast。	需要完全控制所有分页参数时使用。
     * @param pageNo
     * @param pageSize
     * @param totalRecords
     * @param isLast
     * @param list
     * @return
     * @param <T>
     */
    public static <T> PageBaseResp<T> init(Integer pageNo, Integer pageSize, Long totalRecords, Boolean isLast, List<T> list) {
        return new PageBaseResp<>(pageNo, pageSize, totalRecords, isLast, list);
    }

    /**
     * 自动计算 isLast。	普通分页场景，数据从查询中直接获取时使用。
     * @param pageNo
     * @param pageSize
     * @param totalRecords
     * @param list
     * @return
     * @param <T>
     */
    public static <T> PageBaseResp<T> init(Integer pageNo, Integer pageSize, Long totalRecords, List<T> list) {
        return new PageBaseResp<>(pageNo, pageSize, totalRecords, isLastPage(totalRecords, pageNo, pageSize), list);
    }

    /**
     * 直接从 MyBatis Plus 的分页对象中初始化分页数据。	使用 MyBatis Plus 进行分页查询时使用，最简便。
     * @param page
     * @return
     * @param <T>
     */
    public static <T> PageBaseResp<T> init(IPage<T> page) {
        return init((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 支持自定义数据列表，同时利用 MyBatis Plus 的分页元数据。	查询后需要对 list 进行数据处理（如转换为 DTO）。
     * @param page
     * @param list
     * @return
     * @param <T>
     */
    public static <T> PageBaseResp<T> init(IPage page, List<T> list) {
        return init((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), list);
    }

    /**
     * 基于已有分页信息更新数据列表。	重用已有分页信息但需要替换数据列表的场景，例如重新加工返回数据。
     * @param resp
     * @param list
     * @return
     * @param <T>
     */
    public static <T> PageBaseResp<T> init(PageBaseResp resp, List<T> list) {
        return init(resp.getPageNo(), resp.getPageSize(), resp.getTotalRecords(), resp.getIsLast(), list);
    }

    /**
     * 是否是最后一页
     */
    public static Boolean isLastPage(long totalRecords, int pageNo, int pageSize) {
        if (pageSize == 0) {
            return false;
        }
        long pageTotal = totalRecords / pageSize + (totalRecords % pageSize == 0 ? 0 : 1);
        return pageNo >= pageTotal ? true : false;
    }
}
