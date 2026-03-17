package net.blumasc.blusbeasts.item.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CombinedBakedModel implements BakedModel {

    private final BakedModel base;
    private final List<BakedModel> layers;

    public CombinedBakedModel(BakedModel base, List<BakedModel> layers) {
        this.base = base;
        this.layers = layers;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state,
                                    @Nullable Direction side,
                                    RandomSource rand) {
        List<BakedQuad> merged = new ArrayList<>();
        merged.addAll(base.getQuads(state, side, rand));
        for (BakedModel layer : layers) {
            merged.addAll(layer.getQuads(state, side, rand));
        }
        return merged;
    }

    @Override
    public ItemTransforms getTransforms() {
        return base.getTransforms();
    }

    @Override public boolean useAmbientOcclusion() { return base.useAmbientOcclusion(); }
    @Override public boolean isGui3d() { return base.isGui3d(); }
    @Override public boolean usesBlockLight() { return base.usesBlockLight(); }
    @Override public boolean isCustomRenderer() { return false; }
    @Override public TextureAtlasSprite getParticleIcon() { return base.getParticleIcon(); }
    @Override public ItemOverrides getOverrides() { return base.getOverrides(); }
}
