import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:wallpaperplugin/wallpaperplugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('com.ganeshsp.plugins.wallpaper');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('setWallpaper', () async {
    expect(await Wallpaperplugin.setWallpaperWithCrop(localFile: ""), 'false');
  });
}
