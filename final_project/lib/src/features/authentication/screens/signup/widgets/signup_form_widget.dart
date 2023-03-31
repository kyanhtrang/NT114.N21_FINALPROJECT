import 'package:final_project/src/features/authentication/controllers/signup_controller.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';

import '../../../../../constants/sizes.dart';
import '../../../../../constants/text_strings.dart';
import '../../../../../service/firebase_auth_methods.dart';

class SignUpFormWidget extends StatefulWidget {
  const SignUpFormWidget({Key? key,}) : super(key: key);

  @override
  State<SignUpFormWidget> createState() => SignUpWithEmail();
}

class SignUpWithEmail extends State<SignUpFormWidget> {
  final TextEditingController email = TextEditingController();
  final TextEditingController password = TextEditingController();
  final TextEditingController confirmPassword = TextEditingController();

  @override
  void dispose() {
    super.dispose();
    email.dispose();
    password.dispose();
    confirmPassword.dispose();
  }

  void registerUser() async {
    FirebaseAuthMethods(FirebaseAuth.instance).registerWithEmail(
      email: email.text,
      password: password.text,
      context: context,
    );
  }

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(SignUpController());
    final _formKey = GlobalKey<FormState>();

    return Container(
      padding: const EdgeInsets.symmetric(vertical: tFormHeight - 10),
      child: Form(
        key: _formKey,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextFormField(
              controller: email,
              decoration: const InputDecoration(
                  label: Text(tEmail),
                  prefixIcon: Icon(Icons.email_outlined)),
            ),
            const SizedBox(height: tFormHeight - 20),
            TextFormField(
              controller: password,
              decoration: const InputDecoration(
                  label: Text(tPassword),
                  prefixIcon: Icon(Icons.password)),
            ),
            const SizedBox(height: tFormHeight - 20),
            TextFormField(
              controller: confirmPassword,
              decoration: const InputDecoration(
                  label: Text(tConfirmPassword),
                  prefixIcon: Icon(Icons.password)),
            ),
            const SizedBox(height: tFormHeight - 10),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () {
                  registerUser();
                },
                child: Text(tSignup.toUpperCase()),
              ),
            )
          ],
        ),
      ),
    );
  }
}