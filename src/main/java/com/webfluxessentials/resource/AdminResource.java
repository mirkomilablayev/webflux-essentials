package com.webfluxessentials.resource;

import com.webfluxessentials.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AdminResource {

    private final AdminService adminService;

}
