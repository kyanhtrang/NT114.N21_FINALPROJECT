
import 'package:final_project/src/constants/images_strings.dart';
import 'package:final_project/src/constants/text_strings.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';

import '../../../../constants/colors.dart';
import '../../../../constants/sizes.dart';
import '../welcome/welcome_screen.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key}) : super(key: key);

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {

  bool animate = false;

  @override
  void initState() {
    super.initState();
    startAnimation();
  }

  Future startAnimation() async {
    await Future.delayed(const Duration(milliseconds: 500));
    setState(() { animate = true; });
    await Future.delayed(const Duration(milliseconds: 5000));
    Get.to(const WelcomeScreen());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          AnimatedPositioned(
            duration: const Duration(milliseconds: 1600),
            top: animate ? 0 : -30,
            left: animate ? 0 : -30,
            child: AnimatedOpacity(
              duration: const Duration(milliseconds: 1600),
              opacity: animate ? 1 : 0,
              child: const Image(image: AssetImage(tSplashTopIcon)),
            ),
          ),
          AnimatedPositioned(
            duration: const Duration(milliseconds: 2000),
            top: 80,
            left: animate ? tDefaultSize : -80,
            child: AnimatedOpacity(
              duration: const Duration(milliseconds: 2000),
              opacity: animate ? 1 : 0,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    tAppName,
                    style: Theme.of(context).textTheme.headline3,
                  ),
                  Text(
                    tAppTagLine,
                    style: Theme.of(context).textTheme.headline2,
                  )
                ],
              ),
            ),
          ),
          AnimatedPositioned(
            duration: const Duration(milliseconds: 2400),
            bottom: animate ? 100 : 0,
            child: AnimatedOpacity(
              duration: const Duration(milliseconds: 2000),
              opacity: animate ? 1 : 0,
              child: const Image(image: AssetImage(tSplashImage)),
            ),
          ),
          AnimatedPositioned(
            duration: const Duration(milliseconds: 2400),
            bottom: animate ? 60 : 0,
            right: tDefaultSize,
            child: AnimatedOpacity(
              duration: const Duration(milliseconds: 2000),
              opacity: animate ? 1 : 0,
              child: Container(
                width: tSplashContainerSize,
                height: tSplashContainerSize,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(100),
                  color: tPrimaryColor,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}