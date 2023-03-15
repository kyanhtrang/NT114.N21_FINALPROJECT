import 'package:final_project/src/constants/colors.dart';
import 'package:final_project/src/constants/sizes.dart';
import 'package:flutter/material.dart';

class TOutlineButtonTheme {
  TOutlineButtonTheme._();

  static final lightOutlinedButtonTheme = OutlinedButtonThemeData(
      style: OutlinedButton.styleFrom(
        shape: RoundedRectangleBorder(),
        foregroundColor: tSecondaryColor,
        side: BorderSide(color: tSecondaryColor),
        padding: EdgeInsets.symmetric(vertical: tButtonHeight),
        ),
      );

  static final darkOutlinedButtonTheme = OutlinedButtonThemeData(
    style: OutlinedButton.styleFrom(
      shape: RoundedRectangleBorder(),
      foregroundColor: tWhiteColor,
      side: BorderSide(color: tWhiteColor),
      padding: EdgeInsets.symmetric(vertical: tButtonHeight),
    ),
  );
}