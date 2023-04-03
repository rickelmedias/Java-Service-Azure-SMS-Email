package dev.rickelmedias.apismsemail.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PinTypeEnum {
    EMAIL("EMAIL"),
    SMS("SMS");

    private String name;
}
