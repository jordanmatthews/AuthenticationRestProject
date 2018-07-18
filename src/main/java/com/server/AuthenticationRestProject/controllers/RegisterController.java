package com.server.AuthenticationRestProject.controllers;

import com.nulabinc.zxcvbn.Zxcvbn;
import com.server.AuthenticationRestProject.models.User;
import com.server.AuthenticationRestProject.services.EmailService;
import com.server.AuthenticationRestProject.services.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping(value = IBaseController.BASE_API_PATH)
public class RegisterController implements IBaseController {

    private final EmailService emailService;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterController(EmailService emailService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
        this.userService = userService;
    }

    // Process form input data
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> processRegistrationForm(@RequestBody User user, @RequestParam Map requestParams, HttpServletRequest request) {
        //TODO turn on again
        /*if (new Zxcvbn().measure(user.getPassword()).getScore() < 3) {
            return new ResponseEntity<>("Password not acceptable", HttpStatus.NOT_ACCEPTABLE);
        }
        */
        User userExists = userService.getUserByUserName(user.getEmail());
        if (userExists != null) {
            return new ResponseEntity<>(new JSONObject().put("error", "Unable to register with email " + user.getEmail() + ", email already exists."), HttpStatus.CONFLICT);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // Disable user until they click on confirmation link in email
        user.setEnabled(false);
        user.setUsername(user.getEmail());
        // Generate random 36-character string token for confirmation link
        user.setConfirmationToken(UUID.randomUUID().toString());
        userService.save(user);

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080/api/v1";

        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + user.getConfirmationToken());
        registrationEmail.setFrom("noreply@domain.com");

        emailService.sendEmail(registrationEmail);

        return new ResponseEntity<>(new JSONObject().put("message", "A confirmation e-mail has been sent to " + user.getEmail()), HttpStatus.OK);
    }

    // Process confirmation link
    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ResponseEntity<?> showConfirmationPage(@RequestParam("token") String token, HttpServletResponse response) throws IOException {

        User user = userService.findByConfirmationToken(token);

        if (user == null) { // No token found in DB
            return new ResponseEntity(new JSONObject().put("error", "Invalid token, the link is not valid"), HttpStatus.NOT_FOUND);
        }
        user.setEnabled(true);
        userService.save(user);

        response.sendRedirect("http://localhost:3000");

        return new ResponseEntity<>(new JSONObject().put("message", "User email has been confirmed"), HttpStatus.OK);
    }
}