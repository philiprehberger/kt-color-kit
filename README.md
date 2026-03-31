# color-kit

[![Tests](https://github.com/philiprehberger/kt-color-kit/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-color-kit/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/color-kit.svg)](https://central.sonatype.com/artifact/com.philiprehberger/color-kit)
[![Last updated](https://img.shields.io/github/last-commit/philiprehberger/kt-color-kit)](https://github.com/philiprehberger/kt-color-kit/commits/main)

Color parsing, conversion, and manipulation across RGB, HSL, and HEX.

## Installation

### Gradle (Kotlin DSL)

```kotlin
implementation("com.philiprehberger:color-kit:0.1.3")
```

### Maven

```xml
<dependency>
    <groupId>com.philiprehberger</groupId>
    <artifactId>color-kit</artifactId>
    <version>0.1.3</version>
</dependency>
```

## Usage

```kotlin
import com.philiprehberger.colorkit.*

val red = Color.hex("#ff0000")
val blue = Color.rgb(0, 0, 255)

red.toHex()       // "#ff0000"
red.lighten(0.2)  // lighter red
red.mix(blue)     // purple
red.luminance()   // 0.2126
red.isDark()      // true
```

## API

| Function / Class | Description |
|------------------|-------------|
| `Color.hex(hex)` | Parse hex color (#RGB or #RRGGBB) |
| `Color.rgb(r, g, b, a)` | Create from RGB values |
| `Color.hsl(h, s, l)` | Create from HSL values |
| `Color.named(name)` | Look up CSS named color |
| `Color.toHex()` | Convert to hex string |
| `Color.toHsl()` | Convert to HSL triple |
| `Color.lighten(amount)` / `darken(amount)` | Adjust lightness |
| `Color.invert()` / `grayscale()` | Color transformations |
| `Color.mix(other, weight)` | Blend two colors |
| `Color.luminance()` / `isLight()` / `isDark()` | Color analysis |

## Development

```bash
./gradlew test
./gradlew build
```

## Support

If you find this project useful:

⭐ [Star the repo](https://github.com/philiprehberger/kt-color-kit)

🐛 [Report issues](https://github.com/philiprehberger/kt-color-kit/issues?q=is%3Aissue+is%3Aopen+label%3Abug)

💡 [Suggest features](https://github.com/philiprehberger/kt-color-kit/issues?q=is%3Aissue+is%3Aopen+label%3Aenhancement)

❤️ [Sponsor development](https://github.com/sponsors/philiprehberger)

🌐 [All Open Source Projects](https://philiprehberger.com/open-source-packages)

💻 [GitHub Profile](https://github.com/philiprehberger)

🔗 [LinkedIn Profile](https://www.linkedin.com/in/philiprehberger)

## License

[MIT](LICENSE)
