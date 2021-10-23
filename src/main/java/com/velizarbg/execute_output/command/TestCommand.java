package com.velizarbg.execute_output.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.DataCommandObject;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.command.DataCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

import static com.velizarbg.execute_output.mixins.DataCommandMixin.getNbt;
import static net.minecraft.nbt.StringNbtReader.parse;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.DataCommand.TARGET_OBJECT_TYPES;

public class TestCommand {
    public static final SimpleCommandExceptionType NOT_A_LIST_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.data.sort.not_list"));
    public static final DynamicCommandExceptionType NOT_A_STRING_LIST_EXCEPTION = new DynamicCommandExceptionType((object) -> new TranslatableText("commands.data.sort.not_string_list", object));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = literal("data");

        for (DataCommand.ObjectType objectType : TARGET_OBJECT_TYPES) {
            literalArgumentBuilder.then(
                objectType.addArgumentsToBuilder(literal("sort"), (builder) -> builder.then(
                    argument("path", NbtPathArgumentType.nbtPath()).
                        executes((context) -> sortArray(context.getSource(), objectType.getObject(context), NbtPathArgumentType.getNbtPath(context, "path"))))));
        }

        dispatcher.register(literalArgumentBuilder);
        /*literalArgumentBuilder.
            then(literal("sort").
                then(literal("entity").
                    then(
                        argument("target", EntityArgumentType.entity()).
                            then(
                                argument("path", NbtPathArgumentType.nbtPath()).
                                    executes(c -> initVars(c, DataType.ENTITY))
                            )
                    )
                ).
                then(literal("block").
                    then(
                        argument("pos", BlockPosArgumentType.blockPos()).
                            then(
                                argument("path", NbtPathArgumentType.nbtPath()).
                                    executes(c -> initVars(c, DataType.BLOCK))
                            )
                    )
                ).
                then(literal("storage").
                    then(
                        argument("storage", IdentifierArgumentType.identifier()).
                            then(
                                argument("path", NbtPathArgumentType.nbtPath()).
                                    executes(c -> initVars(c, DataType.STORAGE))
                            )
                    )
                )
            );*/
    }

    private static int sortArray(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
        NbtCompound nbtCompound = object.getNbt();
        NbtElement nbtElement = getNbt(path, object);
        //String haha = "{pages:[\"heh \",\"hah\"]}";
        //nbtCompound.copyFrom(parse(haha));
        if (nbtElement.getType() == 9) {
            if (((NbtList) nbtElement).get(0).getType() == 8) {
                System.out.println(((NbtList) nbtElement).getString(0));
                //String nbtElementString = nbtElement.asString();
                //nbtElementString = nbtElementString.substring(2, nbtElementString.length() - 2);
                //Collection<String> collection = List.of(nbtElementString.split("\"(.*?)\""));

                List<String> list = new ArrayList<>();
                int listSize = ((NbtList) nbtElement).size();
                for (int i = 0; i < listSize; i++) {
                    list.add(((NbtList) nbtElement).getString(i));
                }
                //List<T> list = Lists.newArrayList((Iterable) collection);
                list.sort(Comparable::compareTo);

                StringBuilder sortedNbtElementString = new StringBuilder("{" + path.toString() + ":[");
                for (String t : list) {
                    sortedNbtElementString.append("\"" + t + "\",");
                }
                sortedNbtElementString.deleteCharAt(sortedNbtElementString.length() - 1).append("]}");
                nbtCompound.copyFrom(parse(String.valueOf(sortedNbtElementString)));
                object.setNbt(nbtCompound);
                source.sendFeedback(object.feedbackModify(), true);
            } else
                throw NOT_A_STRING_LIST_EXCEPTION.create(((NbtList) nbtElement).get(0).getNbtType().getCommandFeedbackName());
        } else throw NOT_A_LIST_EXCEPTION.create();
        return 1;
    }
}
