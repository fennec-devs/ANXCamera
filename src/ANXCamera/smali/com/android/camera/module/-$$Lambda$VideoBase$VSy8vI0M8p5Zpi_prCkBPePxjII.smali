.class public final synthetic Lcom/android/camera/module/-$$Lambda$VideoBase$VSy8vI0M8p5Zpi_prCkBPePxjII;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/module/VideoBase;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/module/VideoBase;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/-$$Lambda$VideoBase$VSy8vI0M8p5Zpi_prCkBPePxjII;->f$0:Lcom/android/camera/module/VideoBase;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/-$$Lambda$VideoBase$VSy8vI0M8p5Zpi_prCkBPePxjII;->f$0:Lcom/android/camera/module/VideoBase;

    invoke-static {v0}, Lcom/android/camera/module/VideoBase;->lambda$setOrientationParameter$0(Lcom/android/camera/module/VideoBase;)V

    return-void
.end method
