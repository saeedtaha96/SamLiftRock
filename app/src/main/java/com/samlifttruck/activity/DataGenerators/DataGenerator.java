package com.samlifttruck.activity.DataGenerators;

import com.samlifttruck.activity.Models.DraftListModel;
import com.samlifttruck.activity.Models.PermListModel;
import com.samlifttruck.activity.Models.ReceiptListModel;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<DraftListModel> getDraftList() {
        List<DraftListModel> list = new ArrayList<>();
        String draftNum, permNum, custName, date;
        String mDate = "1";
        DraftListModel draftModel;
        for (int i = 0; i < 15; i++) {
            double a = (5 + (Math.random() * 30));
            draftNum = String.valueOf(20 * a);
            permNum = String.valueOf(12 * a);
            if (a < 10) {
                mDate = "0" + (int) a;
            }
            draftModel = new DraftListModel(draftNum, permNum, "saeed" + i, "98/08/" + mDate, "123", "hassan", "con" + i, String.valueOf((int) a * i));
            list.add(draftModel);
        }
        return list;
    }

    public static List<PermListModel> getPermList() {
        List<PermListModel> list = new ArrayList<>();
        String draftNum, permNum, custName, date;
        String mDate = "1";
        PermListModel permModel;
        for (int i = 0; i < 15; i++) {
            double a = (5 + (Math.random() * 30));
            draftNum = String.valueOf(20 * a);
            permNum = String.valueOf(12 * a);
            if (a < 10) {
                mDate = "0" + (int) a;
            }
            permModel = new PermListModel(draftNum, permNum, "saeed" + i, "98/08/" + mDate, "1", "2");
            list.add(permModel);
        }
        return list;
    }

    public static List<ReceiptListModel> getReceiptList() {
        List<ReceiptListModel> list = new ArrayList<>();
        String draftNum, permNum;
        String mDate = "1";
        ReceiptListModel receiptModel;
        for (int i = 0; i < 15; i++) {
            double a = (5 + (Math.random() * 30));
            draftNum = String.valueOf(20 * a);
            permNum = String.valueOf(12 * a);
            if (a < 10) {
                mDate = "0" + (int) a;
            }
            receiptModel = new ReceiptListModel(draftNum, permNum, "saeed" + i, "98/08/" + mDate, "2", "2", "2", "2");
            list.add(receiptModel);
        }
        return list;
    }
}
