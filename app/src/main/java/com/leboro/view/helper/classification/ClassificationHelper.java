package com.leboro.view.helper.classification;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.leboro.model.classification.Position;
import com.leboro.service.ApplicationServiceProvider;
import com.leboro.util.Constants;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ClassificationHelper {

    public static void addPositionsDataArguments(Fragment fragment) {
        ArrayList<Position> positions = Lists
                .newArrayList(ApplicationServiceProvider.getStatisticsService().getClassification());
        Bundle bundle = new Bundle(1);
        bundle.putParcelableArrayList(Constants.BUNDLE_ARG_POSITIONS, positions);
        fragment.setArguments(bundle);
    }

}
