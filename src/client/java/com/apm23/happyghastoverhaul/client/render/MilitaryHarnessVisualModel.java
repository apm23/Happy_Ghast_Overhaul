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

        // Adult Happy Ghast body is roughly 64 model pixels across. Armor is authored
        // directly in that coordinate space so the renderer must not globally scale it.
        // Front remains deliberately open around the eyes/mouth.
        box(root,"crown_front",0,0,-30F,-30F,-33F,60F,10F,3F);
        box(root,"crown_left",0,14,30F,-30F,-30F,3F,20F,60F);
        box(root,"crown_right",64,14,-33F,-30F,-30F,3F,20F,60F);
        box(root,"crown_back",0,38,-30F,-30F,30F,60F,14F,3F);

        box(root,"forehead_plate",0,56,-18F,-20F,-34F,36F,7F,3F);
        box(root,"forehead_step",40,56,-11F,-13F,-34.8F,22F,5F,3F);
        box(root,"center_spear_upper",68,56,-4F,-19F,-35.5F,8F,19F,3F);
        box(root,"center_spear_lower",80,56,-2.5F,0F,-35.7F,5F,24F,3F);
        box(root,"spear_tip",88,56,-1.5F,24F,-35.8F,3F,8F,3F);
        box(root,"brow_left",94,56,5F,-8F,-35F,20F,5F,3F);
        box(root,"brow_right",94,64,-25F,-8F,-35F,20F,5F,3F);
        box(root,"cheek_left",0,70,24F,-2F,-34.5F,7F,22F,3F);
        box(root,"cheek_right",10,70,-31F,-2F,-34.5F,7F,22F,3F);
        box(root,"jaw_left",20,70,15F,19F,-34.2F,12F,4F,3F);
        box(root,"jaw_right",20,77,-27F,19F,-34.2F,12F,4F,3F);

        // Side utility armor sits outside the 64px body instead of intersecting it.
        box(root,"utility_left_front",38,70,32F,-19F,-23F,8F,17F,17F);
        box(root,"utility_left_rear",64,70,32F,-18F,7F,9F,18F,19F);
        box(root,"utility_right_front",94,70,-40F,-19F,-23F,8F,17F,17F);
        box(root,"utility_right_rear",0,92,-41F,-18F,7F,9F,18F,19F);
        box(root,"pod_left",30,92,36F,1F,-14F,6F,10F,10F);
        box(root,"pod_right",48,92,-42F,1F,-14F,6F,10F,10F);

        // Full-size armored deck rides just above the adult body top (-32).
        box(root,"deck",64,92,-29F,-35F,-27F,58F,3F,54F);
        box(root,"deck_spine",0,112,-7F,-37F,-22F,14F,2F,44F);
        box(root,"deck_front_lip",60,112,-28F,-38F,-28F,56F,3F,2F);
        box(root,"deck_back_lip",60,118,-28F,-38F,26F,56F,3F,2F);
        rail(root,"rail_front",-29F,-42F,-29F,58F,2F,2F,0,124);
        rail(root,"rail_back",-29F,-42F,27F,58F,2F,2F,0,124);
        rail(root,"rail_left",27F,-42F,-27F,2F,2F,54F,0,124);
        rail(root,"rail_right",-29F,-42F,-27F,2F,2F,54F,0,124);
        post(root,"post_fl",26F,-49F,-26F,0,100); post(root,"post_fr",-28F,-49F,-26F,8,100);
        post(root,"post_bl",26F,-49F,24F,16,100); post(root,"post_br",-28F,-49F,24F,24,100);

        // Compact command station: accessories are intentionally NOT scaled with the body.
        post(root,"command_post",11F,-52F,10F,32,100);
        box(root,"command_head",40,100,9F,-55F,8F,5F,4F,5F);
        box(root,"antenna_left",36,112,17F,-61F,12F,1F,12F,1F);
        box(root,"antenna_right",40,112,14F,-59F,16F,1F,9F,1F);
        box(root,"antenna_tip",44,112,16F,-63F,11F,3F,3F,3F);

        // Physical amethyst lamps/visor.
        box(root,"lamp_left_housing",0,112,22F,-22F,-35F,6F,6F,3F);
        box(root,"lamp_right_housing",8,112,-28F,-22F,-35F,6F,6F,3F);
        box(root,"lamp_left_crystal",16,112,23.5F,-20.5F,-36.2F,3F,3F,2F);
        box(root,"lamp_right_crystal",22,112,-26.5F,-20.5F,-36.2F,3F,3F,2F);
        box(root,"visor_core",28,112,-3F,-8F,-36.5F,6F,3F,2F);

        // Rebuilt faction standard: large, readable, framed and physically layered.
        // It is placed on the rear-left deck so it does not cover the Ghast face.
        box(root,"banner_mast",48,96,-24F,-67F,18F,2F,31F,2F);
        box(root,"banner_finial",52,96,-25F,-70F,17F,4F,4F,4F);
        box(root,"banner_crossbar",54,96,-23F,-66F,18F,20F,2F,2F);
        box(root,"banner_cloth",64,96,-21F,-64F,18.5F,17F,24F,1F);
        box(root,"banner_top_trim",92,96,-21F,-64F,17.8F,17F,2F,2F);
        box(root,"banner_bottom_trim",92,100,-21F,-42F,17.8F,17F,2F,2F);
        box(root,"banner_badge_outer",92,104,-16F,-58F,17.2F,7F,11F,1.5F);
        box(root,"banner_badge_inner",108,104,-14.5F,-56F,16.5F,4F,7F,1.5F);
        box(root,"banner_badge_tip",118,104,-13.5F,-49F,16.3F,2F,4F,1.5F);

        return LayerDefinition.create(mesh,128,128);
    }

    private static void box(PartDefinition r,String n,int u,int v,float x,float y,float z,float w,float h,float d){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,w,h,d,new CubeDeformation(0)),PartPose.ZERO);
    }
    private static void rail(PartDefinition r,String n,float x,float y,float z,float w,float h,float d,int u,int v){box(r,n,u,v,x,y,z,w,h,d);}
    private static void post(PartDefinition r,String n,float x,float y,float z,int u,int v){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,2,8,2).texOffs(u+4,v).addBox(x-.5F,y-2F,z-.5F,3,2,3),PartPose.ZERO);
    }
}
