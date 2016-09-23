# react-native example with native widgets

This is the default react-native application though also adds examples of native widgets.
This example's concern is native widgets and not the (react-native) app itself... 
Just some examples of how you can have a widget within your react-native application.

    npm install

## Android widgets
In Android widgets can not be created using react-native. 
You will need to create them yourself (java) https://developer.android.com/design/patterns/widgets.html

## iOS widgets (sic)
In iOS widgets are extensions to the notification and can not be created using react-native. 
You will need to create them yourself (Obj-C ose).

## Development

...todo

### Android

...todo

#### Emulator

...todo

 * enable gradle daemon for faster compiles https://docs.gradle.org/2.14.1/userguide/gradle_daemon.html
 * start an emulator
 * for the initial bundle run `npm run bundle-android` else the app will fail on `react-native run-android` (http://stackoverflow.com/questions/38870710/error-could-not-get-batchedbridge-make-sure-your-bundle-is-packaged-properly)
 * for the java changes you need to (re)run `react-native run-android` possibly remove the previous widget and add the updated widget
 * for hot reloading of the app itself you need to be running `npm start`
   * use ctrl/cmd-M on the emulator for the react-native menu .. to reload the app

Note: Have you changed the java code then `react-native run-android` on the project and re-add the widget

#### Device

...todo
 
# Notes / Design
 
## Auth
Your react-native application may need to store auth for the native widget to access remote data.