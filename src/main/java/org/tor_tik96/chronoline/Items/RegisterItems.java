package org.tor_tik96.chronoline.Items;

import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.hud.RegisterHUD;

public class RegisterItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Chronoline.MODID);

    public static final RegistryObject<Item> DISTORTED_FRAGMENT = ITEMS.register("distorted_fragment", () -> new DistortedFragment(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
    ));

    public static final RegistryObject<Item> FLOWER_BAR = ITEMS.register("flower_bar", () -> new StaminaCandyBar(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
    , StaminaCandyBarTypes.FLOWER));

    public static final RegistryObject<Item> DEFAULT_BAR = ITEMS.register("default_bar", () -> new StaminaCandyBar(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
            , StaminaCandyBarTypes.DEFAULT));

    public static final RegistryObject<Item> INK_BAR = ITEMS.register("ink_bar", () -> new StaminaCandyBar(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
            , StaminaCandyBarTypes.INK));

    public static final RegistryObject<Item> HONEY_BAR = ITEMS.register("honey_bar", () -> new StaminaCandyBar(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
            , StaminaCandyBarTypes.HONEY));

    public static final RegistryObject<Item> BERRY_BAR = ITEMS.register("berry_bar", () -> new StaminaCandyBar(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
            , StaminaCandyBarTypes.BERRY));

    public static final RegistryObject<Item> ENDER_BAR = ITEMS.register("ender_bar", () -> new StaminaCandyBar(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
            , StaminaCandyBarTypes.ENDER));

    public static final RegistryObject<Item> BUG_BAR = ITEMS.register("bug_bar", () -> new StaminaCandyBar(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
            , StaminaCandyBarTypes.RANDOM));

    public static final RegistryObject<Item> MONEY_5 = ITEMS.register("money_5", () -> new Money(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
    ));

    public static final RegistryObject<Item> MONEY_25 = ITEMS.register("money_25", () -> new Money(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
    ));

    public static final RegistryObject<Item> MONEY_50 = ITEMS.register("money_50", () -> new Money(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
    ));

    public static final RegistryObject<Item> MONEY_100 = ITEMS.register("money_100", () -> new Money(new Item.Properties()
            .tab(Chronoline.MOD_TAB)
    ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

