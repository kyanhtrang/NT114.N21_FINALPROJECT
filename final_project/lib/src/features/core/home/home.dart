import 'package:final_project/src/constants/sizes.dart';
import 'package:flutter/material.dart';

class Home extends StatelessWidget{
  const Home ({Key ? key}) : super(key: key);

  @override
  Widget build (BuildContext context){
    return Scaffold(
      body: SingleChildScrollView(
        child: Container(
          padding: const EdgeInsets.all(tDefaultSize),
        ),
      ),
    );
  }
}