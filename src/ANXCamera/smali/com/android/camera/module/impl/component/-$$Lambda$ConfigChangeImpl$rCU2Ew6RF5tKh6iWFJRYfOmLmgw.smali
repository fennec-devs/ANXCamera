.class public final synthetic Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$rCU2Ew6RF5tKh6iWFJRYfOmLmgw;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field private final synthetic f$0:Z


# direct methods
.method public synthetic constructor <init>(Z)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-boolean p1, p0, Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$rCU2Ew6RF5tKh6iWFJRYfOmLmgw;->f$0:Z

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 1

    iget-boolean v0, p0, Lcom/android/camera/module/impl/component/-$$Lambda$ConfigChangeImpl$rCU2Ew6RF5tKh6iWFJRYfOmLmgw;->f$0:Z

    check-cast p1, Lcom/android/camera/module/BaseModule;

    invoke-static {v0, p1}, Lcom/android/camera/module/impl/component/ConfigChangeImpl;->lambda$configMoon$2(ZLcom/android/camera/module/BaseModule;)V

    return-void
.end method
