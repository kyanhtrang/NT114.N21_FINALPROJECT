import 'package:final_project/main.dart';
import 'package:final_project/src/utils/theme/widget_themes/elevated_button_theme.dart';
import 'package:final_project/src/utils/theme/widget_themes/outlined_button_theme.dart';
import 'package:final_project/src/utils/theme/widget_themes/text_theme.dart';
import 'package:flutter/material.dart';

class TAppTheme{
  TAppTheme._();

  static ThemeData lightTheme = ThemeData(
    brightness: Brightness.light,
    textTheme: TTextTheme.lightTextTheme,
    outlinedButtonTheme: TOutlineButtonTheme.lightOutlinedButtonTheme,
    elevatedButtonTheme: TElevatedButtonTheme.lightElevatedButtonTheme
  );

  static ThemeData darkTheme = ThemeData(
    brightness: Brightness.dark,
    textTheme: TTextTheme.darkTextTheme,
    outlinedButtonTheme: TOutlineButtonTheme.darkOutlinedButtonTheme,
    elevatedButtonTheme: TElevatedButtonTheme.darkElevatedButtonTheme
  );
}
