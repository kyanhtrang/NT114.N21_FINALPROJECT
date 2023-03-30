import 'package:final_project/src/features/authentication/screens/login/login_screen.dart';
import 'package:final_project/src/features/authentication/screens/splash_screen/splash_screen.dart';
import 'package:final_project/src/features/authentication/screens/welcome/welcome_screen.dart';
import 'package:final_project/src/repository/authentication_repository/authentication_repository.dart';
import 'package:final_project/src/utils/theme/theme.dart';
import 'package:final_project/testmap.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:get/get.dart';

Future main() async {
  // To load the .env file contents into dotenv.
  // NOTE: fileName defaults to .env and can be omitted in this case.
  // Ensure that the filename corresponds to the path in step 1 and 2.
  await dotenv.load(fileName: ".env");
  //...runapp
  WidgetsFlutterBinding.ensureInitialized();
  Firebase.initializeApp(
    options: FirebaseOptions(
      apiKey: dotenv.get('API_KEY'),
      authDomain: dotenv.get('AUTH_DOMAIN'),
      projectId: dotenv.get('PROJECT_ID'),
      storageBucket: dotenv.get('STORAGE_BUCKET'),
      messagingSenderId: dotenv.get('MESSAGING_SENDER_ID'),
      appId: dotenv.get('APP_ID'),
    ),
  );
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
      home: const WelcomeScreen(),
    );
  }
}
