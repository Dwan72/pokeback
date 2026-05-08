package com.example.demo.card;

public record CardResponse(
    Long id,
    String setName,
    String cardName,
    String cardNumber,
    String cardMarketSearch,
    String cardMarketAbbr) {
}
