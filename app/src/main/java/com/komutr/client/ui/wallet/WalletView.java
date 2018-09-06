package com.komutr.client.ui.wallet;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Wallet;

public interface WalletView extends GodBaseView {


    void walletCallBack(RespondDO<Wallet> respondDO);
}
