package com.velizarbg.data_sort;

import com.mojang.brigadier.CommandDispatcher;
import com.velizarbg.data_sort.command.DataSortCommand;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

public class DataSort implements ModInitializer {
    @Override
    public void onInitialize() {
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        DataSortCommand.register(dispatcher);
    }
}