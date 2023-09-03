package net.gartersnake.diabetesmod.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.gartersnake.diabetesmod.fluid.ModFluids;
import net.gartersnake.diabetesmod.item.ModItems;
import net.gartersnake.diabetesmod.networking.ModPackets;
import net.gartersnake.diabetesmod.screen.FermentationTankScreenHandler;
import net.gartersnake.diabetesmod.util.FluidStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FermentationTankBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 300;
    private final long maxCapacity = FluidConstants.DROPLET * 100;

    public FermentationTankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FERMENTATION_TANK, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch(index) {
                    case 0 -> FermentationTankBlockEntity.this.progress;
                    case 1 -> FermentationTankBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0 -> FermentationTankBlockEntity.this.progress = value;
                    case 1 -> FermentationTankBlockEntity.this.maxProgress = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    // Insulin Storage
    public final SingleVariantStorage<FluidVariant> insulinStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return maxCapacity;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            if (!world.isClient) {
                syncInsulinAmount();
            }
        }
    };

    private void syncInsulinAmount() {
        PacketByteBuf buf = PacketByteBufs.create();
        insulinStorage.variant.toPacket(buf);
        buf.writeLong(insulinStorage.amount);
        buf.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModPackets.SYNC_INSULIN, buf);
        }
    }

    public void setInsulinLevel(FluidVariant fluidVariant, long insulinLevel) {
        this.insulinStorage.variant = fluidVariant;
        this.insulinStorage.amount = insulinLevel;
    }

    // Functionality
    @Override
    public Text getDisplayName() {
        return Text.translatable("inventory.diabetesmod.fermentation_tank");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        syncInsulinAmount();
        return new FermentationTankScreenHandler(syncId, playerInventory,
                this, this.propertyDelegate);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("fermentation_tank.progress", progress);
        nbt.put("fermentation_tank.variant", insulinStorage.variant.toNbt());
        nbt.putLong("fermentation_tank.insulin_amount", insulinStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("fermentation_tank.progress");
        insulinStorage.variant = FluidVariant.fromNbt((NbtCompound)nbt.get("fermentation_tank.variant"));
        insulinStorage.amount = nbt.getLong("fermentation_tank.insulin_amount");
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, FermentationTankBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if (hasRecipe(entity)) {
            entity.progress++;
            markDirty(world, blockPos, state);
            if (entity.progress >= entity.maxProgress) {
                extractInsulin(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }
    }

    private static void extractInsulin(FermentationTankBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        if (hasRecipe(entity)) {
            try(Transaction transaction = Transaction.openOuter()) {
                entity.insulinStorage.insert(FluidVariant.of(ModFluids.STILL_INSULIN),
                        FluidConstants.DROPLET * 20, transaction);
                transaction.commit();
                entity.removeStack(0, 1);
            }
            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(FermentationTankBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        boolean hasPancreasInSlot = entity.getStack(0)
                .getItem() == ModItems.PANCREAS;

        boolean isFull = entity.insulinStorage.amount >= entity.maxCapacity;

        return hasPancreasInSlot && !isFull;
    }
}
