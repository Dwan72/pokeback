package com.example.demo.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

final class PokemonCardNormalizer {

  /**
   * Official English expansion abbreviations (Bulbapedia "Set abb."), keyed by
   * {@link #normalizeSetLookupKey(String)}.
   * Unknown sets fall back to {@link #setAbbreviationHeuristic(String)}.
   */
  private static final Map<String, String> KNOWN_SET_ABBREVIATIONS;

  static {
    Map<String, String> m = new HashMap<>();
    m.put("151", "MEW");
    m.put("ancient origins", "AOR");
    m.put("aquapolis", "AQ");
    m.put("arceus", "AR");
    m.put("ascended heroes", "ASC");
    m.put("astral radiance", "ASR");
    m.put("base set", "BS");
    m.put("base set 2", "B2");
    m.put("battle styles", "BST");
    m.put("black & white", "BLW");
    m.put("black & white-boundaries crossed", "BCR");
    m.put("black & white-dark explorers", "DEX");
    m.put("black & white-dragons exalted", "DRX");
    m.put("black & white-emerging powers", "EPO");
    m.put("black & white-legendary treasures", "LTR");
    m.put("black & white-next destinies", "NXD");
    m.put("black & white-noble victories", "NVI");
    m.put("black & white-plasma blast", "PLB");
    m.put("black & white-plasma freeze", "PLF");
    m.put("black & white-plasma storm", "PLS");
    m.put("black bolt", "BLK");
    m.put("boundaries crossed", "BCR");
    m.put("breakpoint", "BKP");
    m.put("breakthrough", "BKT");
    m.put("brilliant stars", "BRS");
    m.put("burning shadows", "BUS");
    m.put("celebrations", "CEL");
    m.put("celestial storm", "CES");
    m.put("champion's path", "CPA");
    m.put("chaos rising", "CRI");
    m.put("chilling reign", "CRE");
    m.put("cosmic eclipse", "CEC");
    m.put("crimson invasion", "CIN");
    m.put("crown zenith", "CRZ");
    m.put("dark explorers", "DEX");
    m.put("darkness ablaze", "DAA");
    m.put("destined rivals", "DRI");
    m.put("detective pikachu", "DET");
    m.put("diamond & pearl", "DP");
    m.put("diamond & pearl-great encounters", "GE");
    m.put("diamond & pearl-legends awakened", "LA");
    m.put("diamond & pearl-majestic dawn", "MD");
    m.put("diamond & pearl-mysterious treasures", "MT");
    m.put("diamond & pearl-secret wonders", "SW");
    m.put("diamond & pearl-stormfront", "SF");
    m.put("double crisis", "DCR");
    m.put("dragon majesty", "DRM");
    m.put("dragon vault", "DRV");
    m.put("dragons exalted", "DRX");
    m.put("emerging powers", "EPO");
    m.put("evolutions", "EVO");
    m.put("evolving skies", "EVS");
    m.put("ex crystal guardians", "CG");
    m.put("ex delta species", "DS");
    m.put("ex deoxys", "DX");
    m.put("ex dragon", "DR");
    m.put("ex dragon frontiers", "DF");
    m.put("ex emerald", "EM");
    m.put("ex firered & leafgreen", "RG");
    m.put("ex hidden legends", "HL");
    m.put("ex holon phantoms", "HP");
    m.put("ex legend maker", "LM");
    m.put("ex power keepers", "PK");
    m.put("ex ruby & sapphire", "RS");
    m.put("ex sandstorm", "SS");
    m.put("ex team magma vs team aqua", "MA");
    m.put("ex team rocket returns", "TRR");
    m.put("ex unseen forces", "UF");
    m.put("expedition base set", "EX");
    m.put("fates collide", "FCO");
    m.put("flashfire", "FLF");
    m.put("forbidden light", "FLI");
    m.put("fossil", "FO");
    m.put("furious fists", "FFI");
    m.put("fusion strike", "FST");
    m.put("generations", "GEN");
    m.put("great encounters", "GE");
    m.put("guardians rising", "GRI");
    m.put("gym challenge", "G2");
    m.put("gym heroes", "G1");
    m.put("heartgold & soulsilver", "HS");
    m.put("hidden fates", "HIF");
    m.put("hs-triumphant", "TM");
    m.put("hs-undaunted", "UD");
    m.put("hs-unleashed", "UL");
    m.put("journey together", "JTG");
    m.put("jungle", "JU");
    m.put("kalos starter set", "KSS");
    m.put("legendary collection", "LC");
    m.put("legendary treasures", "LTR");
    m.put("legends awakened", "LA");
    m.put("lost origin", "LOR");
    m.put("lost thunder", "LOT");
    m.put("majestic dawn", "MD");
    m.put("mega evolution", "MEG");
    m.put("mega evolution-ascended heroes", "ASC");
    m.put("mega evolution-chaos rising", "CRI");
    m.put("mega evolution-perfect order", "POR");
    m.put("mega evolution-phantasmal flames", "PFL");
    m.put("mysterious treasures", "MT");
    m.put("neo destiny", "N4");
    m.put("neo discovery", "N2");
    m.put("neo genesis", "N1");
    m.put("neo revelation", "N3");
    m.put("next destinies", "NXD");
    m.put("noble victories", "NVI");
    m.put("obsidian flames", "OBF");
    m.put("paldea evolved", "PAL");
    m.put("paldean fates", "PAF");
    m.put("paradox rift", "PAR");
    m.put("perfect order", "POR");
    m.put("phantasmal flames", "PFL");
    m.put("phantom forces", "PHF");
    m.put("plasma blast", "PLB");
    m.put("plasma freeze", "PLF");
    m.put("plasma storm", "PLS");
    m.put("platinum", "PL");
    m.put("platinum-arceus", "AR");
    m.put("platinum-rising rivals", "RR");
    m.put("platinum-supreme victors", "SV");
    m.put("pokémon tcg: call of legends", "CL");
    m.put("pokémon tcg: pokémon go", "PGO");
    m.put("primal clash", "PRC");
    m.put("prismatic evolutions", "PRE");
    m.put("rebel clash", "RCL");
    m.put("rising rivals", "RR");
    m.put("roaring skies", "ROS");
    m.put("scarlet & violet", "SVI");
    m.put("scarlet & violet-151", "MEW");
    m.put("scarlet & violet-black bolt", "BLK");
    m.put("scarlet & violet-destined rivals", "DRI");
    m.put("scarlet & violet-journey together", "JTG");
    m.put("scarlet & violet-obsidian flames", "OBF");
    m.put("scarlet & violet-paldea evolved", "PAL");
    m.put("scarlet & violet-paldean fates", "PAF");
    m.put("scarlet & violet-paradox rift", "PAR");
    m.put("scarlet & violet-prismatic evolutions", "PRE");
    m.put("scarlet & violet-shrouded fable", "SFA");
    m.put("scarlet & violet-stellar crown", "SCR");
    m.put("scarlet & violet-surging sparks", "SSP");
    m.put("scarlet & violet-temporal forces", "TEF");
    m.put("scarlet & violet-twilight masquerade", "TWM");
    m.put("scarlet & violet-white flare", "WHT");
    m.put("secret wonders", "SW");
    m.put("shining fates", "SHF");
    m.put("shining legends", "SLG");
    m.put("shrouded fable", "SFA");
    m.put("silver tempest", "SIT");
    m.put("skyridge", "SK");
    m.put("steam siege", "STS");
    m.put("stellar crown", "SCR");
    m.put("stormfront", "SF");
    m.put("sun & moon", "SUM");
    m.put("sun & moon-burning shadows", "BUS");
    m.put("sun & moon-celestial storm", "CES");
    m.put("sun & moon-cosmic eclipse", "CEC");
    m.put("sun & moon-crimson invasion", "CIN");
    m.put("sun & moon-forbidden light", "FLI");
    m.put("sun & moon-guardians rising", "GRI");
    m.put("sun & moon-lost thunder", "LOT");
    m.put("sun & moon-team up", "TEU");
    m.put("sun & moon-ultra prism", "UPR");
    m.put("sun & moon-unbroken bonds", "UNB");
    m.put("sun & moon-unified minds", "UNM");
    m.put("supreme victors", "SV");
    m.put("surging sparks", "SSP");
    m.put("sword & shield", "SSH");
    m.put("sword & shield-astral radiance", "ASR");
    m.put("sword & shield-battle styles", "BST");
    m.put("sword & shield-brilliant stars", "BRS");
    m.put("sword & shield-chilling reign", "CRE");
    m.put("sword & shield-darkness ablaze", "DAA");
    m.put("sword & shield-evolving skies", "EVS");
    m.put("sword & shield-fusion strike", "FST");
    m.put("sword & shield-lost origin", "LOR");
    m.put("sword & shield-rebel clash", "RCL");
    m.put("sword & shield-silver tempest", "SIT");
    m.put("sword & shield-vivid voltage", "VIV");
    m.put("team rocket", "TR");
    m.put("team up", "TEU");
    m.put("temporal forces", "TEF");
    m.put("triumphant", "TM");
    m.put("twilight masquerade", "TWM");
    m.put("ultra prism", "UPR");
    m.put("unbroken bonds", "UNB");
    m.put("undaunted", "UD");
    m.put("unified minds", "UNM");
    m.put("unleashed", "UL");
    m.put("vivid voltage", "VIV");
    m.put("white flare", "WHT");
    m.put("xy", "XY");
    m.put("xy-ancient origins", "AOR");
    m.put("xy-breakpoint", "BKP");
    m.put("xy-breakthrough", "BKT");
    m.put("xy-evolutions", "EVO");
    m.put("xy-fates collide", "FCO");
    m.put("xy-flashfire", "FLF");
    m.put("xy-furious fists", "FFI");
    m.put("xy-kalos starter set", "KSS");
    m.put("xy-phantom forces", "PHF");
    m.put("xy-primal clash", "PRC");
    m.put("xy-roaring skies", "ROS");
    m.put("xy-steam siege", "STS");
    KNOWN_SET_ABBREVIATIONS = Collections.unmodifiableMap(m);
  }

  private PokemonCardNormalizer() {
  }

  /**
   * Produces a Cardmarket-style identifier for a Pokémon card.
   *
   * Pokémon card numbers are formatted as "NNN/TTT" (e.g. "004/165").
   * Cardmarket identifies cards by set abbreviation + sequence number,
   * e.g. "151 004".
   *
   * Examples:
   * ("151", "004/165") → "MEW 004" when {@code 151} denotes the Scarlet & Violet
   * 151 expansion
   * ("Obsidian Flames","194/197") → "OBF 194"
   * ("Paldea Evolved", "093/193") → "PAL 093"
   * ("sv6pt5", "012/131") → "sv6pt5 012"
   */
  static String cardmarketId(String setName, String cardNumber) {
    String sequenceNumber = sequenceNumber(cardNumber);
    String abbreviation = setAbbreviation(setName);
    return abbreviation + " " + sequenceNumber;
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
   * Derives a set abbreviation from a set name.
   *
   * <ol>
   * <li>Looks up {@link #KNOWN_SET_ABBREVIATIONS} (Bulbapedia English
   * abbreviations plus common short
   * titles).</li>
   * <li>If absent: pure numeric set names (e.g. actual Pack SKU codes) stay
   * as-is.</li>
   * <li>If absent: compact alphanumeric codes with digits (e.g. {@code sv6pt5})
   * stay as-is.</li>
   * <li>Otherwise builds a 3-letter acronym from the first letters of up to three
   * words.</li>
   * </ol>
   */
  static String setAbbreviation(String setName) {
    String trimmed = setName.strip();
    String mapped = KNOWN_SET_ABBREVIATIONS.get(normalizeSetLookupKey(trimmed));
    if (mapped != null) {
      return mapped;
    }
    return setAbbreviationHeuristic(trimmed);
  }

  private static String setAbbreviationHeuristic(String trimmed) {
    // Pure number — only when not resolved by the known map (e.g. internal product
    // codes).
    if (trimmed.matches("\\d+")) {
      return trimmed;
    }

    // Already a compact alphanumeric code with digits (e.g. "sv6pt5", "SV3pt5")
    if (trimmed.matches("[A-Za-z0-9]+") && trimmed.matches(".*\\d.*")) {
      return trimmed;
    }

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
   * Lowercase, normalize dashes and whitespace for
   * {@link #KNOWN_SET_ABBREVIATIONS} keys.
   */
  private static String normalizeSetLookupKey(String setName) {
    return setName.strip()
        .toLowerCase(Locale.ROOT)
        .replace('\u2014', '-')
        .replace('\u2013', '-')
        .replaceAll("\\s+", " ");
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
