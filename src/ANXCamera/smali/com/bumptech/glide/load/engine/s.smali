.class Lcom/bumptech/glide/load/engine/s;
.super Ljava/lang/Object;
.source "ResourceRecycler.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/bumptech/glide/load/engine/s$a;
    }
.end annotation


# instance fields
.field private hY:Z

.field private final handler:Landroid/os/Handler;


# direct methods
.method constructor <init>()V
    .locals 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    new-instance v2, Lcom/bumptech/glide/load/engine/s$a;

    invoke-direct {v2}, Lcom/bumptech/glide/load/engine/s$a;-><init>()V

    invoke-direct {v0, v1, v2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;Landroid/os/Handler$Callback;)V

    iput-object v0, p0, Lcom/bumptech/glide/load/engine/s;->handler:Landroid/os/Handler;

    return-void
.end method


# virtual methods
.method h(Lcom/bumptech/glide/load/engine/p;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/bumptech/glide/load/engine/p<",
            "*>;)V"
        }
    .end annotation

    invoke-static {}, Lcom/bumptech/glide/util/k;->fo()V

    iget-boolean v0, p0, Lcom/bumptech/glide/load/engine/s;->hY:Z

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/bumptech/glide/load/engine/s;->handler:Landroid/os/Handler;

    invoke-virtual {v0, v1, p1}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    invoke-virtual {p1}, Landroid/os/Message;->sendToTarget()V

    goto :goto_0

    :cond_0
    iput-boolean v1, p0, Lcom/bumptech/glide/load/engine/s;->hY:Z

    invoke-interface {p1}, Lcom/bumptech/glide/load/engine/p;->recycle()V

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/bumptech/glide/load/engine/s;->hY:Z

    :goto_0
    return-void
.end method
