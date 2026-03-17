package net.blumasc.blusbeasts.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ConfettiParticle extends TextureSheetParticle {
    private float startHue = 0f;
    private final SpriteSet spriteSet;
    private boolean despawn = false;
    protected ConfettiParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.friction = 0.5f;
        this.gravity = 0.7f;
        this.pickSprite(spriteSet);
        this.spriteSet = spriteSet;
        this.lifetime = 60;
        this.quadSize = 0.07f;
        startHue = this.random.nextFloat();
        int rgb = java.awt.Color.HSBtoRGB(startHue, 1.0f, 1.0f);
        this.rCol = ((rgb >> 16) & 0xFF) / 255f;
        this.gCol = ((rgb >> 8)  & 0xFF) / 255f;
        this.bCol = ( rgb        & 0xFF) / 255f;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.random.nextFloat()<0.1f) {
            this.pickSprite(spriteSet);
        }
        float hue = (this.startHue + this.age * 0.02f) % 1.0f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        this.rCol = ((rgb >> 16) & 0xFF) / 255f;
        this.gCol = ((rgb >> 8)  & 0xFF) / 255f;
        this.bCol = ( rgb        & 0xFF) / 255f;
        if (this.onGround && this.age<55) {
            this.age=55;
        }
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double pX, double pY, double pZ, double xS, double yS, double zS) {
            return new ConfettiParticle(clientLevel, pX, pY, pZ, spriteSet, xS, yS, zS);
        }
    }
}