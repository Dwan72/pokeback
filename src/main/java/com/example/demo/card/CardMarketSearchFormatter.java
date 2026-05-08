package com.example.demo.card;

/**
 * Builds {@code card_market_search} from {@code card_name} + collector number —
 * no set abbreviations.
 *
 * <p>
 * Removes parenthetical segments (including the parentheses), normalizes
 * whitespace, lowercases
 * the name, then appends a space and only the digits taken from the portion of
 * {@code card_number}
 * before the first slash.
 * </p>
 *
 * <p>
 * Example: {@code Espeon V (Alternate Full Art)} + {@code 180/203} →
 * {@code espeon v 180}
 * </p>
 */
final class CardMarketSearchFormatter {

  private CardMarketSearchFormatter() {
  }

  static String format(String cardName, String cardNumber) {
    String withoutParens = cardName.replaceAll("\\([^)]*\\)", "");
    String cleaned = withoutParens.replaceAll("\\s+", " ").strip().toLowerCase();
    String digits = digitsBeforeSlash(cardNumber);
    if (digits.isEmpty()) {
      return cleaned;
    }
    return cleaned.isEmpty() ? digits : cleaned + " " + digits;
  }

  /** Digits only from the collector prefix before the first {@code /}. */
  private static String digitsBeforeSlash(String cardNumber) {
    int slash = cardNumber.indexOf('/');
    String prefix = slash >= 0 ? cardNumber.substring(0, slash) : cardNumber;
    return prefix.replaceAll("[^0-9]", "");
  }
}
