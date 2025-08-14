package org.tor_tik96.chronoline.Blocks;

import net.minecraft.world.item.BlockItem;
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

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, int stackTo) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stackTo);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, int stackTo) {
        return RegisterItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()
                .stacksTo(stackTo)
                .tab(Chronoline.MOD_TAB)
        ));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
