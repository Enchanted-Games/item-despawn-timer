package me.whirlfrenzy.itemdespawntimer.util;

public class Conversions {
  public static int hexStringToDecimal(String hexString) {
    return Integer.parseInt(hexString, 16);
  }
  public static String decimalToHexString(int decimal) {
    return Integer.toHexString(decimal);
  }

  public static int hexColourToDecimal(String hexColour) {
    return hexStringToDecimal( hexColour.replace("#", "") );
  }
  public static String decimalToHexColour(int decimal) {
    return "#" + decimalToHexString(decimal);
  }
}
