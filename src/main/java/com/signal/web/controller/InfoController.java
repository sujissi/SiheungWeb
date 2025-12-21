package com.signal.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InfoController {

    @GetMapping("/info/notice/{key}")
    public String noticeDetail(@PathVariable String key, Model model) {
        if ("open".equals(key)) {
            model.addAttribute("title", "[안내] 시:그널 서비스 오픈 안내");
            model.addAttribute("date", "2024-12-14");
            model.addAttribute("content",
                    "시:그널 서비스가 정식 오픈했습니다. 시민 여러분의 생활 속 불편을 빠르게 접수하고 공유할 수 있도록\n" +
                    "민원 등록, 공감, 댓글 기능을 제공하고 있습니다.\n\n" +
                    "지속적으로 기능을 개선해 나가겠습니다. 많은 이용 부탁드립니다.");
        } else if ("maintenance".equals(key)) {
            model.addAttribute("title", "[점검] 서버 점검 예정 (12/10)");
            model.addAttribute("date", "2024-12-10");
            model.addAttribute("content",
                    "안정적인 서비스 운영을 위해 서버 점검을 진행합니다.\n\n" +
                    "점검 시간: 2024-12-10 02:00 ~ 04:00\n" +
                    "영향 범위: 민원 등록/조회 일부 지연 가능");
        } else if ("parking-guide".equals(key)) {
            model.addAttribute("title", "[안내] 불법 주정차 신고 가이드");
            model.addAttribute("date", "2024-12-01");
            model.addAttribute("content",
                    "불법 주정차 신고는 도로/교통 카테고리에서 작성해 주세요.\n\n" +
                    "정확한 위치, 위반 차량 특징, 현장 사진을 첨부하면 처리가 더 빠릅니다.");
        } else {
            model.addAttribute("title", "공지사항을 찾을 수 없습니다.");
            model.addAttribute("date", "");
            model.addAttribute("content", "요청하신 공지사항이 존재하지 않습니다.");
        }
        return "notice-detail";
    }

    @GetMapping("/info/faq/{key}")
    public String faqDetail(@PathVariable String key, Model model) {
        if ("processing-time".equals(key)) {
            model.addAttribute("title", "Q. 민원 처리는 얼마나 걸리나요?");
            model.addAttribute("content",
                    "접수된 민원은 보통 3~7일 내에 처리됩니다.\n" +
                    "민원의 종류와 현장 상황에 따라 처리 기간이 달라질 수 있습니다.");
        } else if ("likes".equals(key)) {
            model.addAttribute("title", "Q. 공감 수는 어떤 역할을 하나요?");
            model.addAttribute("content",
                    "공감 수가 높은 민원은 우선순위 참고 지표로 활용됩니다.\n" +
                    "많은 시민이 공감한 민원일수록 더 빠르게 검토됩니다.");
        } else if ("edit-delete".equals(key)) {
            model.addAttribute("title", "Q. 민원 수정/삭제는 어떻게 하나요?");
            model.addAttribute("content",
                    "민원 상세 페이지에서 수정/삭제 버튼을 사용할 수 있습니다.\n" +
                    "처리 상태가 완료된 민원은 수정이 제한될 수 있습니다.");
        } else {
            model.addAttribute("title", "FAQ를 찾을 수 없습니다.");
            model.addAttribute("content", "요청하신 FAQ가 존재하지 않습니다.");
        }
        return "faq-detail";
    }
}
