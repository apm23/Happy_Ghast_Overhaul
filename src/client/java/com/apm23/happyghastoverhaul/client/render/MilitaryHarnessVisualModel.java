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
        MeshDefinition mesh=new MeshDefinition(); PartDefinition r=mesh.getRoot();
        // Body gets an isolated 256px-wide UV island in the right half of the 512x256 atlas.
        box(r,"body",256,0,-32,-32,-32,64,64,64);
        int ti=0; for(int z:new int[]{-24,-4,16}) for(int x:new int[]{-24,-4,16}) tentacle(r,"tentacle_"+(ti++),x,32,z,256+((ti-1)%6)*16,130+((ti-1)/6)*28);
        box(r,"crown",0,96,-32,-34,-34,64,7,68); box(r,"brow",72,96,-30,-27,-36,60,10,5);
        plate(r,"front_ul",-30,-25,-37,27,7,3); plate(r,"front_ur",3,-25,-37,27,7,3); plate(r,"front_ml",-31,-16,-37,15,10,3); plate(r,"front_mr",16,-16,-37,15,10,3);
        plate(r,"front_corner_l",-35,-23,-32,5,25,20); plate(r,"front_corner_r",30,-23,-32,5,25,20); seam(r,"brow_seam",-2,-27,-39,4,10,2); seam(r,"corner_l_seam",-37,-15,-25,2,13,6); seam(r,"corner_r_seam",35,-15,-25,2,13,6);
        sideArmor(r,true); sideArmor(r,false); rearArmor(r);
        for(int x=-27;x<=27;x+=9){rivet(r,"brow_r"+x,x,-24,-40); rivet(r,"crown_r"+x,x,-35,-30);} for(int y=-18;y<=8;y+=9){rivet(r,"fl_r"+y,-33,y,-38); rivet(r,"fr_r"+y,33,y,-38);}
        box(r,"keystone",132,118,-7,-19,-39,14,12,4); box(r,"center_spine",152,118,-3,-8,-40,6,22,4); box(r,"chevron_l",164,118,-12,8,-40,9,5,4); box(r,"chevron_r",178,118,3,8,-40,9,5,4); box(r,"center_tip",192,118,-3,13,-40,6,10,4); brace(r,"brace_l",-25); brace(r,"brace_r",20);
        box(r,"deck",0,144,-29,-38,-27,58,4,54); box(r,"deck_inset",64,144,-24,-42,-22,48,4,44); rim(r,"rim_front",-30,-46,-29,60,4,4); rim(r,"rim_back",-30,-46,25,60,4,4); rim(r,"rim_l",-33,-46,-25,4,4,50); rim(r,"rim_r",29,-46,-25,4,4,50);
        rail(r,"rail_front",-27,-53,-28,54,2,2); rail(r,"rail_back",-27,-53,26,54,2,2); rail(r,"rail_l",-29,-53,-25,2,2,50); rail(r,"rail_r",27,-53,-25,2,2,50);
        for(int x:new int[]{-27,-9,9,25}){post(r,"front_post"+x,x,-57,-27);post(r,"back_post"+x,x,-57,25);} for(int z:new int[]{-18,0,18}){post(r,"left_post"+z,-28,-57,z);post(r,"right_post"+z,26,-57,z);}
        tower(r,"tower_fl",-22,-66,-21); tower(r,"tower_fr",18,-66,-21); tower(r,"tower_bl",-22,-64,17); tower(r,"tower_br",18,-64,17); tower(r,"tower_command",-3,-73,-2);
        box(r,"command_base",96,174,-8,-48,-8,16,5,16); box(r,"machinery_l",118,174,-19,-48,-5,9,5,13); box(r,"machinery_r",140,174,10,-48,-5,9,5,13); box(r,"antenna_a",160,174,-12,-73,11,1,18,1); box(r,"antenna_b",164,174,11,-70,8,1,15,1); box(r,"antenna_c",168,174,5,-68,18,1,12,1);
        lamp(r,"lamp_fl",-32,-29,-40); lamp(r,"lamp_fr",26,-29,-40); lamp(r,"lamp_l",36,-26,-9); lamp(r,"lamp_r",-42,-26,-9);
        box(r,"banner_mast",0,210,-27,-86,17,3,49,3); box(r,"banner_finial",8,210,-29,-90,15,7,5,5); box(r,"banner_crossbar",20,210,-25,-85,17,27,3,3); box(r,"banner_top",54,210,-22,-81,18,19,20,1); box(r,"banner_mid",78,210,-20,-61,18,15,14,1); box(r,"banner_low",98,210,-17,-47,18,9,10,1); box(r,"banner_point",112,210,-13,-37,18,2,9,1); box(r,"banner_frame_l",120,210,-24,-82,16.8F,2,39,2); box(r,"banner_frame_r",126,210,-3,-82,16.8F,2,39,2); box(r,"skull",134,210,-18,-72,15.8F,11,10,2); box(r,"skull_jaw",150,210,-15,-62,15.5F,5,6,2); box(r,"horn_l",160,210,-22,-75,15.5F,5,9,2); box(r,"horn_r",170,210,-5,-75,15.5F,5,9,2); box(r,"crossbone_l",180,210,-20,-57,15.5F,6,3,2); box(r,"crossbone_r",190,210,-7,-57,15.5F,6,3,2);
        return LayerDefinition.create(mesh,512,256);
    }
    private static void sideArmor(PartDefinition r,boolean left){float x=left?32:-39;String s=left?"l":"r";box(r,s+"_rail",0,130,x,-25,-27,7,17,54);box(r,s+"_frontpod",20,130,left?36:-44,-8,-25,8,20,18);box(r,s+"_midpod",46,130,left?37:-45,-5,-3,8,18,17);box(r,s+"_rearpod",70,130,left?36:-44,-8,18,8,20,13);box(r,s+"_pod_cap_a",94,130,left?39:-47,-5,-22,4,13,12);box(r,s+"_pod_cap_b",110,130,left?40:-48,-2,0,4,12,11);}
    private static void rearArmor(PartDefinition r){box(r,"rear_band",0,118,-30,-25,32,60,18,5);plate(r,"rear_l",-30,-5,35,25,18,3);plate(r,"rear_r",5,-5,35,25,18,3);seam(r,"rear_center",-2,-24,36,4,37,2);}
    private static void tentacle(PartDefinition r,String n,float x,float y,float z,int u,int v){box(r,n,u,v,x,y,z,8,22,8);}
    private static void plate(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,202,118,x,y,z,w,h,d);} private static void seam(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,224,118,x,y,z,w,h,d);} private static void rivet(PartDefinition r,String n,float x,float y,float z){box(r,n,244,118,x,y,z,2,2,2);}
    private static void brace(PartDefinition r,String n,float x){box(r,n,4,166,x,4,-38,5,27,5);box(r,n+"_foot",14,166,x,27,-38,12,5,5);rivet(r,n+"_r1",x+1,8,-40);rivet(r,n+"_r2",x+1,20,-40);} private static void rim(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,118,144,x,y,z,w,h,d);} private static void rail(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,136,156,x,y,z,w,h,d);}
    private static void post(PartDefinition r,String n,float x,float y,float z){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(150,164).addBox(x,y,z,2,8,2).texOffs(155,164).addBox(x-.5F,y-2,z-.5F,3,2,3),PartPose.ZERO);} private static void tower(PartDefinition r,String n,float x,float y,float z){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(0,174).addBox(x,y,z,4,13,4).texOffs(8,174).addBox(x-1,y-4,z-1,6,4,6).texOffs(8,184).addBox(x,y-7,z,4,3,4),PartPose.ZERO);} private static void lamp(PartDefinition r,String n,float x,float y,float z){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(28,174).addBox(x,y,z,6,7,4).texOffs(28,182).addBox(x+1,y+1,z-1,4,5,2).texOffs(40,174).addBox(x+2,y-2,z,2,2,3),PartPose.ZERO);}
    private static void box(PartDefinition r,String n,int u,int v,float x,float y,float z,float w,float h,float d){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,w,h,d,new CubeDeformation(0)),PartPose.ZERO);}
}
