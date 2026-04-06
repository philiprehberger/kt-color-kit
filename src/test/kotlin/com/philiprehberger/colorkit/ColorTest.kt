package com.philiprehberger.colorkit

import kotlin.test.*

class ColorTest {
    @Test fun `hex parsing`() { val c = Color.hex("#ff0000"); assertEquals(255, c.red); assertEquals(0, c.green) }
    @Test fun `short hex`() { val c = Color.hex("#f00"); assertEquals(255, c.red) }
    @Test fun `rgb`() { val c = Color.rgb(100, 200, 50); assertEquals(100, c.red) }
    @Test fun `named`() { assertEquals(Color.rgb(255, 0, 0), Color.named("red")) }
    @Test fun `toHex`() = assertEquals("#ff0000", Color.rgb(255, 0, 0).toHex())
    @Test fun `invert`() { val c = Color.rgb(255, 255, 255).invert(); assertEquals(0, c.red) }
    @Test fun `luminance white`() = assertTrue(Color.named("white").luminance() > 0.9)
    @Test fun `luminance black`() = assertTrue(Color.named("black").luminance() < 0.1)
    @Test fun `isLight`() = assertTrue(Color.named("white").isLight())
    @Test fun `isDark`() = assertTrue(Color.named("black").isDark())
    @Test fun `lighten`() { val c = Color.named("gray").lighten(0.2); assertTrue(c.luminance() > Color.named("gray").luminance()) }
    @Test fun `mix`() { val c = Color.named("red").mix(Color.named("blue"), 0.5); assertTrue(c.red in 100..150) }
    @Test fun `complementary of red is cyan`() { val c = Color.rgb(255, 0, 0).complementary(); assertTrue(c.red <= 5); assertTrue(c.green >= 250); assertTrue(c.blue >= 250) }
    @Test fun `triadic returns two colors`() { val (t1, t2) = Color.rgb(255, 0, 0).triadic(); assertTrue(t1.green > t1.red); assertTrue(t2.blue > t2.red) }
}
