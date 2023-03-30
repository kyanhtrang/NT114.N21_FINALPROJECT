import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_navigation/src/snackbar/snackbar.dart';

import '../../../repository/authentication_repository/authentication_repository.dart';

class SignUpController extends GetxController {
  static SignUpController get instance => Get.find();

  //TextField Controllers to get data from TextFields
  final email = TextEditingController();
  final password = TextEditingController();
  final fullName = TextEditingController();
  final phoneNo = TextEditingController();


  //Call this Function from Design & it will do the rest
  void registerUser(String email, String password) {
    String? error = AuthenticationRepository.instance.createUserWithEmailAndPassword(email, password) as String?;
    _showToast(AuthenticationRepository.instance.firebaseUser.getIdToken().toString());
    if(error != null) {
      Get.showSnackbar(GetSnackBar(message: error.toString(),));
    }
  }

  void _showToast(String text) {
    final scaffold = ScaffoldMessenger.of(Get.context!);
    scaffold.showSnackBar(SnackBar(
      content: Text(text),
    ),);
  }

}