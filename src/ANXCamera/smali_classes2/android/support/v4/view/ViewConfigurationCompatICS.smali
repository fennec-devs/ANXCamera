.class Landroid/support/v4/view/ViewConfigurationCompatICS;
.super Ljava/lang/Object;
.source "ViewConfigurationCompatICS.java"


# direct methods
.method constructor <init>()V
    .registers 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static hasPermanentMenuKey(Landroid/view/ViewConfiguration;)Z
    .registers 2

    invoke-virtual {p0}, Landroid/view/ViewConfiguration;->hasPermanentMenuKey()Z

    move-result v0

    return v0
.end method
