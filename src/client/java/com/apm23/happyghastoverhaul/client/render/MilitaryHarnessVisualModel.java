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

/** Geometry traced from the locked Sentinel/Reaper multi-view reference. */
public final class MilitaryHarnessVisualModel extends EntityModel<HappyGhastRenderState> {
    public MilitaryHarnessVisualModel(ModelPart root) { super(root); }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        // Adult Happy Ghast body envelope: approximately X/Z +/-32.  The reference is a
        // heavy upper-half exoskeleton, not a full rectangular shell.  All front armor is
        // deliberately beyond Z=-32 so the white face/body remains visible underneath.

        // Three-tier black armored brow/crown seen in the front/left/right reference views.
        box(r,"upper_brow",0,0,-29,-31,-38,58,7,5);
        box(r,"upper_brow_cap",0,12,-25,-36,-36.5F,50,5,4);
        box(r,"front_band_left",0,22,-31,-23,-39,24,7,5);
        box(r,"front_band_right",30,22,7,-23,-39,24,7,5);
        box(r,"front_band_center",60,22,-7,-25,-40,14,6,5);
        box(r,"temple_left",82,22,-34,-21,-35,7,17,8);
        box(r,"temple_right",98,22,27,-21,-35,7,17,8);

        // Layered side/back plate stacks.  Smaller overlapping boxes reproduce the riveted,
        // mechanical reference silhouette instead of one giant smooth cuboid.
        box(r,"left_upper",0,36,33,-30,-28,6,15,54);
        box(r,"left_mid_front",16,36,36,-15,-27,8,18,20);
        box(r,"left_mid_rear",40,36,36,-15,5,8,18,20);
        box(r,"left_lower_front",64,36,34,3,-24,7,14,16);
        box(r,"left_lower_rear",84,36,34,3,8,7,14,16);
        box(r,"right_upper",0,58,-39,-30,-28,6,15,54);
        box(r,"right_mid_front",16,58,-44,-15,-27,8,18,20);
        box(r,"right_mid_rear",40,58,-44,-15,5,8,18,20);
        box(r,"right_lower_front",64,58,-41,3,-24,7,14,16);
        box(r,"right_lower_rear",84,58,-41,3,8,7,14,16);
        box(r,"back_upper",0,80,-29,-30,33,58,15,6);
        box(r,"back_left_module",64,80,-29,-15,36,20,19,8);
        box(r,"back_right_module",92,80,9,-15,36,20,19,8);

        // Reference front: open white lower face, purple eyes, central faction-colored arrow
        // and four hanging black/metal braces.  No armor plate is allowed over the eye area.
        box(r,"forehead_keystone",0,104,-7,-19,-42,14,13,5);
        box(r,"arrow_shaft",20,104,-2.5F,-7,-43,5,19,4);
        box(r,"arrow_left_wing",30,104,-9,6,-43,7,5,4);
        box(r,"arrow_right_wing",40,104,2,6,-43,7,5,4);
        box(r,"arrow_tip",50,104,-2.5F,12,-43,5,8,4);
        box(r,"brace_outer_left",60,104,-28,7,-40,5,25,5);
        box(r,"brace_inner_left",70,104,-17,13,-40,4,23,5);
        box(r,"brace_inner_right",80,104,13,13,-40,4,23,5);
        box(r,"brace_outer_right",90,104,23,7,-40,5,25,5);
        box(r,"brace_left_foot",100,104,-28,28,-40,11,5,5);
        box(r,"brace_right_foot",114,104,17,28,-40,11,5,5);

        // Side equipment boxes from the reference, stepped and visibly detached from body skin.
        box(r,"pod_left_a",0,116,39,-17,-21,8,16,16);
        box(r,"pod_left_b",20,116,40,2,-18,7,12,13);
        box(r,"pod_left_c",38,116,39,-13,6,8,18,18);
        box(r,"pod_right_a",60,116,-47,-17,-21,8,16,16);
        box(r,"pod_right_b",80,116,-47,2,-18,7,12,13);
        box(r,"pod_right_c",98,116,-47,-13,6,8,18,18);

        // Armored rooftop from the reference: inset deck, double rim and dense railing.
        box(r,"deck",0,128,-27,-39,-25,54,3,50);
        box(r,"deck_front_rim",60,128,-29,-42,-27,58,4,4);
        box(r,"deck_back_rim",60,136,-29,-42,23,58,4,4);
        box(r,"deck_left_rim",0,140,25,-42,-23,4,4,46);
        box(r,"deck_right_rim",10,140,-29,-42,-23,4,4,46);
        box(r,"deck_center_spine",20,140,-4,-43,-19,8,3,38);
        rail(r,"rail_front",-27,-48,-27,54,2,2,34,140);
        rail(r,"rail_back",-27,-48,25,54,2,2,34,144);
        rail(r,"rail_left",25,-48,-25,2,2,50,34,148);
        rail(r,"rail_right",-27,-48,-25,2,2,50,34,152);
        post(r,"post_fl",23,-56,-23,0,148); post(r,"post_fr",-25,-56,-23,8,148);
        post(r,"post_bl",23,-56,21,16,148); post(r,"post_br",-25,-56,21,24,148);

        // Multiple illuminated rooftop towers are a major visual signature of the concept.
        tower(r,"tower_front_left",-20,-63,-20,32,148);
        tower(r,"tower_front_right",17,-63,-20,44,148);
        tower(r,"tower_back_left",-20,-61,17,56,148);
        tower(r,"tower_back_right",17,-61,17,68,148);
        tower(r,"tower_center",-2,-68,-3,80,148);
        box(r,"antenna_left",94,148,-11,-66,11,1,14,1);
        box(r,"antenna_right",98,148,10,-64,8,1,12,1);
        box(r,"antenna_rear",102,148,3,-62,19,1,10,1);

        // Side/front lamps: real protruding cubes, faction texture provides blue/red glow.
        lamp(r,"lamp_front_left",-31,-27,-42,108,148);
        lamp(r,"lamp_front_right",25,-27,-42,116,148);
        lamp(r,"lamp_side_left",40,-24,-6,124,148);
        lamp(r,"lamp_side_right",-44,-24,-6,132,148);

        // Tall rear-left war banner from the supplied in-game banner reference.  Cloth is
        // segmented to form a long pointed pennant rather than the previous rectangular flag.
        box(r,"banner_mast",0,160,-26,-78,18,2,39,2);
        box(r,"banner_finial",6,160,-27,-81,17,4,4,4);
        box(r,"banner_crossbar",14,160,-25,-77,18,23,2,2);
        box(r,"banner_cloth_top",42,160,-22,-74,18.5F,18,20,1);
        box(r,"banner_cloth_mid",64,160,-20,-54,18.5F,14,12,1);
        box(r,"banner_cloth_low",82,160,-17,-42,18.5F,8,8,1);
        box(r,"banner_point",94,160,-14,-34,18.5F,2,7,1);
        box(r,"banner_left_frame",100,160,-23,-74,17.8F,2,34,2);
        box(r,"banner_right_frame",106,160,-4,-74,17.8F,2,34,2);
        // Layered skull/badge volume; texture carries the actual faction skull artwork.
        box(r,"banner_badge",112,160,-18,-66,17.2F,10,10,1.5F);
        box(r,"banner_badge_face",126,160,-16,-64,16.5F,6,6,1.5F);

        return LayerDefinition.create(mesh,192,192);
    }

    private static void box(PartDefinition r,String n,int u,int v,float x,float y,float z,float w,float h,float d){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,w,h,d,new CubeDeformation(0)),PartPose.ZERO);
    }
    private static void rail(PartDefinition r,String n,float x,float y,float z,float w,float h,float d,int u,int v){box(r,n,u,v,x,y,z,w,h,d);}
    private static void post(PartDefinition r,String n,float x,float y,float z,int u,int v){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,2,8,2).texOffs(u+4,v).addBox(x-.5F,y-2,z-.5F,3,2,3),PartPose.ZERO);
    }
    private static void tower(PartDefinition r,String n,float x,float y,float z,int u,int v){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,3,12,3).texOffs(u+4,v).addBox(x-1,y-4,z-1,5,4,5),PartPose.ZERO);
    }
    private static void lamp(PartDefinition r,String n,float x,float y,float z,int u,int v){
        r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,6,7,4).texOffs(u,v+8).addBox(x+1,y+1,z-1,4,5,2),PartPose.ZERO);
    }
}
