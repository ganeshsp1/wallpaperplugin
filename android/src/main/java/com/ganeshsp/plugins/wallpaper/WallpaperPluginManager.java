package com.ganeshsp.plugins.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import java.io.File;
import java.io.IOException;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** WallpaperPluginManager */
public class WallpaperPluginManager implements MethodCallHandler {
  private static final String WALLPAPER_CHANNEL = "com.ganeshsp.plugins.wallpaper";
    private final Context context;
    private final Activity activity;

    /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), WALLPAPER_CHANNEL);
    channel.setMethodCallHandler(new WallpaperPluginManager(registrar.activeContext(),registrar.activity()));
  }

    WallpaperPluginManager(Context context, Activity activity){
        this.context=context;
        this.activity = activity;
    }
  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("setWallpaperWithCrop")) {
      setWallpaperWithCrop((String) call.argument("filePath"), result);
    } else if (call.method.equals("setAutoWallpaper")) {
      setAutoWallpaper((String) call.argument("filePath"), result);
    } else {
      result.notImplemented();
    }
  }




  private void setWallpaperWithCrop(final String filePath, final MethodChannel.Result result) {
    if(filePath==null||filePath.equals("")){
      result.success(false);
      return;
    }
    File file = new File(filePath);
    if (!file.exists()) {
      result.success(false);
    } else {
      boolean resultValue = true;
      final WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
      try {
        Uri contentURI = getImageContentUri(context, file.getAbsolutePath());
//        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
//        context.startActivity(wallpaperManager.getCropAndSetWallpaperIntent(contentURI));
//        Toast.makeText(context, "Wallpaper set", Toast.LENGTH_SHORT).show();
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        InputStream inputStream = new FileInputStream(file);
//        wallpaperManager.setBitmap(bitmap, null, true);
      try {
        context.startActivity(wallpaperManager.getCropAndSetWallpaperIntent(contentURI));
          } catch (IllegalArgumentException e) {
         // Seems to be an Oreo bug - fall back to using the bitmap instead
          Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.activity.getContentResolver(), contentURI);
          WallpaperManager.getInstance(this.activity).setBitmap(bitmap);
        }
        Toast.makeText(context, "Wallpaper set", Toast.LENGTH_SHORT).show();
      } catch (Exception e) {
        e.printStackTrace();
        resultValue = false;
      }
      result.success(resultValue);
    }
  }
  private void setAutoWallpaper(final String filePath, final MethodChannel.Result result) {
    if(filePath==null||filePath.equals("")){
      result.success(false);
      return;
    }
    File file = new File(filePath);
    if (!file.exists()) {
      result.success(false);
    } else {
      Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
      WallpaperManager myWallpaperManager = WallpaperManager.getInstance(context);
      try {
        myWallpaperManager.setBitmap(bitmap);
        Toast.makeText(context, "Wallpaper set", Toast.LENGTH_SHORT).show();
      } catch (IOException e) {
        Toast.makeText(context, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
      }
      result.success(true);
    }
  }

  public static Uri getImageContentUri(Context context, String absPath) {

    Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
            new String[] { absPath }, null);

    if (cursor != null && cursor.moveToFirst()) {
      int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
      return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));

    } else if (!absPath.isEmpty()) {
      ContentValues values = new ContentValues();
      values.put(MediaStore.Images.Media.DATA, absPath);
      return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    } else {
      return null;
    }
  }
}
