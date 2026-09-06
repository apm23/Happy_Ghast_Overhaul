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

/** Full military Happy Ghast replacement authored on the vanilla 64px adult body envelope. */
public final class MilitaryHarnessVisualModel extends EntityModel<HappyGhastRenderState> {
    public MilitaryHarnessVisualModel(ModelPart root) { super(root); }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        // Vanilla-scale visual body: exactly 64 x 64 x 64 model pixels. Never globally scale this model.
        box(r,"body",0,0,-32,-32,-32,64,64,64);
        // Nine 8px tentacles keep the replacement's lower silhouette anchored to the vanilla body.
        tentacle(r,"t0",-24,32,-24,0,70); tentacle(r,"t1",-4,32,-24,16,70); tentacle(r,"t2",16,32,-24,32,70);
        tentacle(r,"t3",-24,32,-4,48,70); tentacle(r,"t4",-4,32,-4,64,70); tentacle(r,"t5",16,32,-4,80,70);
        tentacle(r,"t6",-24,32,16,96,70); tentacle(r,"t7",-4,32,16,112,70); tentacle(r,"t8",16,32,16,128,70);

        // Upper-half exoskeleton: layered, thin plates following the body instead of a giant shell.
        box(r,"crown",0,96,-32,-34,-34,64,7,68);
        box(r,"front_brow",72,96,-30,-27,-36,60,10,5);
        box(r,"front_l",140,96,-33,-16,-35,18,19,5); box(r,"front_r",164,96,15,-16,-35,18,19,5);
        box(r,"temple_l",188,96,-36,-19,-30,5,25,24); box(r,"temple_r",220,96,31,-19,-30,5,25,24);
        sideModules(r,1); sideModules(r,-1);
        box(r,"rear_band",0,118,-30,-24,32,60,19,5);
        box(r,"rear_module_l",68,118,-30,-4,34,25,18,5); box(r,"rear_module_r",100,118,5,-4,34,25,18,5);

        // Open face and faction centerpiece. Purple eyes live on the replacement texture's body face.
        box(r,"keystone",132,118,-7,-19,-38,14,12,4);
        box(r,"center_spine",152,118,-3,-8,-39,6,22,4);
        box(r,"chevron_l",164,118,-12,8,-39,9,5,4); box(r,"chevron_r",178,118,3,8,-39,9,5,4);
        box(r,"center_tip",192,118,-3,13,-39,6,10,4);
        brace(r,"brace_l",-25,4); brace(r,"brace_r",20,24);

        // Fortress deck follows the 64px body top. It adds height but does not alter body/rider scale.
        box(r,"deck",0,144,-29,-38,-27,58,4,54);
        box(r,"deck_inset",64,144,-24,-41,-22,48,3,44);
        rim(r,"front",-29,-44,-29,58,3,3,118,144); rim(r,"back",-29,-44,26,58,3,3,118,150);
        rim(r,"left",-32,-44,-26,3,3,52,118,156); rim(r,"right",29,-44,-26,3,3,52,126,156);
        rail(r,"rf",-27,-51,-27,54,2,2,136,156); rail(r,"rb",-27,-51,25,54,2,2,136,160);
        rail(r,"rl",-27,-51,-25,2,2,50,136,164); rail(r,"rr",25,-51,-25,2,2,50,142,164);
        for (int sx : new int[]{-25,23}) for (int sz : new int[]{-25,23}) post(r,"p"+sx+"_"+sz,sx,-56,sz,150,164);

        // Reference rooftop: four perimeter beacons, taller command beacon and antenna cluster.
        tower(r,"tower_fl",-22,-64,-21,0,174); tower(r,"tower_fr",18,-64,-21,18,174);
        tower(r,"tower_bl",-22,-62,17,36,174); tower(r,"tower_br",18,-62,17,54,174);
        tower(r,"tower_command",-3,-70,-2,72,174);
        box(r,"antenna_a",94,174,-12,-70,11,1,16,1); box(r,"antenna_b",98,174,11,-67,8,1,13,1);
        box(r,"antenna_c",102,174,5,-65,18,1,11,1);
        lamp(r,"lamp_fl",-32,-29,-39,110,174); lamp(r,"lamp_fr",26,-29,-39,124,174);
        lamp(r,"lamp_l",35,-26,-9,138,174); lamp(r,"lamp_r",-41,-26,-9,152,174);

        // Tall pointed war banner, mounted at the rear-left of the fortress as in the master sheet.
        box(r,"banner_mast",0,198,-27,-82,17,2,45,2); box(r,"banner_finial",6,198,-28,-85,16,4,4,4);
        box(r,"banner_crossbar",16,198,-25,-81,17,25,2,2);
        box(r,"banner_top",46,198,-22,-78,18,19,19,1); box(r,"banner_mid",68,198,-20,-59,18,15,14,1);
        box(r,"banner_low",86,198,-17,-45,18,9,10,1); box(r,"banner_point",100,198,-13,-35,18,2,8,1);
        box(r,"banner_badge",108,198,-18,-70,16.8F,11,11,2); box(r,"banner_badge_core",124,198,-15,-67,15.8F,5,5,2);

        return LayerDefinition.create(mesh,256,256);
    }

    private static void sideModules(PartDefinition r,int side){
        float x=side>0?32:-39; String s=side>0?"l":"r";
        box(r,s+"_upper",0,130,x,-24,-27,7,16,54);
        box(r,s+"_pod_front",20,130,side>0?36:-44,-7,-24,8,19,18);
        box(r,s+"_pod_mid",46,130,side>0?37:-45,-3,-2,8,16,16);
        box(r,s+"_pod_rear",70,130,side>0?36:-44,-7,18,8,19,13);
    }
    private static void tentacle(PartDefinition r,String n,float x,float y,float z,int u,int v){box(r,n,u,v,x,y,z,8,22,8);}
    private static void brace(PartDefinition r,String n,float x,int u){box(r,n,u,166,x,4,-37,5,27,5);box(r,n+"_foot",u+8,166,x,27,-37,12,5,5);}
    private static void box(PartDefinition r,String n,int u,int v,float x,float y,float z,float w,float h,float d){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,w,h,d,new CubeDeformation(0)),PartPose.ZERO);}
    private static void rim(PartDefinition r,String n,float x,float y,float z,float w,float h,float d,int u,int v){box(r,"rim_"+n,u,v,x,y,z,w,h,d);}
    private static void rail(PartDefinition r,String n,float x,float y,float z,float w,float h,float d,int u,int v){box(r,n,u,v,x,y,z,w,h,d);}
    private static void post(PartDefinition r,String n,float x,float y,float z,int u,int v){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,2,8,2).texOffs(u+5,v).addBox(x-.5F,y-2,z-.5F,3,2,3),PartPose.ZERO);}
    private static void tower(PartDefinition r,String n,float x,float y,float z,int u,int v){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,4,13,4).texOffs(u+8,v).addBox(x-1,y-4,z-1,6,4,6).texOffs(u+8,v+10).addBox(x,y-6,z,4,2,4),PartPose.ZERO);}
    private static void lamp(PartDefinition r,String n,float x,float y,float z,int u,int v){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,6,7,4).texOffs(u,v+8).addBox(x+1,y+1,z-1,4,5,2),PartPose.ZERO);}
}
