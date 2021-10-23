package com.velizarbg.execute_output;

import com.mojang.brigadier.CommandDispatcher;
import com.velizarbg.execute_output.command.TestCommand;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

public class ExecuteOutput implements ModInitializer {
    @Override
    public void onInitialize() {
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        TestCommand.register(dispatcher);
    }
}