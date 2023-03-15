import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class TTextTheme{
  static TextTheme lightTextTheme = TextTheme(
    headline2: GoogleFonts.montserrat(color: Colors.black87),
    subtitle2: GoogleFonts.poppins(color: Colors.deepPurple, fontSize: 24),
  );

  static TextTheme darkTextTheme = TextTheme(
      headline2: GoogleFonts.montserrat(color: Colors.white70),
      subtitle2: GoogleFonts.poppins(color: Colors.white60, fontSize: 24),
  );
}