package com.example.demo.card;

final class PokemonCardNormalizer {

  private PokemonCardNormalizer() {
  }

  /**
   * Produces a Cardmarket-style identifier for a Pokémon card.
   *
   * Pokémon card numbers are formatted as "NNN/TTT" (e.g. "004/165").
   * Cardmarket identifies cards by set abbreviation + sequence number,
   * e.g. "151-004".
   *
   * Examples:
   * ("151", "004/165") → "151-004"
   * ("Obsidian Flames","194/197") → "OBF-194"
   * ("Paldea Evolved", "093/193") → "PAL-093"
   * ("sv6pt5", "012/131") → "sv6pt5-012"
   */
  static String cardmarketId(String setName, String cardNumber) {
    String sequenceNumber = sequenceNumber(cardNumber);
    String abbreviation = setAbbreviation(setName);
    return abbreviation + "-" + sequenceNumber;
  }

  /**
   * Strips the "/total" suffix so "004/165" becomes "004".
   * Returns the number unchanged if there is no slash.
   */
  static String sequenceNumber(String cardNumber) {
    int slash = cardNumber.indexOf('/');
    return slash >= 0
        ? cardNumber.substring(0, slash).strip()
        : cardNumber.strip();
  }

  /**
   * Derives a Cardmarket set abbreviation from a set name.
   *
   * Rules (Pokémon-specific):
   * 1. Numeric-only set names (e.g. "151") are kept as-is.
   * 2. Already-abbreviated codes with digits (e.g. "sv6pt5") are kept as-is.
   * 3. Multi-word names produce a 3-letter acronym from the first three words
   * (e.g. "Obsidian Flames" → "OBF", "Paldea Evolved" → "PAL").
   */
  static String setAbbreviation(String setName) {
    String trimmed = setName.strip();

    // Pure number (e.g. "151")
    if (trimmed.matches("\\d+")) {
      return trimmed;
    }

    // Already a compact alphanumeric code with digits (e.g. "sv6pt5", "SV3pt5")
    if (trimmed.matches("[A-Za-z0-9]+") && trimmed.matches(".*\\d.*")) {
      return trimmed;
    }

    // Build acronym: take first letter of each word, up to 3, uppercased
    String[] words = trimmed.split("[\\s\\-]+");
    StringBuilder abbrev = new StringBuilder();
    for (int i = 0; i < words.length && abbrev.length() < 3; i++) {
      if (!words[i].isBlank()) {
        abbrev.append(Character.toUpperCase(words[i].charAt(0)));
      }
    }
    return abbrev.toString();
  }

  /**
   * Lowercases and strips all non-alphanumeric characters for fuzzy name matching
   * across sources (e.g. TCGPlayer vs Cardmarket spelling differences).
   *
   * Example: "Charizard ex" → "charizardex"
   */
  static String normalizeSearchName(String name) {
    return name.toLowerCase().replaceAll("[^a-z0-9]", "");
  }
}
