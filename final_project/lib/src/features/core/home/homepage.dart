import 'package:final_project/src/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';

import '../../../constants/colors.dart';

class HomePage extends StatelessWidget{
  const HomePage ({Key ? key}) : super(key: key);

  @override
  Widget build (BuildContext context){

    var mediaQuery = MediaQuery.of(context);
    var height = mediaQuery.size.height;
    var brightness = mediaQuery.platformBrightness;
    final isDarkMode = brightness == Brightness.dark;

    return Scaffold(
      bottomNavigationBar: Container(
        color: isDarkMode ? tWhiteColor : tSecondaryColor,
        child: Padding(
          padding: const EdgeInsets.symmetric(
            horizontal: 15.0,
            vertical: 15.0),
          child: GNav(
            backgroundColor: isDarkMode ? tWhiteColor : tSecondaryColor,
            color: isDarkMode ? tSecondaryColor : tWhiteColor,
            activeColor: isDarkMode ? tSecondaryColor : tWhiteColor,
            tabBackgroundColor: tPrimaryColor,
            gap: 6,
            onTabChange: (index){
              print(index);
            },
            padding: EdgeInsets.all(10),
            tabs: const [
              GButton(
                  icon: Icons.home,
                  text: 'Home'
              ),
              GButton(
                  icon: Icons.message_outlined,
                  text: 'Message'
              ),
              GButton(
                  icon: Icons.notifications_outlined,
                  text: 'Notification'
              ),
              GButton(
                  icon: Icons.settings,
                  text: 'Setting'
              )
            ],
          ),
        ),
      )
    );
  }
}