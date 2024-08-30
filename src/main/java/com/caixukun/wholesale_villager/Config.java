package com.caixukun.wholesale_villager;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = wholesale_villager.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Double> maxMerchantValue = BUILDER
            .comment("村民减价触发值为最大交易物品数除以数值")
            .define("maxMerchantValue", 1.5);

    private static final ForgeConfigSpec.ConfigValue<Double> MerchantCost = BUILDER
            .comment("村民减价倍率")
            .define("MerchantCost", 1.5);
    private static final ForgeConfigSpec.BooleanValue INF = BUILDER
            .comment("开启无限交易")
            .define("infMerchant", true);

    private static final ForgeConfigSpec.BooleanValue HST = BUILDER
            .comment("当折扣到1时，是否开启物品数量上涨")
            .define("maxf", true);

    // a list of strings that are treated as resource locations for items

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Double maxMerchantValuef;
    public static Double MerchantCostf;
    public static boolean inf;
    public static boolean hst;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        maxMerchantValuef = maxMerchantValue.get();
        MerchantCostf = MerchantCost.get();
        inf = INF.get();
        hst = HST.get();
    }
}
