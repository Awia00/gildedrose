package net.praqma.codeacademy.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    private GildedRose run_with(String name, int sell_in, int quality) {
        Item[] items = new Item[] { new Item(name, sell_in, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app;
    }

    @Test
    public void name_persist() {
        GildedRose app = run_with("foo", 0, 0);
        assertEquals("foo", app.items[0].name);
    }

    @Test
    public void quality_decrease_by_1() {
        GildedRose app = run_with("foo", 50, 50);
        assertEquals(49, app.items[0].quality);
    }

    @Test
    public void quality_decrease_by_1_over_50() {
        GildedRose app = run_with("foo", 50, 100);
        assertEquals(99, app.items[0].quality);
    }

    @Test
    public void quality_decrease_by_0_under_0() {
        GildedRose app = run_with("foo", 50, 0);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void quality_decrease_by_2() {
        GildedRose app = run_with("foo", 0, 50);
        assertEquals(48, app.items[0].quality);
    }

    @Test
    public void quality_increase_agedbrie_by_1() {
        GildedRose app = run_with("Aged Brie", 10, 10);
        assertEquals(11, app.items[0].quality);
    }

    @Test
    public void quality_increase_agedbrie_by_0() {
        GildedRose app = run_with("Aged Brie", 10, 50);
        assertEquals(50, app.items[0].quality);
    }

    @Test
    public void quality_increase_agedbrie_by_2_after_sellin() {
        GildedRose app = run_with("Aged Brie", 0, 10);
        assertEquals(12, app.items[0].quality);
    }

    @Test
    public void sellin_decrease_by_1() {
        GildedRose app = run_with("foo", 50, 50);
        assertEquals(49, app.items[0].sellIn);
    }

    @Test
    public void sulfurus_equialibrium_sellin() {
        GildedRose app = run_with("Sulfuras", 50, 50);
        assertEquals(50, app.items[0].sellIn);
    }

    @Test
    public void sulfurus_equialibrium_quality() {
        GildedRose app = run_with("Sulfuras", 50, 50);
        assertEquals(50, app.items[0].quality);
    }

    @Test
    public void backstage_after_sellin() {
        GildedRose app = run_with("Backstage passes", 0, 50);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void backstage_increase_by_1() {
        GildedRose app =  run_with("Backstage passes", 11, 10);
        assertEquals(11, app.items[0].quality);
    }

    @Test
    public void backstage_increase_by_2_higher() {
        GildedRose app =  run_with("Backstage passes", 10, 10);
        assertEquals(12, app.items[0].quality);
    }

    @Test
    public void backstage_increase_by_2_lower() {
        GildedRose app =  run_with("Backstage passes", 6, 10);
        assertEquals(12, app.items[0].quality);
    }

    @Test
    public void backstage_increase_by_3_higher() {
        GildedRose app =  run_with("Backstage passes", 5, 10);
        assertEquals(13, app.items[0].quality);
    }

    @Test
    public void backstage_increase_by_3_lower() {
        GildedRose app =  run_with("Backstage passes", 1, 10);
        assertEquals(13, app.items[0].quality);
    }

    @Test
    public void conjured_double_decrease_normal() {
        GildedRose app =  run_with("Conjured", 10, 10);
        assertEquals(8, app.items[0].quality);
    }

    @Test
    public void conjured_double_decrease_normal_after_sellin() {
        GildedRose app =  run_with("Conjured foo", 0, 10);
        assertEquals(6, app.items[0].quality);
    }
}