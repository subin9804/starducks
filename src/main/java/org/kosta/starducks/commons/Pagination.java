package org.kosta.starducks.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이지네이션
 *
 *
 * @param <T>
 */
@Getter @Setter @ToString
public class Pagination<T> {

    private int page; // 현재 페이지
    private long total; // 전체 레코드 수
    private int prev; // 전 구간 마지막 페이지
    private int next; // 다음 구간 마지막 페이지
    private int lastPage; // 마지막 페이지
    private int pageCnt; // 구간별 페이지 갯수
    private int pagePerCnt; // 1 페이지당 레코드 수
    private boolean isFirstCnt; // 첫 번째 구간 여부
    private boolean isLastCnt; // 마지막 구간 여부
    private List<Integer> pages; // 구간별 페이지 번호
    private String baseUrl;

    public Pagination(Page<T> data, String url) {
        this(data.getNumber() + 1, data.getTotalElements(), data.getSize(), url);
    }

    public Pagination(int page, long total, String url) {
        this(page, total, 20, url);
    }

    public Pagination(int _page, long _total, int _pagePerCnt, String url) {
        total = _total;
        pagePerCnt = _pagePerCnt;
        page = _page <= 0 ? 1 : page;

        if (total < 1) {
            return;
        }

        baseUrl = url;
        baseUrl = baseUrl == null ? "" : baseUrl;

        this.pageCnt = pageCnt < 1 ? 10 : pageCnt; // 페이지 구간이 0 이하 인 경우는 기본값 10 지정
        this.lastPage = (int) Math.ceil(total / (double) this.pagePerCnt);  // 마지막 페이지

        page = page < 1 ? 1 : page; // 페이지가 0 이하 일 경우 1로 고정
        if (page > this.lastPage) page = this.lastPage; // 페이지가 마지막 페이지보다 크다면 마지막 페이지로 고정

        pages = new ArrayList<>();


        /** 페이지 구간 구하기 S */
        int cnt = (int) Math.ceil(this.page / (double) this.pageCnt) - 1; // 현재 페이지 구간 번호
        int lastCnt = (int) Math.ceil(this.lastPage / (double) this.pageCnt) - 1; // 마지막 페이지 구간 번호

        if (cnt == 0) this.isFirstCnt = true;  // 첫번째 페이지 구간 체크
        if (cnt == lastCnt) this.isLastCnt = true; // 마지막 페이지 구간 체크
        /** 페이지 구간 구하기 E */

        /** 구간별 페이지 번호 S */
        int start = cnt * this.pageCnt + 1;
        for (int i = start; i < start + this.pageCnt; i++) {
            pages.add(i);

            if (i == this.lastPage) { // 마지막 페이지에서 멈춤
                break;
            }
        }
        /** 구간별 페이지 번호 E */

        /** 전 구간 마지막 페이지 S */
        if (!this.isFirstCnt) {
            prev = cnt * this.pageCnt;
        }
        /** 전 구간 마지막 페이지  E */
        /** 다음 구간 시작 페이지 S */
        if (!this.isLastCnt) {
            next = (cnt + 1) * this.pageCnt + 1;
        }
        /** 다음 구간 시작 페이지  E */
    }
}