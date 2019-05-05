import 'dart:async';

import 'package:flutter/services.dart';
import 'package:meta/meta.dart';

/// Wallpaper plugin class, with methods to invoke the platform channel methods.
class Wallpaperplugin {
  static const MethodChannel _channel =
      MethodChannel('com.ganeshsp.plugins.wallpaper');

  ///This method is used to set wallpaper with crop option, where the user gets
  ///to crop the image and then user can set it as wallpaper
  ///input - [localfile] which is a [String] giving the path of the image
  ///in the external storage.
  static Future<bool> setWallpaperWithCrop({@required String localFile}) async {
    try {
      await _channel.invokeMethod<bool>(
          'setWallpaperWithCrop', <String, String>{'filePath': localFile});
      return true;
    } on PlatformException {
      print('Error : PlatformException');
    }
    return false;
  }

  ///This method is used to set wallpaper directly.
  ///input - [localfile] which is a [String] giving the path of the image
  ///in the external storage.
  static Future<bool> setAutoWallpaper({@required String localFile}) async {
    try {
      await _channel.invokeMethod<bool>(
          'setAutoWallpaper', <String, String>{'filePath': localFile});
    } on PlatformException {
      print('Error : PlatformException');
    }
    return false;
  }
}
