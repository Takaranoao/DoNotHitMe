package com.takaranoao.donothitme.mixin;

import com.takaranoao.donothitme.DoNotHitMe;
import com.takaranoao.donothitme.data.MyPredicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(ProjectileUtil.class)
@Environment(EnvType.CLIENT)
public class MixinProjectileUtil {
    @Inject(method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;", at = @At("HEAD"), cancellable = true)
    private static void getEntityHitResult(Entity entity, Vec3 vec3, Vec3 vec31, AABB aabb, Predicate<Entity> predicate, double d, CallbackInfoReturnable<EntityHitResult> cir) {
        if (predicate instanceof MyPredicate) {
            return;
        }
        var mod = DoNotHitMe.getInstance();
        if (mod == null) {
            return;
        }
        MyPredicate myPredicate = entity1 -> {
            if (!mod.filterTargetEntity(entity1)) {
                return false;
            }
            return predicate.test(entity1);
        };
        cir.setReturnValue(ProjectileUtil.getEntityHitResult(entity, vec3, vec31, aabb, myPredicate, d));
    }
}
