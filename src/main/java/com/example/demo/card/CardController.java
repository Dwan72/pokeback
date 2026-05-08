package com.example.demo.card;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
class CardController {

  private final JdbcTemplate jdbc;

  CardController(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CardResponse create(@RequestBody CardRequest request) {
    String cardMarketSearch = CardMarketSearchFormatter.format(request.cardName(), request.cardNumber());
    String cardMarketAbbr = PokemonCardNormalizer.cardmarketId(request.setName(), request.cardNumber());

    Long id = jdbc.queryForObject(
        """
            INSERT INTO cards (set_name, card_name, card_number, card_market_search, card_market_abbr)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT (card_name, card_number) DO UPDATE
                SET set_name = EXCLUDED.set_name,
                    card_market_search = EXCLUDED.card_market_search,
                    card_market_abbr = EXCLUDED.card_market_abbr
            RETURNING id
            """,
        Long.class,
        request.setName(),
        request.cardName(),
        request.cardNumber(),
        cardMarketSearch,
        cardMarketAbbr);

    return new CardResponse(
        id,
        request.setName(),
        request.cardName(),
        request.cardNumber(),
        cardMarketSearch,
        cardMarketAbbr);
  }

  @PostMapping("/batch")
  @Transactional
  @ResponseStatus(HttpStatus.CREATED)
  List<CardResponse> createBatch(@RequestBody List<CardRequest> requests) {
    return requests.stream()
        .map(this::create)
        .filter(response -> response.id() != null)
        .toList();
  }
}
