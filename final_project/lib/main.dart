import 'package:final_project/firebase_options.dart';
import 'package:final_project/src/features/authentication/screens/login/login_screen.dart';
import 'package:final_project/src/features/authentication/screens/splash_screen/splash_screen.dart';
import 'package:final_project/src/repository/authentication_repository/authentication_repository.dart';
import 'package:final_project/src/utils/theme/theme.dart';
import 'package:final_project/testmap.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_navigation/src/root/get_material_app.dart';

void main(){
  WidgetsFlutterBinding.ensureInitialized();
  Firebase.initializeApp(options:  DefaultFirebaseOptions.currentPlatform)
      .then((value) => Get.put(AuthenticationRepository()));
  runApp(const App());
}

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      theme: TAppTheme.lightTheme,
      darkTheme: TAppTheme.darkTheme,
      themeMode: ThemeMode.system,
      home: const LoginScreen(),
    );
  }
}
