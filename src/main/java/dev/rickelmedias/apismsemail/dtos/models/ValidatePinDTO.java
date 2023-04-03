package dev.rickelmedias.apismsemail.dtos.models;

import dev.rickelmedias.apismsemail.enums.PinTypeEnum;

public record ValidatePinDTO(PinTypeEnum type, String document, int pin) {

}
