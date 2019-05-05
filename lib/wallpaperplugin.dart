import 'dart:async';

import 'package:flutter/services.dart';
import 'package:meta/meta.dart';

class Wallpaperplugin {
  static const MethodChannel _channel =
      const MethodChannel('com.ganeshsp.plugins.wallpaper');

  static Future<bool> setWallpaperWithCrop({@required String localFile}) async {

      try {
        await _channel
            .invokeMethod("setWallpaperWithCrop", {"filePath": localFile});
        return true;
      } on PlatformException {
        print("Error : PlatformException");
      }
      return false;
  }

  static Future<bool> setAutoWallpaper({@required String localFile}) async {
      try {
        return await _channel
            .invokeMethod("setAutoWallpaper", {"filePath": localFile});
      } on PlatformException {
        print("Error : PlatformException");
      }
      return false;
  }
}
