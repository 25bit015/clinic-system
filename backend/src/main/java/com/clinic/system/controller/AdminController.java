package com.clinic.system.controller;

import com.clinic.system.entity.DrugInventory;
import com.clinic.system.entity.User;
import com.clinic.system.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<User> users() {
        return adminService.users();
    }

    @GetMapping("/drugs")
    public List<DrugInventory> drugs() {
        return adminService.drugs();
    }

    @GetMapping("/reports/daily")
    public Map<String, Object> dailyReport() {
        return adminService.dailyReport();
    }
}
