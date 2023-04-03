package dev.rickelmedias.apismsemail.controllers;

import dev.rickelmedias.apismsemail.dtos.models.SendPinDTO;
import dev.rickelmedias.apismsemail.dtos.models.ValidatePinDTO;
import dev.rickelmedias.apismsemail.services.api.pin.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pin")
public class PinController {

    @Autowired
    PinService pinService;

    @PostMapping(path = "/send")
    public ResponseEntity<?> sendPin(@RequestBody SendPinDTO body) {
        return pinService.sendPin(body);
    }

    @PostMapping(path = "/valid")
    public ResponseEntity<?> validSMS(@RequestBody ValidatePinDTO body) {
        return pinService.validatePin(body);
    }

}
