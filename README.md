# media-library
Android media library app developed in Kotlin

## Main functionality:
- sign in with google account
- note games, movies and books titles
- view noted items, divided into *planned*, *started*, *finished*
- edit and remove noted items
- search items using 3rd' parties databases

## Tech stack

- [Android Studio](https://developer.android.com/studio)
- [Kotlin](https://kotlinlang.org/)
- [Firebase](https://firebase.google.com/?hl=pl)
- [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
- [Material Design](https://material.io/design/)
- [Retrofit2](https://square.github.io/retrofit/)
- [Gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
- [Okhttp3](https://square.github.io/okhttp/)

## Credits

-  Movies database provided by [OMDB](http://www.omdbapi.com/)

## Dev environment preparation

Before building, an API key needs to be provided in order for OMDB to allow data access. To provide the key, follow these steps:
1. Get an API key (6-sing long) from [OMDB](http://www.omdbapi.com/)
2. To the local gradle-created file `local.properties` add the following content:
```
api_key_movies="YOUR_KEY"
```
3. Replace *YOUR_KEY* with the API key described in the point 1.

Moreover, google services data is required to run this app. Paste `google-services.json` file, provided using other channel, to the `/app` directory.

## App installation

To install dev version of the app you need to have a device plugged or an emulator installed, both with Android 4.4 or higher.
