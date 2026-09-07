package com.apm23.happyghastoverhaul.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.server.packs.resources.Resource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;

/**
 * Runtime container for the approved FINAL v2 UV mesh.
 *
 * The packed resource is deliberately simpler than GLB at runtime: HGV2 header,
 * then expanded triangle vertices (position xyz, uv, normal xyz) as little-endian floats.
 * The source GLB remains the authoring/Blender artifact; this packed form is only a
 * renderer-friendly transport generated from that exact geometry/UV set.
 */
public final class FinalV2PackedMesh {
    private static final int MAGIC = 0x32564748; // "HGV2" in little endian
    private static final int VERSION = 1;
    private static final int FLOATS_PER_VERTEX = 8;
    private static final int BYTES_PER_VERTEX = FLOATS_PER_VERTEX * Float.BYTES;

    /** GLB unit envelope -> vanilla 64px Happy Ghast body envelope. */
    private static final float MODEL_SCALE = 3.0F;

    private static FinalV2PackedMesh cached;
    private static boolean attemptedLoad;

    private final float[] vertices;
    private final int bodyVertexCount;
    private final int bannerVertexCount;

    private FinalV2PackedMesh(float[] vertices, int bodyVertexCount, int bannerVertexCount) {
        this.vertices = vertices;
        this.bodyVertexCount = bodyVertexCount;
        this.bannerVertexCount = bannerVertexCount;
    }

    public static Optional<FinalV2PackedMesh> get() {
        if (!attemptedLoad) {
            attemptedLoad = true;
            cached = load();
        }
        return Optional.ofNullable(cached);
    }

    private static FinalV2PackedMesh load() {
        try {
            Optional<Resource> resource = Minecraft.getInstance()
                    .getResourceManager()
                    .getResource(FinalV2ModelResources.PACKED_GEOMETRY);
            if (resource.isEmpty()) {
                return null;
            }

            byte[] bytes;
            try (var input = resource.get().open()) {
                bytes = input.readAllBytes();
            }

            ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
            if (buffer.remaining() < 16 || buffer.getInt() != MAGIC) {
                return null;
            }

            int version = buffer.getInt();
            int bodyVertices = buffer.getInt();
            int bannerVertices = buffer.getInt();
            if (version != VERSION || bodyVertices < 0 || bannerVertices < 0) {
                return null;
            }

            int totalVertices = Math.addExact(bodyVertices, bannerVertices);
            int expectedPayload = Math.multiplyExact(totalVertices, BYTES_PER_VERTEX);
            if (buffer.remaining() != expectedPayload) {
                return null;
            }

            float[] data = new float[Math.multiplyExact(totalVertices, FLOATS_PER_VERTEX)];
            for (int i = 0; i < data.length; i++) {
                data[i] = buffer.getFloat();
            }
            return new FinalV2PackedMesh(data, bodyVertices, bannerVertices);
        } catch (IOException | ArithmeticException ignored) {
            return null;
        }
    }

    public int bodyVertexCount() {
        return bodyVertexCount;
    }

    public int bannerVertexCount() {
        return bannerVertexCount;
    }

    /**
     * Submits both the rigid body and currently-static banner. bannerVertexCount is kept
     * separate so the cloth can later receive its own local transform without touching UVs.
     */
    public void render(PoseStack.Pose pose, VertexConsumer consumer, int packedLight) {
        for (int i = 0; i < vertices.length; i += FLOATS_PER_VERTEX) {
            float x = vertices[i] * MODEL_SCALE;
            float y = vertices[i + 1] * MODEL_SCALE;
            float z = vertices[i + 2] * MODEL_SCALE;
            float u = vertices[i + 3];
            float v = vertices[i + 4];
            float nx = vertices[i + 5];
            float ny = vertices[i + 6];
            float nz = vertices[i + 7];

            consumer.addVertex(pose, x, y, z)
                    .setColor(255, 255, 255, 255)
                    .setUv(u, v)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(packedLight)
                    .setNormal(pose, nx, ny, nz);
        }
    }
}
