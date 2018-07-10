package com.storm.quora.controller;

import com.storm.quora.common.AjaxResponseBody;
import com.storm.quora.common.GoogleProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;
import org.springframework.validation.Errors;

@RestController
public class RestWebController {
    private static final Logger logger = LoggerFactory.getLogger(RestWebController.class);

    @PostMapping(value = "/login_google_2")
    public ResponseEntity<?> loginGoogle(@Valid @RequestBody GoogleProfile profile, Errors errors) {
        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.msg = errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(result);
        }

        try {
            result.msg = "success";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /*@PostMapping(value = "/login_google")
    public ResponseEntity<?> loginGoogle(@Valid @RequestBody GoogleProfile profile, Error error) {
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            result.msg = "success";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }*/

    /*@RequestMapping(value = "/login_google", method = RequestMethod.POST)
    @ResponseBody
    public String loginGoogle(@RequestBody GoogleProfile profile) {
        String email = profile.getEmail();

        // your logic next
        return "A";
    }*/
}
