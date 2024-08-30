package com.caixukun.wholesale_villager.mixin;

import com.caixukun.wholesale_villager.Config;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantOffer.class)
public abstract class MixinVillage {
    @Mutable
    @Shadow @Final private int maxUses;


    @Shadow private int demand;


    @Shadow private int uses;

    @Shadow @Final private float priceMultiplier;

    @Shadow private int specialPriceDiff;

    @Shadow @Final private ItemStack result;
    @Unique int wholesale_villager1$count = -1;

    @Inject(method = "getModifiedCostCount", at = @At("HEAD"), cancellable = true)
    public void cost(ItemCost p_330475_, CallbackInfoReturnable<Integer> cir){
        double b1 = Config.MerchantCostf;
        double b2 = Config.maxMerchantValuef;


        int i = p_330475_.count();
        if (this.uses / (this.maxUses/b2)<=1){
            int j = Math.max(0, Mth.floor((float)(i * this.demand) * this.priceMultiplier));
            int k = Mth.clamp(i - j + this.specialPriceDiff, 1, p_330475_.itemStack().getMaxStackSize());
            cir.setReturnValue(k);
        }else {
            int j = Math.max(0, Mth.floor((float)(i * this.demand) * this.priceMultiplier));
            int fik = 0;
            if(this.specialPriceDiff==0){
                fik = -1;
            }else if(this.specialPriceDiff>0&&this.demand<0) {
                fik = -1*this.specialPriceDiff;
            }else {
                fik = this.specialPriceDiff;
            }
            int k = (int) (i - j + Math.floor((double) fik * Math.max(0.2, 1.0 * this.uses / (this.maxUses/b2)) * b1));
            if(k<=1&&Config.hst){
                int rs = (int) (1.0*this.uses/(1.0*this.maxUses));
                this.result.setCount(Math.min(this.wholesale_villager1$count + rs, this.result.getMaxStackSize()));
            if(k<1) k=1;
            } else if (k>p_330475_.itemStack().getMaxStackSize()) {
                k=p_330475_.itemStack().getMaxStackSize();
                this.result.setCount(this.wholesale_villager1$count);
            }
            cir.setReturnValue(k);
        }

    }
    @Inject(method = "isOutOfStock", at = @At("HEAD"), cancellable = true)
    public void inf(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(!Config.inf);
    }

    @Inject(method = "getResult" , at= @At("HEAD"), cancellable = true)
    public void resultCount(CallbackInfoReturnable<ItemStack> cir){
        if(this.wholesale_villager1$count==-1){
            this.wholesale_villager1$count=this.result.getCount();
        }
        cir.setReturnValue(this.result);
    }

}
