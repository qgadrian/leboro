package com.leboro.util.list;

import java.util.List;

import com.google.api.client.util.Lists;

public class ListUtils {

    public static <T> List<T> getJoinedLists(List<T>... lists) {
        List<T> joinedLists = Lists.newArrayList();

        for (List<T> list : lists) {
            joinedLists.addAll(list);
        }

        return joinedLists;
    }

}
