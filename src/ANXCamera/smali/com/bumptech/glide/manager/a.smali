.class Lcom/bumptech/glide/manager/a;
.super Ljava/lang/Object;
.source "ActivityFragmentLifecycle.java"

# interfaces
.implements Lcom/bumptech/glide/manager/h;


# instance fields
.field private final nS:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Lcom/bumptech/glide/manager/i;",
            ">;"
        }
    .end annotation
.end field

.field private nT:Z

.field private np:Z


# direct methods
.method constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/util/WeakHashMap;

    invoke-direct {v0}, Ljava/util/WeakHashMap;-><init>()V

    invoke-static {v0}, Ljava/util/Collections;->newSetFromMap(Ljava/util/Map;)Ljava/util/Set;

    move-result-object v0

    iput-object v0, p0, Lcom/bumptech/glide/manager/a;->nS:Ljava/util/Set;

    return-void
.end method


# virtual methods
.method public a(Lcom/bumptech/glide/manager/i;)V
    .locals 1
    .param p1    # Lcom/bumptech/glide/manager/i;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/bumptech/glide/manager/a;->nS:Ljava/util/Set;

    invoke-interface {v0, p1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    iget-boolean v0, p0, Lcom/bumptech/glide/manager/a;->nT:Z

    if-eqz v0, :cond_0

    invoke-interface {p1}, Lcom/bumptech/glide/manager/i;->onDestroy()V

    goto :goto_0

    :cond_0
    iget-boolean v0, p0, Lcom/bumptech/glide/manager/a;->np:Z

    if-eqz v0, :cond_1

    invoke-interface {p1}, Lcom/bumptech/glide/manager/i;->onStart()V

    goto :goto_0

    :cond_1
    invoke-interface {p1}, Lcom/bumptech/glide/manager/i;->onStop()V

    :goto_0
    return-void
.end method

.method public b(Lcom/bumptech/glide/manager/i;)V
    .locals 1
    .param p1    # Lcom/bumptech/glide/manager/i;
        .annotation build Landroid/support/annotation/NonNull;
        .end annotation
    .end param

    iget-object v0, p0, Lcom/bumptech/glide/manager/a;->nS:Ljava/util/Set;

    invoke-interface {v0, p1}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    return-void
.end method

.method onDestroy()V
    .locals 2

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/bumptech/glide/manager/a;->nT:Z

    iget-object v0, p0, Lcom/bumptech/glide/manager/a;->nS:Ljava/util/Set;

    invoke-static {v0}, Lcom/bumptech/glide/util/k;->c(Ljava/util/Collection;)Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/bumptech/glide/manager/i;

    invoke-interface {v1}, Lcom/bumptech/glide/manager/i;->onDestroy()V

    goto :goto_0

    :cond_0
    return-void
.end method

.method onStart()V
    .locals 2

    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/bumptech/glide/manager/a;->np:Z

    iget-object v0, p0, Lcom/bumptech/glide/manager/a;->nS:Ljava/util/Set;

    invoke-static {v0}, Lcom/bumptech/glide/util/k;->c(Ljava/util/Collection;)Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/bumptech/glide/manager/i;

    invoke-interface {v1}, Lcom/bumptech/glide/manager/i;->onStart()V

    goto :goto_0

    :cond_0
    return-void
.end method

.method onStop()V
    .locals 2

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/bumptech/glide/manager/a;->np:Z

    iget-object v0, p0, Lcom/bumptech/glide/manager/a;->nS:Ljava/util/Set;

    invoke-static {v0}, Lcom/bumptech/glide/util/k;->c(Ljava/util/Collection;)Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/bumptech/glide/manager/i;

    invoke-interface {v1}, Lcom/bumptech/glide/manager/i;->onStop()V

    goto :goto_0

    :cond_0
    return-void
.end method
