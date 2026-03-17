package net.blumasc.blusbeasts.entity.client.livinglightning;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.LivingLightningEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class LivingLightningRenderer extends MobRenderer<LivingLightningEntity, LivingLightningModel<LivingLightningEntity>> {
    public LivingLightningRenderer(EntityRendererProvider.Context context) {
        super(context, new LivingLightningModel<>(context.bakeLayer(LivingLightningModel.LAYER_LOCATION)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingLightningEntity livingLightningEntity) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/living_lightning/texture.png");
    }

    @Override
    public void render(
            LivingLightningEntity entity,
            float yaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        super.render(entity, yaw, partialTick, poseStack, buffer, packedLight);

        poseStack.pushPose();

        Vec3 camPos = this.entityRenderDispatcher.camera.getPosition();

        RandomSource rand = RandomSource.create(entity.getId() * 31L + entity.tickCount / 2);

        int arcCount = 1 + rand.nextInt(3);

        for (int i = 0; i < arcCount; i++) {

            double radius = rand.nextDouble();
            double angle = rand.nextDouble() * Math.PI * 2;
            double height = (rand.nextDouble() - 0.5) * 1.5;

            Vec3 start = new Vec3(0, entity.getBbHeight() * 0.5, 0);

            Vec3 end = new Vec3(
                    Math.cos(angle) * radius,
                    height,
                    Math.sin(angle) * radius
            );

            renderArc(
                    poseStack,
                    buffer,
                    start,
                    end,
                    camPos.subtract(entity.position()),
                    rand.nextLong()
            );
        }

        poseStack.popPose();
    }

    public static void renderArc(
            PoseStack poseStack,
            MultiBufferSource buffer,
            Vec3 start,
            Vec3 end,
            Vec3 cameraPos,
            long seed
    ) {
        Vec3 dir = end.subtract(start);
        double length = dir.length();
        if (length < 0.001) return;

        Vec3 norm = dir.normalize();

        VertexConsumer vc = buffer.getBuffer(RenderType.lightning());
        RandomSource rand = RandomSource.create(seed);

        Matrix4f mat = poseStack.last().pose();

        int segments = Math.max(8, (int)(length * 2.0));
        float baseWidth = 0.06f;

        Vec3 prevOffset = Vec3.ZERO;

        for (int i = 0; i < segments; i++) {
            double t1 = (double)i / segments;
            double t2 = (double)(i + 1) / segments;

            Vec3 p1 = start.add(norm.scale(t1 * length));
            Vec3 p2 = start.add(norm.scale(t2 * length));

            Vec3 jitter = new Vec3(
                    (rand.nextFloat() - 0.5) * 0.6,
                    (rand.nextFloat() - 0.5) * 0.6,
                    (rand.nextFloat() - 0.5) * 0.6
            );

            Vec3 a = p1.add(prevOffset);
            Vec3 b = p2.add(jitter);
            prevOffset = jitter;

            Vec3 camDir = cameraPos.subtract(a).normalize();
            Vec3 right = norm.cross(camDir).normalize();

            float width = baseWidth * (1.0f - (float)t1 * 0.6f);
            Vec3 w = right.scale(width);

            quad(mat, vc, a, b, w);
        }
    }

    private static void quad(Matrix4f mat, VertexConsumer vc, Vec3 a, Vec3 b, Vec3 w) {
        vc.addVertex(mat,(float)(a.x+w.x),(float)(a.y+w.y),(float)(a.z+w.z)).setColor(0.45f,0.45f,1f,0.85f);
        vc.addVertex(mat,(float)(b.x+w.x),(float)(b.y+w.y),(float)(b.z+w.z)).setColor(0.45f,0.45f,1f,0.85f);
        vc.addVertex(mat,(float)(b.x-w.x),(float)(b.y-w.y),(float)(b.z-w.z)).setColor(0.45f,0.45f,1f,0.85f);
        vc.addVertex(mat,(float)(a.x-w.x),(float)(a.y-w.y),(float)(a.z-w.z)).setColor(0.45f,0.45f,1f,0.85f);
    }
}
