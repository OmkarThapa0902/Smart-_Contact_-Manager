/*package com.smart.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.EmailService;
import com.smartdao.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
    Random random = new Random();

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    // Email id form open handler
    @RequestMapping("/forgot")
    public String openEmailForm() {
        return "forgot_email_form";
    }

    // Send OTP
    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email, HttpSession session) {
        System.out.println("EMAIL: " + email);

        // Generating OTP of 6 digits
        int otp = random.nextInt(900000) + 100000;
        System.out.println("OTP: " + otp);

        // Write code for sending OTP to email
        String subject = "OTP From SCM";
        String message = "<h1> OTP= " + otp + " </h1>";
        String to = email;

        boolean flag = this.emailService.sendEmail(subject, message, to);

        if (flag) {
            session.setAttribute("otp", otp); // Storing OTP in session
            session.setAttribute("message", new Message("We have sent OTP to your email.", "success"));
            return "verify_otp";
            
        } else {
            session.setAttribute("message", new Message("Check your email id !!", "danger"));
            return "forgot_email_form";
        }
    }
    //verify-otp
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") int otp,HttpSession session) {
    	int myOtp=(int)session.getAttribute("myotp");
    	String email=(String)session.getAttribute("email");
    	if(myOtp==otp) {
    		//Password change form
    		User user = this.userRepository.getUserByUserName(email);
    		 if (user==null) {
    			 //send error message
    			 session.setAttribute("message", "User does not exit with this email !!");
    	            return "forgot_email_form";
    			 
    		 }else {
    			 //send change password form
    			 
    		 }
    		 
    		return"password_change_form";
    	}else {
    		session.setAttribute("message","You have entered wrong otp !!");
    		return"verify_otp";
    	}
    
    }
    
    //change password
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword") String newpassword,HttpSession session) {
    	String email=(String)session.getAttribute("email");
    	User user=this.userRepository.getUserByUserName(email);
    	user.setPassword(this.bcrypt.encode(newpassword));
    	this.userRepository.save(user);
    	
        return "redirect:/signin?change=password changed successfully";
    	
    }
}*/


package com.smart.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.EmailService;
import com.smartdao.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
   

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    // Email id form open handler
    @RequestMapping("/forgot")
    public String openEmailForm() {
        return "forgot_email_form";
    }

    // Send OTP
    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email, HttpSession session) {
        System.out.println("EMAIL: " + email);

        // Generating OTP of 6 digits
        Random random = new Random(100000);
        int otp = random.nextInt(900000);
        System.out.println("OTP: " + otp);

        // Write code for sending OTP to email
        String subject = "OTP From SCM";
        String message = "<h1> OTP= " + otp + " </h1>";
        String to = email;

        boolean flag = this.emailService.sendEmail(subject, message, to);

        if (flag) {
            session.setAttribute("otp", otp); // Storing OTP in session
            session.setAttribute("email", email); // Storing email in session
            session.setAttribute("message", new Message("We have sent OTP to your email.", "success"));
            return "verify_otp";
            
        } else {
            session.setAttribute("message", new Message("Check your email id !!", "danger"));
            return "forgot_email_form";
        }
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") int otp, HttpSession session) {
        int sessionOtp = (int) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");

        if (sessionOtp == otp) {
            // Password change form
            User user = this.userRepository.getUserByUserName(email);
            if (user == null) {
                // Send error message
                session.setAttribute("message", "User does not exist with this email !!");
                return "forgot_email_form";
            } else {
                // Send change password form
                return "password_change_form";
            }
        } else {
            session.setAttribute("message", "You have entered the wrong OTP !!");
            return "verify_otp";
        }
    }

    // Change password
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword") String newpassword, HttpSession session) {
        String email = (String) session.getAttribute("email");
        User user = this.userRepository.getUserByUserName(email);
        user.setPassword(this.bcrypt.encode(newpassword));
        this.userRepository.save(user);

        return "redirect:/signin?change=Password changed successfully";
    }
}

