package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.commons.MenuService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.mypage.dto.ConfBookDto;
import org.kosta.starducks.mypage.dto.Room;
import org.kosta.starducks.mypage.entity.ConfRoom;
import org.kosta.starducks.mypage.entity.ConfRoomEN;
import org.kosta.starducks.mypage.service.ConfRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/conf")
public class ConfController {

    private final ConfRoomService service;
    private final EmpRepository empRepository;
    private final HttpServletRequest request;


    @GetMapping
    public String index(Model model, @AuthenticationPrincipal CustomUserDetails details) {
        MenuService.commonProcess(request, model, "mypage");

        // 유저 정보 받아오기
        if(details != null) {
            Employee emp = details.getEmployee();

            // 화면에 전달
            model.addAttribute("emp", emp);
        }

        model.addAttribute("rooms", ConfRoomEN.values());
        return "mypage/confRoom/confList";
    }

    /**
     * 예약 리스트 api
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<ConfRoom>> list() {
        List<ConfRoom> rooms = service.getDayList(LocalDate.parse("2023-12-07"));

        return ResponseEntity.ok().body(rooms);
    }

    @GetMapping("/room")
    @ResponseBody
    public ResponseEntity<List<Room>> room() {

        List<Room> rooms = new ArrayList();
        ConfRoomEN[] values = ConfRoomEN.values();

        for (int i = 0; i < values.length; i++) {
            Room room = new Room();
            room.setId(i + 1);
            room.setTitle(String.valueOf(values[i]));
            rooms.add(room);
        }

        return ResponseEntity.ok().body(rooms);
    }

    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<ConfRoom> booking(@RequestBody ConfBookDto dto, Authentication auth) {
        System.out.println("디티오: "+ dto);
        ConfRoom savedBooking = service.booking(dto, auth);

        return ResponseEntity.ok(savedBooking);
    }
}
