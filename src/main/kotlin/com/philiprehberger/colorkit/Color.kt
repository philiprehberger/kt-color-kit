package com.philiprehberger.colorkit

/** Immutable color with RGB components and alpha. */
public class Color internal constructor(
    public val red: Int,
    public val green: Int,
    public val blue: Int,
    public val alpha: Double = 1.0,
) {
    public companion object {
        /** Parse a hex color string (#RGB or #RRGGBB). */
        public fun hex(hex: String): Color {
            val h = hex.trimStart('#')
            val (r, g, b) = when (h.length) {
                3 -> Triple(h[0].toString().repeat(2).toInt(16), h[1].toString().repeat(2).toInt(16), h[2].toString().repeat(2).toInt(16))
                6 -> Triple(h.substring(0, 2).toInt(16), h.substring(2, 4).toInt(16), h.substring(4, 6).toInt(16))
                else -> throw IllegalArgumentException("Invalid hex: $hex")
            }
            return Color(r, g, b)
        }

        /** Create from RGB values (0-255). */
        public fun rgb(r: Int, g: Int, b: Int, a: Double = 1.0): Color = Color(r.coerceIn(0, 255), g.coerceIn(0, 255), b.coerceIn(0, 255), a.coerceIn(0.0, 1.0))

        /** Create from HSL values (h: 0-360, s: 0-1, l: 0-1). */
        public fun hsl(h: Double, s: Double, l: Double): Color {
            val (r, g, b) = hslToRgb(h, s, l)
            return Color(r, g, b)
        }

        /** Look up a named CSS color. */
        public fun named(name: String): Color = NAMED_COLORS[name.lowercase()] ?: throw IllegalArgumentException("Unknown color: $name")
    }

    /** Convert to hex string. */
    public fun toHex(): String = "#%02x%02x%02x".format(red, green, blue)
    /** Convert to CSS rgb() string. */
    public fun toCssRgb(): String = "rgb($red, $green, $blue)"
    /** Convert to HSL triple (h, s, l). */
    public fun toHsl(): Triple<Double, Double, Double> = rgbToHsl(red, green, blue)

    /** Lighten by [amount] (0.0-1.0). */
    public fun lighten(amount: Double): Color { val (h, s, l) = toHsl(); return hsl(h, s, (l + amount).coerceIn(0.0, 1.0)) }
    /** Darken by [amount] (0.0-1.0). */
    public fun darken(amount: Double): Color { val (h, s, l) = toHsl(); return hsl(h, s, (l - amount).coerceIn(0.0, 1.0)) }
    /** Invert this color. */
    public fun invert(): Color = Color(255 - red, 255 - green, 255 - blue, alpha)
    /** Convert to grayscale. */
    public fun grayscale(): Color { val g = (0.299 * red + 0.587 * green + 0.114 * blue).toInt(); return Color(g, g, g, alpha) }
    /** Mix with another color. */
    public fun mix(other: Color, weight: Double = 0.5): Color {
        val w = weight.coerceIn(0.0, 1.0)
        return Color(((1 - w) * red + w * other.red).toInt(), ((1 - w) * green + w * other.green).toInt(), ((1 - w) * blue + w * other.blue).toInt())
    }
    /** Set alpha. */
    public fun withAlpha(a: Double): Color = Color(red, green, blue, a.coerceIn(0.0, 1.0))

    /** Relative luminance (0.0-1.0). */
    public fun luminance(): Double = (0.2126 * red / 255.0 + 0.7152 * green / 255.0 + 0.0722 * blue / 255.0)
    /** True if this is a light color. */
    public fun isLight(): Boolean = luminance() > 0.5
    /** True if this is a dark color. */
    public fun isDark(): Boolean = !isLight()

    override fun equals(other: Any?): Boolean = other is Color && red == other.red && green == other.green && blue == other.blue && alpha == other.alpha
    override fun hashCode(): Int = 31 * (31 * (31 * red + green) + blue) + alpha.hashCode()
    override fun toString(): String = toHex()
}

internal val NAMED_COLORS = mapOf(
    "red" to Color(255, 0, 0), "green" to Color(0, 128, 0), "blue" to Color(0, 0, 255),
    "white" to Color(255, 255, 255), "black" to Color(0, 0, 0), "yellow" to Color(255, 255, 0),
    "cyan" to Color(0, 255, 255), "magenta" to Color(255, 0, 255), "orange" to Color(255, 165, 0),
    "purple" to Color(128, 0, 128), "pink" to Color(255, 192, 203), "gray" to Color(128, 128, 128),
    "brown" to Color(165, 42, 42), "navy" to Color(0, 0, 128), "teal" to Color(0, 128, 128),
    "lime" to Color(0, 255, 0), "coral" to Color(255, 127, 80), "salmon" to Color(250, 128, 114),
    "gold" to Color(255, 215, 0), "silver" to Color(192, 192, 192),
)

internal fun rgbToHsl(r: Int, g: Int, b: Int): Triple<Double, Double, Double> {
    val rf = r / 255.0; val gf = g / 255.0; val bf = b / 255.0
    val max = maxOf(rf, gf, bf); val min = minOf(rf, gf, bf)
    val l = (max + min) / 2.0
    if (max == min) return Triple(0.0, 0.0, l)
    val d = max - min
    val s = if (l > 0.5) d / (2.0 - max - min) else d / (max + min)
    val h = when (max) {
        rf -> ((gf - bf) / d + (if (gf < bf) 6 else 0)) * 60
        gf -> ((bf - rf) / d + 2) * 60
        else -> ((rf - gf) / d + 4) * 60
    }
    return Triple(h, s, l)
}

internal fun hslToRgb(h: Double, s: Double, l: Double): Triple<Int, Int, Int> {
    if (s == 0.0) { val v = (l * 255).toInt(); return Triple(v, v, v) }
    val q = if (l < 0.5) l * (1 + s) else l + s - l * s
    val p = 2 * l - q
    fun hue2rgb(t: Double): Double {
        val tt = when { t < 0 -> t + 1; t > 1 -> t - 1; else -> t }
        return when { tt < 1.0/6 -> p + (q - p) * 6 * tt; tt < 1.0/2 -> q; tt < 2.0/3 -> p + (q - p) * (2.0/3 - tt) * 6; else -> p }
    }
    return Triple((hue2rgb(h / 360.0 + 1.0/3) * 255).toInt(), (hue2rgb(h / 360.0) * 255).toInt(), (hue2rgb(h / 360.0 - 1.0/3) * 255).toInt())
}
