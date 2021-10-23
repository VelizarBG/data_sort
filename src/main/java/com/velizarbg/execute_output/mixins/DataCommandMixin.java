package com.velizarbg.execute_output.mixins;

import net.minecraft.command.DataCommandObject;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.command.DataCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DataCommand.class)
public interface DataCommandMixin {

    /*@Accessor("SOURCE_OBJECT_TYPES")
    static List<DataCommand.ObjectType> SOURCE_OBJECT_TYPES() {
        throw new AssertionError();
    }*/

    @Invoker("getNbt")
    static NbtElement getNbt(NbtPathArgumentType.NbtPath path, DataCommandObject object) {
        throw new AssertionError();
    }
}
