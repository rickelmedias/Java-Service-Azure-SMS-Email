package dev.rickelmedias.apismsemail.services.api.pin;

import dev.rickelmedias.apismsemail.dtos.models.SendPinDTO;
import dev.rickelmedias.apismsemail.dtos.models.ValidatePinDTO;
import org.springframework.http.ResponseEntity;

public interface PinService {
    ResponseEntity<?> sendPin(SendPinDTO body);

    ResponseEntity<?> validatePin(ValidatePinDTO body);
}
