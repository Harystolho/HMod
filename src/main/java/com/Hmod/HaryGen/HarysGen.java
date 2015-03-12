package com.Hmod.HaryGen;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HarysGen {
	 
    public static void MainRegistry(){
           
            registerWorldGen(new HaryOreGen(),1);
           
    }
   
    public static void registerWorldGen(IWorldGenerator iGenertor, int weightedProbability){
            GameRegistry.registerWorldGenerator(iGenertor, weightedProbability);
    }
}