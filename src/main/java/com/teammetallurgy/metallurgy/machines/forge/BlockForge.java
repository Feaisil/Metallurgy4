package com.teammetallurgy.metallurgy.machines.forge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.teammetallurgy.metallurgy.Metallurgy;
import com.teammetallurgy.metallurgy.lib.GUIIds;
import com.teammetallurgy.metallurgy.machines.BlockMetallurgy;

public class BlockForge extends BlockMetallurgy
{

    public BlockForge()
    {
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityForge();
    }

    @Override
    protected void doOnActivate(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
        player.openGui(Metallurgy.instance, GUIIds.FORGE, world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {

        TileEntityForge blockTileEntity = (TileEntityForge) world.getTileEntity(x, y, z);

        ItemStack equippedItem = player.getCurrentEquippedItem();

        if (equippedItem != null && equippedItem.getItem() == Items.lava_bucket)
        {
            FluidStack stack = new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);
            int fill = blockTileEntity.fill(ForgeDirection.UNKNOWN, stack, false);

            if (fill == FluidContainerRegistry.BUCKET_VOLUME)
            {
                blockTileEntity.fill(ForgeDirection.UNKNOWN, stack, true);
                equippedItem.stackSize--;

                if (equippedItem.stackSize <= 0)
                {
                    ItemStack stack2 = equippedItem.getItem().getContainerItem(equippedItem);
                    player.inventory.addItemStackToInventory(stack2);
                }

                world.markBlockForUpdate(x, y, z);
                return true;
            }
        }

        return super.onBlockActivated(world, x, y, z, player, side, xOffset, yOffset, zOffset);
    }

}
