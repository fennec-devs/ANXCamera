.class public final synthetic Lcom/android/camera/module/-$$Lambda$Camera2Module$8$g4AFkT46WP81JdvmCSveR6sewkk;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/camera/protocol/ModeProtocol$TopAlert;


# direct methods
.method public synthetic constructor <init>(Lcom/android/camera/protocol/ModeProtocol$TopAlert;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$8$g4AFkT46WP81JdvmCSveR6sewkk;->f$0:Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/camera/module/-$$Lambda$Camera2Module$8$g4AFkT46WP81JdvmCSveR6sewkk;->f$0:Lcom/android/camera/protocol/ModeProtocol$TopAlert;

    invoke-static {v0}, Lcom/android/camera/module/Camera2Module$8;->lambda$onSubscribe$0(Lcom/android/camera/protocol/ModeProtocol$TopAlert;)V

    return-void
.end method
