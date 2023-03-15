import 'package:final_project/main.dart';
import 'package:final_project/src/utils/theme/widget_themes/text_theme.dart';
import 'package:flutter/material.dart';

class TAppTheme{
  TAppTheme._();

  static ThemeData lightTheme = ThemeData(
    primarySwatch: Colors.yellow,
    brightness: Brightness.light,
    textTheme: TTextTheme.lightTextTheme,
    appBarTheme: AppBarTheme(),
    floatingActionButtonTheme: FloatingActionButtonThemeData(),
    elevatedButtonTheme: ElevatedButtonThemeData(style: ElevatedButton.styleFrom())
  );

  static ThemeData darkTheme = ThemeData(
      primarySwatch: Colors.yellow,
      brightness: Brightness.dark,
      textTheme: TTextTheme.darkTextTheme,
      appBarTheme: AppBarTheme(),
      floatingActionButtonTheme: FloatingActionButtonThemeData(),
      elevatedButtonTheme: ElevatedButtonThemeData(style: ElevatedButton.styleFrom())
  );
}
