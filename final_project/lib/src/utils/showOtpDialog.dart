import 'package:flutter/material.dart';

void showOtpDialog(
    {required BuildContext context,
    required TextEditingController codeController,
    required VoidCallback onPressed}) {
  showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) => AlertDialog(
            title: const Text("Enter Otp"),
            content: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                TextField(
                  controller: codeController,
                )
              ],
            ),
        actions: <Widget>[
          TextButton(onPressed: onPressed, child: Text("Done"))
        ],
          ));
}
