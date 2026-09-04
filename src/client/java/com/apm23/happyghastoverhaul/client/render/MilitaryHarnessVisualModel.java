package com.apm23.happyghastoverhaul.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;

/**
 * Geometry-first pass for the locked Military Harness visual reference.
 *
 * The silhouette deliberately uses real cuboids for the parts that must visibly protrude:
 * layered forehead/face armor, side utility modules, top deck, rails, command posts and
 * the banner mast. Micro-detail (bolts, scratches, panel lines) belongs in the texture pass.
 */
public final class MilitaryHarnessVisualModel extends EntityModel<HappyGhastRenderState> {
    public MilitaryHarnessVisualModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Main armored crown around the upper half of the Happy Ghast.
        root.addOrReplaceChild("crown_front",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-9.5F, -9.5F, -10.25F, 19.0F, 6.0F, 2.25F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        root.addOrReplaceChild("crown_left",
                CubeListBuilder.create().texOffs(0, 10)
                        .addBox(8.0F, -9.5F, -8.0F, 2.25F, 8.0F, 16.0F),
                PartPose.ZERO);
        root.addOrReplaceChild("crown_right",
                CubeListBuilder.create().texOffs(38, 10)
                        .addBox(-10.25F, -9.5F, -8.0F, 2.25F, 8.0F, 16.0F),
                PartPose.ZERO);
        root.addOrReplaceChild("crown_back",
                CubeListBuilder.create().texOffs(0, 35)
                        .addBox(-9.5F, -9.5F, 8.0F, 19.0F, 8.0F, 2.25F),
                PartPose.ZERO);

        // Layered angular forehead and the long downward center spear from the reference.
        root.addOrReplaceChild("forehead_plate",
                CubeListBuilder.create().texOffs(0, 47)
                        .addBox(-5.5F, -8.75F, -11.20F, 11.0F, 3.0F, 1.25F),
                PartPose.ZERO);
        root.addOrReplaceChild("forehead_step",
                CubeListBuilder.create().texOffs(26, 47)
                        .addBox(-3.5F, -6.0F, -11.35F, 7.0F, 2.25F, 1.20F),
                PartPose.ZERO);
        root.addOrReplaceChild("center_spear_upper",
                CubeListBuilder.create().texOffs(46, 47)
                        .addBox(-1.25F, -8.25F, -11.65F, 2.5F, 6.25F, 1.20F),
                PartPose.ZERO);
        root.addOrReplaceChild("center_spear_lower",
                CubeListBuilder.create().texOffs(56, 47)
                        .addBox(-0.75F, -2.0F, -11.70F, 1.5F, 5.5F, 1.15F),
                PartPose.ZERO);

        // Brow blocks leave the vanilla face visible while creating the visor-like silhouette.
        root.addOrReplaceChild("brow_left",
                CubeListBuilder.create().texOffs(64, 47)
                        .addBox(1.5F, -4.25F, -11.45F, 6.25F, 2.0F, 1.20F),
                PartPose.ZERO);
        root.addOrReplaceChild("brow_right",
                CubeListBuilder.create().texOffs(64, 52)
                        .addBox(-7.75F, -4.25F, -11.45F, 6.25F, 2.0F, 1.20F),
                PartPose.ZERO);

        // Lower cheek brackets: intentionally do not cover the whole white face.
        root.addOrReplaceChild("cheek_left",
                CubeListBuilder.create().texOffs(0, 56)
                        .addBox(6.25F, -2.0F, -10.90F, 2.25F, 7.5F, 1.15F),
                PartPose.ZERO);
        root.addOrReplaceChild("cheek_right",
                CubeListBuilder.create().texOffs(8, 56)
                        .addBox(-8.5F, -2.0F, -10.90F, 2.25F, 7.5F, 1.15F),
                PartPose.ZERO);

        // Side equipment boxes, deliberately asymmetric in depth to avoid a flat ring silhouette.
        root.addOrReplaceChild("utility_left_front",
                CubeListBuilder.create().texOffs(18, 56)
                        .addBox(9.25F, -6.5F, -6.75F, 3.75F, 6.0F, 5.25F),
                PartPose.ZERO);
        root.addOrReplaceChild("utility_left_rear",
                CubeListBuilder.create().texOffs(38, 56)
                        .addBox(9.0F, -6.0F, 1.0F, 4.25F, 6.5F, 6.25F),
                PartPose.ZERO);
        root.addOrReplaceChild("utility_right_front",
                CubeListBuilder.create().texOffs(62, 56)
                        .addBox(-13.0F, -6.5F, -6.25F, 3.75F, 6.0F, 5.0F),
                PartPose.ZERO);
        root.addOrReplaceChild("utility_right_rear",
                CubeListBuilder.create().texOffs(82, 56)
                        .addBox(-13.25F, -6.0F, 1.0F, 4.25F, 6.5F, 6.25F),
                PartPose.ZERO);

        // Top armored deck.
        root.addOrReplaceChild("deck",
                CubeListBuilder.create().texOffs(0, 70)
                        .addBox(-9.0F, -11.25F, -8.0F, 18.0F, 2.0F, 16.0F),
                PartPose.ZERO);

        // Railings: block-thin but truly 3D.
        addRail(root, "rail_front", -9.0F, -14.0F, -8.75F, 18.0F, 0.75F, 0.75F, 0, 90);
        addRail(root, "rail_back", -9.0F, -14.0F, 8.0F, 18.0F, 0.75F, 0.75F, 0, 94);
        addRail(root, "rail_left", 8.0F, -14.0F, -8.0F, 0.75F, 0.75F, 16.0F, 40, 90);
        addRail(root, "rail_right", -8.75F, -14.0F, -8.0F, 0.75F, 0.75F, 16.0F, 44, 90);

        // Corner/command posts and lamp housings.
        addPost(root, "post_fl", 7.0F, -18.0F, -7.0F, 0, 100);
        addPost(root, "post_fr", -8.0F, -18.0F, -7.0F, 8, 100);
        addPost(root, "post_bl", 7.0F, -18.0F, 6.0F, 16, 100);
        addPost(root, "post_br", -8.0F, -18.0F, 6.0F, 24, 100);
        addPost(root, "command_post", 3.5F, -21.0F, 3.5F, 32, 100);

        // Banner mast on rear-right quadrant, matching the reference placement.
        root.addOrReplaceChild("banner_mast",
                CubeListBuilder.create().texOffs(48, 96)
                        .addBox(-7.75F, -25.0F, 5.75F, 1.0F, 15.5F, 1.0F),
                PartPose.ZERO);
        root.addOrReplaceChild("banner_crossbar",
                CubeListBuilder.create().texOffs(54, 96)
                        .addBox(-7.25F, -25.0F, 5.75F, 8.0F, 1.0F, 1.0F),
                PartPose.ZERO);

        // Flat-but-thick cloth placeholder. Texture pass carries the locked skull/banner artwork.
        root.addOrReplaceChild("banner_cloth",
                CubeListBuilder.create().texOffs(64, 96)
                        .addBox(-6.75F, -23.75F, 5.90F, 6.5F, 11.5F, 0.35F),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void addRail(PartDefinition root, String name,
                                float x, float y, float z, float w, float h, float d,
                                int u, int v) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).addBox(x, y, z, w, h, d),
                PartPose.ZERO);
    }

    private static void addPost(PartDefinition root, String name,
                                float x, float y, float z, int u, int v) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v)
                        .addBox(x, y, z, 1.0F, 6.0F, 1.0F)
                        .texOffs(u + 4, v)
                        .addBox(x - 0.5F, y - 1.5F, z - 0.5F, 2.0F, 1.5F, 2.0F),
                PartPose.ZERO);
    }
}
