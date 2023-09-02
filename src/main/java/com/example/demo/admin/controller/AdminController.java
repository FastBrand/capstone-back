package com.example.demo.admin.controller;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.entity.Admin;
import com.example.demo.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public List<Admin> managers(){
        return adminService.managers();
    }

    @PostMapping("/join")
    public ResponseEntity<AdminDto> create(@RequestBody AdminDto dto) {
        adminService.createAdmin(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<AdminDto> update(@PathVariable Long id, @RequestBody AdminDto dto) {
        AdminDto adminDto = adminService.updateAdmin(id, dto);
        if(adminDto != null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<AdminDto> delete(@PathVariable Long id) {
        AdminDto adminDto = adminService.deleteAdmin(id);
        if(adminDto != null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
