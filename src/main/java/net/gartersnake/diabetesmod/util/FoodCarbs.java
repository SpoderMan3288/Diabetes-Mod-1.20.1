package net.gartersnake.diabetesmod.util;

public enum FoodCarbs {
    // Fruit and Plants
    APPLE(20),
    GOLDEN_APPLE(30),
    ENCHANTED_GOLDEN_APPLE(35),
    MELON_SLICE(20),
    SWEET_BERRIES(1),
    GLOW_BERRIES(1),
    CHORUS_FRUIT(15),
    CARROT(3),
    GOLDEN_CARROT(5),
    POTATO(25),
    BAKED_POTATO(30),
    POISONOUS_POTATO(25),
    BEETROOT(10),
    DRIED_KELP(0),

    // Meat
    BEEF(0),
    COOKED_BEEF(0),
    PORKCHOP(0),
    COOKED_PORKCHOP(0),
    MUTTON(0),
    COOKED_MUTTON(0),
    CHICKEN(0),
    COOKED_CHICKEN(0),
    RABBIT(0),
    COOKED_RABBIT(0),

    // Fish
    COD(0),
    COOKED_COD(0),
    SALMON(0),
    COOKED_SALMON(0),
    TROPICAL_FISH(0),
    PUFFERFISH(0),

    // Confections
    BREAD(30),
    COOKIE(20),
    CAKE(60),
    PUMPKIN_PIE(50),

    // Stew and Drinks
    MUSHROOM_STEW(5),
    BEETROOT_SOUP(5),
    RABBIT_STEW(5),
    SUSPICIOUS_STEW(5),
    MILK_BUCKET(20),
    HONEY_BOTTLE(15);

    final int carbs;

    FoodCarbs (int carbs) {
        this.carbs = carbs;
    }
}
