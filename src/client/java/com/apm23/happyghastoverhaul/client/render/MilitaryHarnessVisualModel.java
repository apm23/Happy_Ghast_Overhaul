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

/** Geometry for the locked Military Harness reference. */
public final class MilitaryHarnessVisualModel extends EntityModel<HappyGhastRenderState> {
    public MilitaryHarnessVisualModel(ModelPart root) { super(root); }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        box(root,"crown_front",0,0,-9.5F,-9.5F,-10.25F,19,6,2.25F);
        box(root,"crown_left",0,10,8,-9.5F,-8,2.25F,8,16);
        box(root,"crown_right",38,10,-10.25F,-9.5F,-8,2.25F,8,16);
        box(root,"crown_back",0,35,-9.5F,-9.5F,8,19,8,2.25F);
        box(root,"forehead_plate",0,47,-5.5F,-8.75F,-11.20F,11,3,1.25F);
        box(root,"forehead_step",26,47,-3.5F,-6,-11.35F,7,2.25F,1.20F);
        box(root,"center_spear_upper",46,47,-1.25F,-8.25F,-11.65F,2.5F,6.25F,1.20F);
        box(root,"center_spear_lower",56,47,-0.75F,-2,-11.70F,1.5F,7.25F,1.15F);
        box(root,"spear_tip",62,47,-0.45F,5.25F,-11.72F,.9F,2.2F,1.10F);
        box(root,"brow_left",64,47,1.5F,-4.25F,-11.45F,6.25F,2,1.20F);
        box(root,"brow_right",64,52,-7.75F,-4.25F,-11.45F,6.25F,2,1.20F);
        box(root,"cheek_left",0,56,6.25F,-2,-10.90F,2.25F,7.5F,1.15F);
        box(root,"cheek_right",8,56,-8.5F,-2,-10.90F,2.25F,7.5F,1.15F);
        box(root,"jaw_left",12,56,4.5F,4.75F,-10.75F,3.5F,1.4F,1.1F);
        box(root,"jaw_right",12,60,-8,4.75F,-10.75F,3.5F,1.4F,1.1F);
        box(root,"utility_left_front",18,56,9.25F,-6.5F,-6.75F,3.75F,6,5.25F);
        box(root,"utility_left_rear",38,56,9,-6,1,4.25F,6.5F,6.25F);
        box(root,"utility_right_front",62,56,-13,-6.5F,-6.25F,3.75F,6,5);
        box(root,"utility_right_rear",82,56,-13.25F,-6,1,4.25F,6.5F,6.25F);
        box(root,"pod_left",96,56,10.2F,-1,-4.2F,2.5F,3.25F,3.25F);
        box(root,"pod_right",108,56,-12.7F,-1,-4.2F,2.5F,3.25F,3.25F);
        box(root,"deck",0,70,-9,-11.25F,-8,18,2,16);
        box(root,"deck_spine",68,70,-2.25F,-12.35F,-6.5F,4.5F,1.15F,13);
        rail(root,"rail_front",-9,-14,-8.75F,18,.75F,.75F,0,90);
        rail(root,"rail_back",-9,-14,8,18,.75F,.75F,0,94);
        rail(root,"rail_left",8,-14,-8,.75F,.75F,16,40,90);
        rail(root,"rail_right",-8.75F,-14,-8,.75F,.75F,16,44,90);
        post(root,"post_fl",7,-18,-7,0,100); post(root,"post_fr",-8,-18,-7,8,100);
        post(root,"post_bl",7,-18,6,16,100); post(root,"post_br",-8,-18,6,24,100);
        post(root,"command_post",3.5F,-21,3.5F,32,100);
        box(root,"command_head",40,100,2.25F,-23.25F,2.25F,3.5F,2.5F,3.5F);

        // Physical lamp housings/crystals: these protrude in-game instead of being painted pixels.
        box(root,"lamp_left_housing",0,112,7.1F,-8.7F,-10.95F,2.15F,2.15F,1.25F);
        box(root,"lamp_right_housing",8,112,-9.25F,-8.7F,-10.95F,2.15F,2.15F,1.25F);
        box(root,"lamp_left_crystal",16,112,7.55F,-8.25F,-11.45F,1.25F,1.25F,.65F);
        box(root,"lamp_right_crystal",22,112,-8.8F,-8.25F,-11.45F,1.25F,1.25F,.65F);
        box(root,"visor_core",28,112,-1F,-4.35F,-12.05F,2F,1.2F,.55F);
        // Antenna cluster strengthens the military command-platform silhouette.
        box(root,"antenna_left",36,112,5.4F,-25.5F,4.2F,.55F,8,.55F);
        box(root,"antenna_right",40,112,4.1F,-24,5.25F,.5F,6.5F,.5F);
        box(root,"antenna_tip",44,112,5.05F,-26.35F,3.85F,1.25F,1.25F,1.25F);

        box(root,"banner_mast",48,96,-7.75F,-27,5.75F,1,17.5F,1);
        box(root,"banner_finial",52,96,-8.15F,-28,5.35F,1.8F,1.4F,1.8F);
        box(root,"banner_crossbar",54,96,-7.25F,-26.5F,5.75F,9,1,1);
        box(root,"banner_cloth",64,96,-6.75F,-25.25F,5.90F,7.5F,12.75F,.35F);
        return LayerDefinition.create(mesh,128,128);
    }
    private static void box(PartDefinition r,String n,int u,int v,float x,float y,float z,float w,float h,float d){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,w,h,d,new CubeDeformation(0)),PartPose.ZERO);}
    private static void rail(PartDefinition r,String n,float x,float y,float z,float w,float h,float d,int u,int v){box(r,n,u,v,x,y,z,w,h,d);}
    private static void post(PartDefinition r,String n,float x,float y,float z,int u,int v){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,1,6,1).texOffs(u+4,v).addBox(x-.5F,y-1.5F,z-.5F,2,1.5F,2),PartPose.ZERO);}
}
