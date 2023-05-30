package com.group3.tofu.employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group3.tofu.employee.model.EmployeeService;

@Controller
public class LoginController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/login_emp")
	public String showLoginPage() {
		return "employee/login";
	}
	
	// add request mapping for /access-denied
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "employee/access-denied";
    }
    
    //取得使用者的權限
    @ResponseBody
    @GetMapping("/info/employee/role")
    public List<String> getUserRole(Authentication authentication) {
        List<String> roles = new ArrayList<>();
        authentication.getAuthorities().forEach(authority -> {
            roles.add(authority.getAuthority());
        });
        
        return roles;
    }
    
    //取得使用者的名字
    @ResponseBody
    @GetMapping("/info/employee/user")
    public Map<String, String> getUserName(Authentication authentication) {
        Map<String, String> user = new HashMap<>();
        user.put("name", authentication.getName());
        
        return user;
    }
    
    //取得使用者的ID
    @ResponseBody
    @GetMapping("/info/employee/userId")
    public Map<String, Integer> getUserId(Authentication authentication) {
        Map<String, Integer> user = new HashMap<>();
        user.put("id", employeeService.findIdByName(authentication.getName()));
        
        return user;
    }
    
    
    
    
}
