package net.praqma.codeacademy.gildedrose;

public class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            int deltaQ = 0;
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.contains("Backstage passes")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras")) {
                        deltaQ--;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    deltaQ++;
            
                    if (items[i].name.contains("Backstage passes")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                deltaQ++;
                            }
                        }
            
                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                deltaQ++;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.contains("Backstage passes")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras")) {
                                deltaQ--;
                            }
                        }
                    } else {
                        deltaQ = -items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        deltaQ++;
                    }
                }
            }
            if(items[i].name.startsWith("Conjured") && deltaQ < 0)
                deltaQ *= 2;
            items[i].quality = items[i].quality + deltaQ;
        }
    }
}