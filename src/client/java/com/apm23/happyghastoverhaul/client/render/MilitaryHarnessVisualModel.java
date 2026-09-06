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

/** Adult-Happy-Ghast-sized geometry for the locked Military Harness silhouette. */
public final class MilitaryHarnessVisualModel extends EntityModel<HappyGhastRenderState> {
    public MilitaryHarnessVisualModel(ModelPart root) { super(root); }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        /*
         * Adult body envelope is roughly +/-32 model px.  Every visible armor surface below is
         * intentionally authored OUTSIDE that envelope.  The face is kept open: no 60px-wide
         * rectangular slab across the front anymore.  The silhouette is now layered/stepped like
         * the locked military concept instead of looking like a giant vanilla harness shell.
         */

        // Upper crown: four separated armored shoulders around the top edge.
        box(root,"crown_front_left",0,0,-29F,-31F,-38F,24F,7F,4F);
        box(root,"crown_front_right",30,0,5F,-31F,-38F,24F,7F,4F);
        box(root,"crown_front_center",60,0,-8F,-33F,-39F,16F,5F,4F);
        box(root,"crown_left_front",0,14,34F,-31F,-28F,4F,9F,24F);
        box(root,"crown_left_rear",30,14,34F,-31F,4F,4F,9F,24F);
        box(root,"crown_right_front",60,14,-38F,-31F,-28F,4F,9F,24F);
        box(root,"crown_right_rear",90,14,-38F,-31F,4F,4F,9F,24F);
        box(root,"crown_back_left",0,30,-29F,-31F,34F,24F,9F,4F);
        box(root,"crown_back_right",30,30,5F,-31F,34F,24F,9F,4F);

        // Angular forehead/visor structure.  Face center stays readable.
        box(root,"forehead_left",0,44,-25F,-22F,-39F,17F,6F,4F);
        box(root,"forehead_right",24,44,8F,-22F,-39F,17F,6F,4F);
        box(root,"forehead_bridge",48,44,-8F,-25F,-40F,16F,5F,4F);
        box(root,"brow_left",70,44,-24F,-11F,-40F,16F,4F,4F);
        box(root,"brow_right",92,44,8F,-11F,-40F,16F,4F,4F);
        box(root,"cheek_left",0,54,-29F,-4F,-39.5F,5F,17F,4F);
        box(root,"cheek_right",10,54,24F,-4F,-39.5F,5F,17F,4F);
        box(root,"jaw_left",20,54,-25F,13F,-39F,12F,4F,4F);
        box(root,"jaw_right",38,54,13F,13F,-39F,12F,4F,4F);

        // Long central downward spear/arrow, thinner than build #95 and farther forward.
        box(root,"center_spear_upper",56,54,-3F,-22F,-41F,6F,18F,4F);
        box(root,"center_spear_mid",66,54,-2F,-4F,-41.5F,4F,22F,4F);
        box(root,"center_spear_lower",74,54,-1.5F,18F,-42F,3F,15F,4F);
        box(root,"spear_tip",82,54,-1F,33F,-42.2F,2F,7F,4F);

        // Side utility boxes: compact military pods, clearly outside X +/-32.
        box(root,"utility_left_front",0,68,36F,-18F,-23F,8F,14F,15F);
        box(root,"utility_left_rear",22,68,36F,-17F,8F,8F,15F,17F);
        box(root,"utility_right_front",46,68,-44F,-18F,-23F,8F,14F,15F);
        box(root,"utility_right_rear",68,68,-44F,-17F,8F,8F,15F,17F);
        box(root,"pod_left",92,68,39F,0F,-11F,7F,9F,9F);
        box(root,"pod_right",108,68,-46F,0F,-11F,7F,9F,9F);

        // Top armored platform: smaller than the body footprint, with visible stepped rim.
        box(root,"deck",0,88,-25F,-38F,-22F,50F,3F,44F);
        box(root,"deck_spine",56,88,-5F,-40F,-17F,10F,2F,34F);
        box(root,"deck_front_lip",72,88,-24F,-41F,-23F,48F,3F,2F);
        box(root,"deck_back_lip",72,94,-24F,-41F,21F,48F,3F,2F);
        box(root,"deck_left_lip",0,98,23F,-41F,-21F,2F,3F,42F);
        box(root,"deck_right_lip",8,98,-25F,-41F,-21F,2F,3F,42F);

        rail(root,"rail_front",-24F,-45F,-24F,48F,1.5F,1.5F,18,98);
        rail(root,"rail_back",-24F,-45F,22.5F,48F,1.5F,1.5F,18,102);
        rail(root,"rail_left",22.5F,-45F,-22F,1.5F,1.5F,44F,18,106);
        rail(root,"rail_right",-24F,-45F,-22F,1.5F,1.5F,44F,18,110);
        post(root,"post_fl",21F,-51F,-21F,0,112); post(root,"post_fr",-23F,-51F,-21F,8,112);
        post(root,"post_bl",21F,-51F,19F,16,112); post(root,"post_br",-23F,-51F,19F,24,112);

        // Compact rear command post and antenna cluster.
        post(root,"command_post",9F,-54F,8F,32,112);
        box(root,"command_head",40,112,7F,-57F,6F,6F,4F,6F);
        box(root,"antenna_left",52,112,15F,-64F,9F,1F,11F,1F);
        box(root,"antenna_right",56,112,12F,-62F,13F,1F,8F,1F);
        box(root,"antenna_tip",60,112,14F,-66F,8F,3F,3F,3F);

        // Real amethyst lamp housings and visor core; pushed farther in front of the face.
        box(root,"lamp_left_housing",66,112,-27F,-24F,-40.5F,6F,6F,3F);
        box(root,"lamp_right_housing",76,112,21F,-24F,-40.5F,6F,6F,3F);
        box(root,"lamp_left_crystal",86,112,-25.5F,-22.5F,-42F,3F,3F,2F);
        box(root,"lamp_right_crystal",92,112,22.5F,-22.5F,-42F,3F,3F,2F);
        box(root,"visor_core",98,112,-3F,-12F,-42F,6F,3F,2F);

        // Faction standard rebuilt to the locked tall military banner silhouette.
        // Narrower cloth, stronger mast/crossbar and a pointed layered emblem.
        box(root,"banner_mast",0,122,-21F,-69F,15F,2F,30F,2F);
        box(root,"banner_finial",6,122,-22F,-72F,14F,4F,4F,4F);
        box(root,"banner_crossbar",14,122,-20F,-68F,15F,17F,2F,2F);
        box(root,"banner_cloth",36,122,-18F,-66F,15.5F,14F,22F,1F);
        box(root,"banner_top_trim",56,122,-18F,-66F,14.7F,14F,2F,2F);
        box(root,"banner_left_trim",74,122,-18F,-64F,14.7F,2F,20F,2F);
        box(root,"banner_right_trim",80,122,-6F,-64F,14.7F,2F,20F,2F);
        box(root,"banner_badge_outer",86,122,-14F,-60F,14.2F,6F,9F,1.5F);
        box(root,"banner_badge_inner",96,122,-12.5F,-58F,13.5F,3F,5F,1.5F);
        box(root,"banner_badge_tip",104,122,-11.5F,-53F,13.2F,1.5F,5F,1.5F);

        return LayerDefinition.create(mesh,128,128);
    }

    private static void box(PartDefinition r,String n,int u,int v,float x,float y,float z,float w,float h,float d){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,w,h,d,new CubeDeformation(0)),PartPose.ZERO);
    }
    private static void rail(PartDefinition r,String n,float x,float y,float z,float w,float h,float d,int u,int v){box(r,n,u,v,x,y,z,w,h,d);}
    private static void post(PartDefinition r,String n,float x,float y,float z,int u,int v){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,2,7,2).texOffs(u+4,v).addBox(x-.5F,y-2F,z-.5F,3,2,3),PartPose.ZERO);
    }
}
