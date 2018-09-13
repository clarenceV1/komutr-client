package com.komutr.client.dao;

import com.cai.framework.dagger.ActivityScope;
import com.komutr.client.ui.FrequentlyStation;
import com.komutr.client.ui.FrequentlyStation_;


import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;

@ActivityScope
public class FrequentlyStationDao extends BaseDAO {

    Box<FrequentlyStation> msgBox;

    @Inject
    public FrequentlyStationDao() {
        msgBox = boxStore.boxFor(FrequentlyStation.class);
    }

    /**
     * @param offset 从第几条取
     * @param limit  取出条数
     * @return
     */
    public List<FrequentlyStation> getFrequentlyStationList(long offset, long limit) {
        return msgBox.query().build().find(offset, limit);
    }

    public void deleteAll() {
        msgBox.removeAll();
    }

    public void add(FrequentlyStation position) {
        if (position != null) {
            msgBox.put(position);
        }
    }



    public void delete(int id){
        FrequentlyStation frequentlyStation = getFrequentlyStationById(id);
        if(frequentlyStation != null){
            msgBox.remove(frequentlyStation);
        }
    }




    public List<FrequentlyStation> updateAll(List<FrequentlyStation> positionList,long offset, long limit) {
        if (positionList != null && positionList.size() > 0) {
            msgBox.removeAll();
            msgBox.put(positionList);
            return positionList;
        }
        return getFrequentlyStationList(offset,limit);

    }

    /**
     * 从数据库获取十条数据
     * @param id
     * @return
     */
    public FrequentlyStation getFrequentlyStationById(int id) {
        return msgBox.query().equal(FrequentlyStation_.id, id).build().findFirst();
    }
}
