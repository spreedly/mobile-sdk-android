![Lint](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Lint%20check%20sources)
![Test](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Run%20Tests)
![Build](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Build%20Libraries)

# spreedly-android


# Lint

Run

    ./gradlew lint


# Coverage

A coverage report is regularly posted [here](https://ergonlabs.github.io/spreedly-docs/coverage/java/core-sdk/index.html).

To see this locally run:

    ./gradlew test

Then open [build/core-sdk/reports/jacoco/test/html/index.html](build/core-sdk/reports/jacoco/test/html/index.html)

# Docs

To build docs use:

    rm -rf docs
    ./gradlew alljavadoc