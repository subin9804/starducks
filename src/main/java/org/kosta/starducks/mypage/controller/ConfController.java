package org.kosta.starducks.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/conf")
public class ConfController {

    private final ConfRoomService service;
    private final EmpService empService;
    private final HttpServletRequest request;


    @GetMapping
    public String index(Model model, @AuthenticationPrincipal CustomUserDetails details) {

        Employee emp = empService.getEmp(1L);
        // 유저 정보 받아오기
        if(details != null) {
            emp = details.getEmployee();

        }
        // 화면에 전달
        model.addAttribute("emp", emp);

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
        List<ConfRoom> rooms = service.getAll();

        return ResponseEntity.ok().body(rooms);
    }


    /**
     * 회의실 정보 전달
     * @return
     */
    @GetMapping("/room")
    @ResponseBody
    public ResponseEntity<List<Room>> room() {

        List<Room> rooms = new ArrayList();
        ConfRoomEN[] values = ConfRoomEN.values();

        for (int i = 0; i < values.length; i++) {
            Room room = new Room();
            room.setId(String.valueOf(values[i]));
            room.setTitle(String.valueOf(values[i]));
            rooms.add(room);
        }

        return ResponseEntity.ok().body(rooms);
    }


    /**
     * 예약하기
     * @param dto
     * @param auth
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<ConfRoom> booking(@RequestBody ConfBookDto dto, Authentication auth) {
        System.out.println("디티오: "+ dto);
        ConfRoom savedBooking = service.booking(dto, auth);

        return ResponseEntity.ok(savedBooking);
    }

    /**
     * 예약 수정
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/edit/{id}", consumes = "application/json")
    public ResponseEntity<ConfRoom> editBooking(@PathVariable("id")Long id, @RequestBody ConfBookDto dto) {
        System.out.println("수정한다!!" + dto.toString());

        ConfRoom editedBooking = service.edit(id, dto);

        System.out.println(editedBooking.toString());
        return ResponseEntity.ok(editedBooking);
    }

    @ResponseBody
    @RequestMapping("/delete/{id}")
    public String deleteBooking(@PathVariable("id")Long id) {
        service.remove(id);
        return "삭제되었습니다.";
    }
}
