# Architecture

This document describes the high-level architecture of the Spreedly Android SDK.
If you want to familiarize yourself with the code base, you are just in the right place!

## Bird's Eye View

On the highest level, the sdks is broken into 4 libraries. 3 libraries that build on each other and a 3D Secure library.

The `core-sdk` provides the communication faoundation for the rest of the library. It provides no UI, and the `express` and `securewidgets` libraries provide UI commonents for use on android.

## Code Map

This section talks briefly about various important directories and data structures. All of the libraries use standard java/maven directory stucture. Gradle is used for all build and test tasks.

### `core-sdk`

This library provides the communications functionality for talking with the Spreedly back-end servers. There are a couple of important to understand design decisions about this library.

* Not android specific. This library is just a platform independent java library. This makes testing easier and enables it to be used in non android situations.
* Supports both anonymous and authenticated transactions. While the publicly exposed functionality only uses the API end-points that support anonymous transactions, internally the library handles both. This enables future expansion, and also enabled a more comprehensive test setup.


### `securewidgets`

This is a set of base UI components that integrate with the `core-sdk`. If you were to build your own UI in android for doing spreedly transactions, this would not be a bad place to start.

The most important class here is `SecureFormLayout`, which can be used to contain your payment forms. The library exposes a set of resource ids that can be used either with native views or with the views in the library. The `SecureFormLayout` exposes functionality to find and use these field views to populate spreedly transactions.

### `express`

See README.md or the saple app for examples of how to use the `express` library.

This library used the `core-sdk` and the `securewidgets` libraries to expose an easy to use UI framework to quickly add payment capabilities to an android app.

### `docs`

This directory contains html files generated from the javadocs throughout the projects.

### 'sdk_sample'

This is an android app that provides the ability to test and demonstrate all aspects of the above libraries.

## Cross-Cutting Concerns

This sections talks about the things which are everywhere and nowhere in particular.

### 3D Secure

This live in a separate repository. Originally all of these libraries were developed together in one repository. This means the sample app lives here, there, and in a standalone repository. The stand-alone repository exists mainly to test pulling all the libraries in as gradle dependencies rather than as peer libraries.

### Testing

There are unit and integration tests for the entire core library. However there is little automated testing for the UI components.

