/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import vazkii.botania.client.fx.SparkleParticleData;

import javax.annotation.Nonnull;

import java.util.Random;

public class BlockAltGrass extends BlockMod {

	public enum Variant {
		DRY,
		GOLDEN,
		VIVID,
		SCORCHED,
		INFUSED,
		MUTATED
	}

	private final Variant variant;

	public BlockAltGrass(Variant v, Settings builder) {
		super(builder);
		this.variant = v;
	}

	@Override
<<<<<<< HEAD
	public boolean isToolEffective(BlockState state, ToolType tool) {
		return tool.equals(ToolType.SHOVEL);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack held = player.getStackInHand(hand);
		if (held.getItem() instanceof HoeItem && world.isAir(pos.up())) {
			held.damage(1, player, e -> e.sendToolBreakStatus(hand));
=======
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack held = player.getHeldItem(hand);
		if (held.getItem() instanceof HoeItem && world.isAirBlock(pos.up())) {
			held.damageItem(1, player, e -> e.sendBreakAnimation(hand));
>>>>>>> be69f10c4... Floating flowers are now broken faster with a shovel
			world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (!world.isClient && state.getBlock() == this && world.getLightLevel(pos.up()) >= 9) {
			for (int l = 0; l < 4; ++l) {
				BlockPos pos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
				BlockPos pos1up = pos1.up();

				if (world.getBlockState(pos1).getBlock() == Blocks.DIRT && world.getLightLevel(pos1up) >= 4 && world.getBlockState(pos1up).getOpacity(world, pos1up) <= 2) {
					world.setBlockState(pos1, getDefaultState());
				}
			}
		}
	}

	@Override
	public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull BlockView world, BlockPos pos, @Nonnull Direction direction, IPlantable plantable) {
		PlantType type = plantable.getPlantType(world, pos.down());
		return type == PlantType.PLAINS || type == PlantType.BEACH || plantable instanceof StemBlock;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random r) {
		switch (variant) {
		case DRY:
			break;
		case GOLDEN:
			break;
		case VIVID:
			break;
		case SCORCHED:
			if (r.nextInt(80) == 0) {
				world.addParticle(ParticleTypes.FLAME, pos.getX() + r.nextFloat(), pos.getY() + 1.1, pos.getZ() + r.nextFloat(), 0, 0, 0);
			}
			break;
		case INFUSED:
			if (r.nextInt(100) == 0) {
				SparkleParticleData data = SparkleParticleData.sparkle(r.nextFloat() * 0.2F + 1F, 0F, 1F, 1F, 5);
				world.addParticle(data, pos.getX() + r.nextFloat(), pos.getY() + 1.05, pos.getZ() + r.nextFloat(), 0, 0, 0);
			}
			break;
		case MUTATED:
			if (r.nextInt(100) == 0) {
				if (r.nextInt(100) > 25) {
					SparkleParticleData data = SparkleParticleData.sparkle(r.nextFloat() * 0.2F + 1F, 1F, 0F, 1F, 5);
					world.addParticle(data, pos.getX() + r.nextFloat(), pos.getY() + 1.05, pos.getZ() + r.nextFloat(), 0, 0, 0);
				} else {
					SparkleParticleData data = SparkleParticleData.sparkle(r.nextFloat() * 0.2F + 1F, 1F, 1F, 0F, 5);
					world.addParticle(data, pos.getX() + r.nextFloat(), pos.getY() + 1.05, pos.getZ() + r.nextFloat(), 0, 0, 0);
				}
			}
			break;
		}
	}
}
