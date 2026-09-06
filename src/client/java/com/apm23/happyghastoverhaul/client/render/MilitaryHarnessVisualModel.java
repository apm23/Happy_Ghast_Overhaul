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
 public MilitaryHarnessVisualModel(ModelPart root){super(root);}
 public static LayerDefinition createLayer(){MeshDefinition m=new MeshDefinition();PartDefinition r=m.getRoot();
  box(r,"body",256,0,-32,-32,-32,64,64,64);int i=0;for(int z:new int[]{-24,-4,16})for(int x:new int[]{-24,-4,16})tent(r,"tentacle_"+(i++),x,32,z,256+(i%6)*16,130+(i/6)*28);
  box(r,"crown",0,96,-32,-34,-34,64,7,68);box(r,"brow",72,96,-30,-27,-36,60,10,5);
  plate(r,"ful",-30,-25,-37,27,7,3);plate(r,"fur",3,-25,-37,27,7,3);plate(r,"fml",-31,-16,-37,15,10,3);plate(r,"fmr",16,-16,-37,15,10,3);plate(r,"fcl",-35,-23,-32,5,25,20);plate(r,"fcr",30,-23,-32,5,25,20);
  // Reference trim hierarchy: silver corner clamps over dark layered plates.
  trim(r,"clamp_l",-36,-29,-35,7,15,4);trim(r,"clamp_r",29,-29,-35,7,15,4);trim(r,"clamp_l2",-36,-10,-35,7,10,4);trim(r,"clamp_r2",29,-10,-35,7,10,4);
  seam(r,"brow_seam",-2,-27,-39,4,10,2);side(r,true);side(r,false);rear(r);
  for(int x=-27;x<=27;x+=9){rivet(r,"br"+x,x,-24,-40);rivet(r,"cr"+x,x,-35,-30);}for(int y=-18;y<=8;y+=9){rivet(r,"lr"+y,-33,y,-38);rivet(r,"rr"+y,33,y,-38);}
  // Long faction face ornaments seen clearly in the reference front views.
  box(r,"keystone",132,118,-7,-19,-39,14,12,4);box(r,"spine",152,118,-3,-8,-40,6,22,4);box(r,"wing_l",164,118,-12,8,-40,9,5,4);box(r,"wing_r",178,118,3,8,-40,9,5,4);box(r,"tip",192,118,-3,13,-40,6,10,4);brace(r,"brace_l",-25);brace(r,"brace_r",20);
  // Extra narrow hanging armor straps keep the lower white body exposed like the sheet.
  box(r,"strap_inner_l",196,144,-14,2,-37,4,25,4);box(r,"strap_inner_r",204,144,10,2,-37,4,25,4);
  box(r,"deck",0,144,-29,-38,-27,58,4,54);box(r,"deck2",64,144,-24,-42,-22,48,4,44);rim(r,"rf",-30,-46,-29,60,4,4);rim(r,"rb",-30,-46,25,60,4,4);rim(r,"rl",-33,-46,-25,4,4,50);rim(r,"rr",29,-46,-25,4,4,50);
  rail(r,"xaf",-27,-53,-28,54,2,2);rail(r,"xab",-27,-53,26,54,2,2);rail(r,"xal",-29,-53,-25,2,2,50);rail(r,"xar",27,-53,-25,2,2,50);for(int x:new int[]{-27,-9,9,25}){post(r,"pf"+x,x,-57,-27);post(r,"pb"+x,x,-57,25);}for(int z:new int[]{-18,0,18}){post(r,"pl"+z,-28,-57,z);post(r,"pr"+z,26,-57,z);}
  tower(r,"tfl",-22,-66,-21);tower(r,"tfr",18,-66,-21);tower(r,"tbl",-22,-64,17);tower(r,"tbr",18,-64,17);tower(r,"tc",-3,-73,-2);box(r,"cmd",96,174,-8,-48,-8,16,5,16);box(r,"ml",118,174,-19,-48,-5,9,5,13);box(r,"mr",140,174,10,-48,-5,9,5,13);box(r,"aa",160,174,-12,-73,11,1,18,1);box(r,"ab",164,174,11,-70,8,1,15,1);box(r,"ac",168,174,5,-68,18,1,12,1);lamp(r,"lfl",-32,-29,-40);lamp(r,"lfr",26,-29,-40);lamp(r,"ll",36,-26,-9);lamp(r,"lr",-42,-26,-9);
  // Taller, broader V-bottom banner with framed skull/horn assembly.
  box(r,"mast",0,210,-27,-88,17,3,51,3);box(r,"finial",8,210,-29,-92,15,7,5,5);box(r,"bar",20,210,-25,-87,17,29,3,3);box(r,"bt",54,210,-22,-83,18,21,22,1);box(r,"bm",78,210,-20,-61,18,17,15,1);box(r,"bl",98,210,-17,-46,18,11,11,1);box(r,"bp",112,210,-12,-35,18,3,9,1);box(r,"bfl",120,210,-24,-84,16.8F,2,43,2);box(r,"bfr",126,210,-1,-84,16.8F,2,43,2);box(r,"skull",134,210,-18,-73,15.8F,11,10,2);box(r,"jaw",150,210,-15,-63,15.5F,5,6,2);box(r,"hl",160,210,-22,-77,15.5F,5,11,2);box(r,"hr",170,210,-5,-77,15.5F,5,11,2);box(r,"xl",180,210,-20,-58,15.5F,6,3,2);box(r,"xr",190,210,-7,-58,15.5F,6,3,2);
  return LayerDefinition.create(m,512,256);}
 private static void side(PartDefinition r,boolean l){float x=l?32:-39;String s=l?"l":"r";box(r,s+"rail",0,130,x,-25,-27,7,17,54);box(r,s+"fp",20,130,l?36:-44,-8,-25,8,20,18);box(r,s+"mp",46,130,l?37:-45,-5,-3,8,18,17);box(r,s+"rp",70,130,l?36:-44,-8,18,8,20,13);box(r,s+"ca",94,130,l?39:-47,-5,-22,4,13,12);box(r,s+"cb",110,130,l?40:-48,-2,0,4,12,11);trim(r,s+"edge",l?43:-49,-19,-27,3,25,54);}
 private static void rear(PartDefinition r){box(r,"rear",0,118,-30,-25,32,60,18,5);plate(r,"rearl",-30,-5,35,25,18,3);plate(r,"rearr",5,-5,35,25,18,3);seam(r,"rearc",-2,-24,36,4,37,2);}
 private static void tent(PartDefinition r,String n,float x,float y,float z,int u,int v){box(r,n,u,v,x,y,z,8,22,8);}private static void plate(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,202,118,x,y,z,w,h,d);}private static void trim(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,232,144,x,y,z,w,h,d);}private static void seam(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,224,118,x,y,z,w,h,d);}private static void rivet(PartDefinition r,String n,float x,float y,float z){box(r,n,244,118,x,y,z,2,2,2);}
 private static void brace(PartDefinition r,String n,float x){box(r,n,4,166,x,4,-38,5,27,5);box(r,n+"f",14,166,x,27,-38,12,5,5);rivet(r,n+"1",x+1,8,-40);rivet(r,n+"2",x+1,20,-40);}private static void rim(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,118,144,x,y,z,w,h,d);}private static void rail(PartDefinition r,String n,float x,float y,float z,float w,float h,float d){box(r,n,136,156,x,y,z,w,h,d);}private static void post(PartDefinition r,String n,float x,float y,float z){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(150,164).addBox(x,y,z,2,8,2).texOffs(155,164).addBox(x-.5F,y-2,z-.5F,3,2,3),PartPose.ZERO);}private static void tower(PartDefinition r,String n,float x,float y,float z){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(0,174).addBox(x,y,z,4,13,4).texOffs(8,174).addBox(x-1,y-4,z-1,6,4,6).texOffs(8,184).addBox(x,y-7,z,4,3,4),PartPose.ZERO);}private static void lamp(PartDefinition r,String n,float x,float y,float z){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(28,174).addBox(x,y,z,6,7,4).texOffs(28,182).addBox(x+1,y+1,z-1,4,5,2).texOffs(40,174).addBox(x+2,y-2,z,2,2,3),PartPose.ZERO);}private static void box(PartDefinition r,String n,int u,int v,float x,float y,float z,float w,float h,float d){r.addOrReplaceChild(n,CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,w,h,d,new CubeDeformation(0)),PartPose.ZERO);}
}
