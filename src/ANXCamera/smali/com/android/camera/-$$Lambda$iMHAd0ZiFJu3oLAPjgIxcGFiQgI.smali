.class public final synthetic Lcom/android/camera/-$$Lambda$iMHAd0ZiFJu3oLAPjgIxcGFiQgI;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/Camera;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/Camera;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/-$$Lambda$iMHAd0ZiFJu3oLAPjgIxcGFiQgI;->f$0:Lcom/android/camera/Camera;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/-$$Lambda$iMHAd0ZiFJu3oLAPjgIxcGFiQgI;->f$0:Lcom/android/camera/Camera;

    invoke-virtual {v0}, Lcom/android/camera/Camera;->showGuide()V

    return-void
.end method
