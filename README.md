# color-kit

[![Tests](https://github.com/philiprehberger/kt-color-kit/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-color-kit/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/color-kit)](https://central.sonatype.com/artifact/com.philiprehberger/color-kit)
[![License](https://img.shields.io/github/license/philiprehberger/kt-color-kit)](LICENSE)

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

## License

MIT
