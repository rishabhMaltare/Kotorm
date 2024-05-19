This is my journey on Kotorm project. A project to explore KMP & CMP. I am sharing here my experiences per commit basis. 

#### 1. I created the project as per the [official get started guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-getting-started.html).
#### 2. Completed the project with minor changes.
#### 3. Added Material3 in CMP
1. Add material3 dependencies in composeApp build.gradle.
2. Create theme files. I used [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/) for theme creation. Download the files for Compose.
3. Create a `theme` folder in `commonMain/kotlin`.
4. Paste `Color.kt`,  `Theme.kt`, `Type.kt` files which we downloaded in 1st step.
5. Create an `expect` AppTheme Composable in theme folder. Implement the `actual` functions in the same hierarchy in all other Mains folder (iOSMain, androidMain, desktopMain). Ref: [expect/actual](https://kotlinlang.org/docs/multiplatform-expect-actual.html)
6. Wrap the App() Composable in `App.kt` with AppTheme(). Handle dark theme & dynamicColorTheme booleans in `MainActivity.kt` & `MainViewController.kt`.
7. In `App.kt`, update the imports from material to material3 for all UI componenets. Due to this I had to spend a lot of time, figuring out, why my theme was not getting applied.
8. Run & verify by switching themes from settings on device.
9. Commit & push the code. 