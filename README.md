# wallpaperplugin

[![pub package](https://img.shields.io/pub/v/wallpaperplugin.svg)](https://pub.dartlang.org/packages/wallpaperplugin)

Wallpaper plugin can be used to set wallpaper in android from any file in gallery.
This plugin cannot be used with iOS as it doesn't support wallpaper setting to 3rd party apps.

## Usage
To use this plugin, add `wallpaperplugin:` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).

### Example

``` dart
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:wallpaperplugin/wallpaperplugin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _wallpaperStatus = "Initial";

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String wallpaperStatus = "Unexpected Result";
      String _localFile = "";
      // Platform messages may fail, so we use a try/catch PlatformException.
      try {
        Wallpaperplugin.setWallpaperWithCrop(localFile: _localFile);
        //uncomment below line to set wallpaper without cropping
        //Wallpaperplugin.setAutoWallpaper(localFile: _localFile);
        wallpaperStatus = "new Wallpaper set";
      } on PlatformException {
        print("Platform exception");
        wallpaperStatus = "Platform Error Occured";
      }
    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;
    setState(() {
      _wallpaperStatus = wallpaperStatus;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Wallpaper Plugin example app'),
        ),
        body: Center(
          child: Text('Wallpaper Status: $_wallpaperStatus\n'),
        ),
      ),
    );
  }
  }
```

### Testing

You can set `wallpaper` with crop in your tests by running this code:

```dart
Wallpaperplugin.setWallpaperWithCrop(localFile: _localFile);
```

You can set `wallpaper` directly in your tests by running this code:

```dart
Wallpaperplugin.setAutoWallpaper(localFile: _localFile);
```
