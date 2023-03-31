import 'package:final_project/src/features/authentication/screens/on_boarding/on_boarding_screen.dart';
import 'package:get/get.dart';
import 'package:get/get_state_manager/src/simple/get_controllers.dart';

import '../../features/authentication/screens/welcome/welcome_screen.dart';

class FadeInAnimationController extends GetxController {
  static FadeInAnimationController get find => Get.find();

  RxBool animate = false.obs;

  Future startSplashAnimation() async {
    await Future.delayed(const Duration(milliseconds: 500));
    animate.value = true;
    await Future.delayed(const Duration(milliseconds: 3000));
    animate.value = false;
    await Future.delayed(const Duration(milliseconds: 2000));
    Get.off( // Get.off Instead of Get.offAll()
          () => const OnBoardingScreen(),
      duration: const Duration(milliseconds: 1000), //Transition Time
      transition: Transition.fadeIn, //Screen Switch Transition
    );
  }

//Can be used to animate In after calling the next screen.
  Future animationIn() async {
    await Future.delayed(const Duration(milliseconds: 500));
    animate.value = true;
  }

//Can be used to animate Out before calling the next screen.
  Future animationOut() async {
    animate.value = false;
    await Future.delayed(const Duration(milliseconds: 100));
  }
}