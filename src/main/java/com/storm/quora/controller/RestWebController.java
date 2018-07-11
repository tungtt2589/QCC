package com.storm.quora.controller;

import com.storm.quora.common.AjaxResponseBody;
import com.storm.quora.common.GoogleProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestWebController {
    @RequestMapping(value = "/login_google", method = RequestMethod.POST)
    public ResponseEntity<?> loginGoogle(@RequestBody GoogleProfile profile) {
        AjaxResponseBody result = new AjaxResponseBody();
        result.msg = "Email: " + profile.getEmail();
        return ResponseEntity.ok(result);
    }
}
