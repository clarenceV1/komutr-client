package com.komutr.client.ui.nickname;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;

public interface NicknameView extends GodBaseView {


    void updateMyData(RespondDO respondDO);

    void checkUsername(RespondDO respondDO);
}
