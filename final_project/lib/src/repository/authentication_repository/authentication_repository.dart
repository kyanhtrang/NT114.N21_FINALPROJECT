import 'package:final_project/src/exceptions/authen/signin_email_password_failure.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:get/get_rx/src/rx_workers/rx_workers.dart';
import 'dart:developer';

import '../../features/authentication/screens/welcome/welcome_screen.dart';
import '../../features/core/home/homepage.dart';
import '../../exceptions/authen/signup_email_password_failure.dart';

class AuthenticationRepository extends GetxController {
  static AuthenticationRepository get instance => Get.find();

  //Variables
  final FirebaseAuth _auth = FirebaseAuth.instance;
  late final User firebaseUser;

  //Will be load when app launches this func will be called and set the firebaseUser state
  @override
  void onReady() {
    _showToast(_auth.currentUser.isBlank.toString());
  }

  _setInitialScreen(User? user) {
    user == null ? Get.offAll(() => const WelcomeScreen()) : Get.offAll(() => const HomePage());
  }

  //FUNC
  Future<String?> createUserWithEmailAndPassword(String email, String password) async {
    try {
      await _auth.createUserWithEmailAndPassword(
          email: email, password: password);
      await sendEmailVerificaton();
    } on FirebaseAuthException catch (e) {
      final ex = SignUpWithEmailAndPasswordFailure.code(e.code);
      return ex.message;
    } catch (_) {
      const ex = SignUpWithEmailAndPasswordFailure();
      return ex.message;
    }
    return null;
  }

  Future<void> sendEmailVerificaton() async {
    try {
      _auth.currentUser!.sendEmailVerification();
    } on FirebaseAuthException catch (err) {
    }
  }
/*
  Future<String?> loginWithEmailAndPassword(String email, String password) async {
    try {
      await _auth.signInWithEmailAndPassword(email: email, password: password);
      if (firebaseUser.getIdToken() != null) {
        await Get.offAll(() => const HomePage());
      } else {
        await Get.offAll(const WelcomeScreen());
        return 'Error: unable to sign in';
      }
    } on FirebaseAuthException catch (e) {
      final ex = SignInWithEmailAndPasswordFailure.code(e.code);
      return ex.message;
    } catch (_) {
      const ex = SignInWithEmailAndPasswordFailure();
      return ex.message;
    }
    return null;
  }*/

  Future<String?> loginWithEmailAndPassword(String email, String password) async {
    try {
      final userCredential = await FirebaseAuth.instance.signInWithEmailAndPassword(email: email, password: password);
      if (userCredential.user != null) {
        await Get.offAll(() => const HomePage());
      } else {
        await Get.offAll(const WelcomeScreen());
        return 'Error: unable to sign in';
      }
    } on FirebaseAuthException catch (e) {
      final ex = SignInWithEmailAndPasswordFailure.code(e.code);
      return ex.message;
    } catch (e, stackTrace) {
      debugPrint('$e\n$stackTrace');
      const ex = SignInWithEmailAndPasswordFailure();
      return ex.message;
    }
    return null;
  }


  void _showToast(String text) {
    final scaffold = ScaffoldMessenger.of(Get.context!);
    scaffold.showSnackBar(SnackBar(
      content: Text(text),
    ),);
  }

  Future<void> logout() async => await _auth.signOut();

}