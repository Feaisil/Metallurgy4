package com.teammetallurgy.metallurgy.networking;

import java.io.File;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FileResourcePack;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ClientProxy extends CommonProxy
{

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void injectZipAsResource(String zipDir)
    {

        Object value = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, FMLClientHandler.instance().getClient(), "defaultResourcePacks");

        if (value instanceof List)
        {
            FileResourcePack pack = new FileResourcePack(new File(zipDir));

            ((List) value).add(pack);
        }
        FMLClientHandler.instance().getClient().refreshResources();
    }
}
