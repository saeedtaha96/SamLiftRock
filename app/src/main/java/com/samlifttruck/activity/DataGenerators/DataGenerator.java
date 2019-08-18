package com.samlifttruck.activity.DataGenerators;

import com.samlifttruck.activity.Models.DraftListModel;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<DraftListModel> getDraftList() {
        List<DraftListModel> list = new ArrayList<>();
        String draftNum, permNum, custName, date;
        String mDate = "1";
        for (int i = 0; i < 15; i++) {
           double a = (5 + (Math.random() * 30));
            draftNum = String.valueOf(20 * a);
            permNum = String.valueOf(12 * a);
            if (a < 10) {
                mDate = "0" + (int) a;
            }
            DraftListModel draftModel = new DraftListModel(draftNum, permNum, "saeed" + i, "98/08/" +mDate);
            list.add(draftModel);
        }
        return list;
    }
}
