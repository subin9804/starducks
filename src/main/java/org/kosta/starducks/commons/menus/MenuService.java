package org.kosta.starducks.commons.menus;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
////

/**
 * 서브메뉴 조회
 */
public class MenuService {


    // 이름       코드      하위메뉴
    // 마이페이지  mypage    attendance, schedule, conf
    // 전자결재   document   mydocu, createDoc, recieved, tempsaved
    // 전사게시판  forum
    // 인사부     hr        emp(사원관리), attend, vacation, dept, docu
    // 물류유통부 logistic   store(입고), release(출고), inventory(재고)
    // 총무부     general   adschedule, adforum, adconf, adproduct
    // 재무부     fina      point(지점), vendor(거래처)


    /**
     * 상위 메뉴에 따른 하위 메뉴 조회
     *
     * @param code 상위 메뉴 코드
     * @return
     */
    public static List<MenuDetail> gets(String code) {
//        System.out.println("code" + code);
        List<MenuDetail> menus = new ArrayList<>();

        // 게시판 하위 메뉴
        switch (code) {
            case "mypage":
                menus.add(new MenuDetail("attendance", "근태관리", "/mypage/attendance"));
                menus.add(new MenuDetail("schedule", "일정관리", "/mypage/schedule/show"));
                menus.add(new MenuDetail("conf", "회의실 예약", "/mypage/conf"));
                break;
            case "document":
                menus.add(new MenuDetail("createDoc", "결재문서 작성", "/document/createDoc"));
                menus.add(new MenuDetail("submitDoc", "결재 상신함", "/document/submitDoc"));
                menus.add(new MenuDetail("receiveDoc", "결재 수신함", "/document/receiveDoc"));
                menus.add(new MenuDetail("tempList", "임시저장함", "/document/tempList"));
                break;
            case "hr":
                menus.add(new MenuDetail("emp", "사원 관리", "/hr/emp"));
                menus.add(new MenuDetail("attend", "근태 관리", "/hr/attend"));
//                menus.add(new MenuDetail("vacation", "휴가 관리", "/"));
                menus.add(new MenuDetail("dept", "부서 관리", "/hr/dept"));
//                menus.add(new MenuDetail("docu", "문서 관리", "/"));
                break;
            case "logistic":
                menus.add(new MenuDetail("inbound", "입고 관리", "/logistic/inbound/warehouse/list"));
                menus.add(new MenuDetail("outbound", "출고 관리", "/logistic/outbound/warehouse/list"));
                menus.add(new MenuDetail("stock", "재고 관리", "/logistic/stock/warehouse/list"));
                break;
            case "general":
//                menus.add(new MenuDetail("schedule", "전사 일정 관리", "/general/schedule"));
//                menus.add(new MenuDetail("adforum", "게시판 관리", "/"));
//                menus.add(new MenuDetail("adconf", "회의실 관리", "/"));
                menus.add(new MenuDetail("products", "품목 관리", "/general/products/list"));
                break;
            case "fina" :
                menus.add(new MenuDetail("store", "지점 정보 관리", "/fina/store/list"));
                menus.add(new MenuDetail("vendor", "거래처 정보 관리", "/fina/vendor/list"));
                break;
            default:
                menus.add(new MenuDetail("attendance", "근태관리", "/mypage/attendance"));
                menus.add(new MenuDetail("schedule", "일정관리", "/mypage/schedule"));
                menus.add(new MenuDetail("confroom", "회의실 예약", "/mypage/conf"));
        }
//        if (code.equals("mypage")) {
//        } else if (code.equals("document")) {
//        } else if (code.equals("hr")) {
//        } else if (code.equals("logistic")) {
//        } else if (code.equals("general")) {
//        } else if (code.equals("fina")) {
//        }

        return menus;
    }

    public static String getSubMenuCode(HttpServletRequest request) {
        String URI = request.getRequestURI();
//        System.out.println("uri: " + URI);
        return URI.substring(URI.indexOf("/") + 1);
    }
}