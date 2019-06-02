.class public Lcom/android/volley/toolbox/AndroidAuthenticator;
.super Ljava/lang/Object;
.source "AndroidAuthenticator.java"

# interfaces
.implements Lcom/android/volley/toolbox/Authenticator;


# instance fields
.field private final mAccount:Landroid/accounts/Account;

.field private final mAuthTokenType:Ljava/lang/String;

.field private final mContext:Landroid/content/Context;

.field private final mNotifyAuthFailure:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/accounts/Account;Ljava/lang/String;)V
    .registers 5

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, p3, v0}, Lcom/android/volley/toolbox/AndroidAuthenticator;-><init>(Landroid/content/Context;Landroid/accounts/Account;Ljava/lang/String;Z)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/accounts/Account;Ljava/lang/String;Z)V
    .registers 5

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mContext:Landroid/content/Context;

    iput-object p2, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mAccount:Landroid/accounts/Account;

    iput-object p3, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mAuthTokenType:Ljava/lang/String;

    iput-boolean p4, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mNotifyAuthFailure:Z

    return-void
.end method


# virtual methods
.method public getAccount()Landroid/accounts/Account;
    .registers 2

    iget-object v0, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mAccount:Landroid/accounts/Account;

    return-object v0
.end method

.method public getAuthToken()Ljava/lang/String;
    .registers 8
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/android/volley/AuthFailureError;
        }
    .end annotation

    iget-object v0, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mContext:Landroid/content/Context;

    invoke-static {v0}, Landroid/accounts/AccountManager;->get(Landroid/content/Context;)Landroid/accounts/AccountManager;

    move-result-object v0

    iget-object v2, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mAccount:Landroid/accounts/Account;

    iget-object v3, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mAuthTokenType:Ljava/lang/String;

    iget-boolean v4, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mNotifyAuthFailure:Z

    const/4 v5, 0x0

    const/4 v6, 0x0

    move-object v1, v0

    invoke-virtual/range {v1 .. v6}, Landroid/accounts/AccountManager;->getAuthToken(Landroid/accounts/Account;Ljava/lang/String;ZLandroid/accounts/AccountManagerCallback;Landroid/os/Handler;)Landroid/accounts/AccountManagerFuture;

    move-result-object v1

    :try_start_13
    invoke-interface {v1}, Landroid/accounts/AccountManagerFuture;->getResult()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/os/Bundle;
    :try_end_19
    .catch Ljava/lang/Exception; {:try_start_13 .. :try_end_19} :catch_60

    nop

    const/4 v3, 0x0

    invoke-interface {v1}, Landroid/accounts/AccountManagerFuture;->isDone()Z

    move-result v4

    if-eqz v4, :cond_44

    invoke-interface {v1}, Landroid/accounts/AccountManagerFuture;->isCancelled()Z

    move-result v4

    if-nez v4, :cond_44

    const-string v4, "intent"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->containsKey(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_36

    const-string v4, "authtoken"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    goto :goto_44

    :cond_36
    const-string v4, "intent"

    invoke-virtual {v2, v4}, Landroid/os/Bundle;->getParcelable(Ljava/lang/String;)Landroid/os/Parcelable;

    move-result-object v4

    check-cast v4, Landroid/content/Intent;

    new-instance v5, Lcom/android/volley/AuthFailureError;

    invoke-direct {v5, v4}, Lcom/android/volley/AuthFailureError;-><init>(Landroid/content/Intent;)V

    throw v5

    :cond_44
    :goto_44
    if-eqz v3, :cond_47

    return-object v3

    :cond_47
    new-instance v4, Lcom/android/volley/AuthFailureError;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Got null auth token for type: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mAuthTokenType:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-direct {v4, v5}, Lcom/android/volley/AuthFailureError;-><init>(Ljava/lang/String;)V

    throw v4

    :catch_60
    move-exception v2

    new-instance v3, Lcom/android/volley/AuthFailureError;

    const-string v4, "Error while retrieving auth token"

    invoke-direct {v3, v4, v2}, Lcom/android/volley/AuthFailureError;-><init>(Ljava/lang/String;Ljava/lang/Exception;)V

    throw v3
.end method

.method public invalidateAuthToken(Ljava/lang/String;)V
    .registers 4

    iget-object v0, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mContext:Landroid/content/Context;

    invoke-static {v0}, Landroid/accounts/AccountManager;->get(Landroid/content/Context;)Landroid/accounts/AccountManager;

    move-result-object v0

    iget-object v1, p0, Lcom/android/volley/toolbox/AndroidAuthenticator;->mAccount:Landroid/accounts/Account;

    iget-object v1, v1, Landroid/accounts/Account;->type:Ljava/lang/String;

    invoke-virtual {v0, v1, p1}, Landroid/accounts/AccountManager;->invalidateAuthToken(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method
