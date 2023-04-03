package dev.rickelmedias.apismsemail.dtos.models;

import dev.rickelmedias.apismsemail.enums.PinTypeEnum;

public record SendPinDTO(PinTypeEnum type, String document) {

}
