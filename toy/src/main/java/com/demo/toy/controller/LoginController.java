//package com.demo.toy.controller;
//
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.demo.toy.common.JwtTokenProvider;
//
//@RestController
//@RequestMapping("/api/login")
//public class LoginController {
//
//    private final JwtTokenProvider jwtTokenProvider;
////    private final MemberService memberService; // DB에서 사용자 확인용
//    
//    public LoginController(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
//        this.jwtTokenProvider = jwtTokenProvider;
////        this.memberService = memberService;
//    }
//    
//
//    @PostMapping
//    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
//        String id = loginData.get("id");
//        String pw = loginData.get("pw");
//
//        boolean valid = memberService.checkLogin(id, pw);
//        if (!valid) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        String token = jwtTokenProvider.createToken(id);
//        return ResponseEntity.ok(Collections.singletonMap("token", token));
//    }
//}
