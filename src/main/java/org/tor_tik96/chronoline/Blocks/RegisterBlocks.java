package org.tor_tik96.chronoline.Blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Items.RegisterItems;

import java.util.function.Supplier;

public class RegisterBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Chronoline.MODID);


    public static final RegistryObject<Block> BLACK_BLOCK = registerBlock("black_spec_block", () -> new BlackBlock(BlockBehaviour.Properties.of(Material.BARRIER)
            .strength(-1, 3600000.0F)
            .noLootTable()
    ), 64);

    public static final RegistryObject<Block> TOY_1 = registerToy("toy_1", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_1));

    public static final RegistryObject<Block> TOY_2 = registerToy("toy_2", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_2));

    public static final RegistryObject<Block> TOY_3 = registerToy("toy_3", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_3));

    public static final RegistryObject<Block> TOY_4 = registerToy("toy_4", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_4));

    public static final RegistryObject<Block> TOY_5 = registerToy("toy_5", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_5));

    public static final RegistryObject<Block> TOY_6 = registerToy("toy_6", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_6));

    public static final RegistryObject<Block> TOY_7 = registerToy("toy_7", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_7));

    public static final RegistryObject<Block> TOY_8 = registerToy("toy_8", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_8));

    public static final RegistryObject<Block> TOY_9 = registerToy("toy_9", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_9));

    public static final RegistryObject<Block> TOY_10 = registerToy("toy_10", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_10));

    public static final RegistryObject<Block> TOY_11 = registerToy("toy_11", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_11));

    public static final RegistryObject<Block> TOY_12 = registerToy("toy_12", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_12));

    public static final RegistryObject<Block> TOY_13 = registerToy("toy_13", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_13));

    public static final RegistryObject<Block> TOY_14 = registerToy("toy_14", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_14));

    public static final RegistryObject<Block> TOY_15 = registerToy("toy_15", () -> new Toy(BlockBehaviour.Properties.of(Material.AIR)
            .strength(-1, 3600000.0F)
            , Toys.TOY_15));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, int stackTo) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stackTo, Chronoline.MOD_TAB);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerToy(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, 1, Chronoline.EASTER_TAB);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, int stackTo, CreativeModeTab tab) {
        return RegisterItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()
                .stacksTo(stackTo)
                .tab(tab)
        ));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
