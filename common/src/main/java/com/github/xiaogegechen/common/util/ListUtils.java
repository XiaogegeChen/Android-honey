package com.github.xiaogegechen.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    private ListUtils(){}

    /**
     * 找到只在第一个list中而不在第二个list中的元素，并返回这些元素。这个方法不会更改传入的list。
     *
     * @param first 第一个list
     * @param second 第二个list
     * @param <T> 数据类型
     * @return 只在第一个list中而不在第二个list中的元素
     */
    public static <T extends Comparable<T>> List<T> onlyInFirst(List<T> first, List<T> second){
        List<T> secondCopy = new ArrayList<>(second);
        List<T> result = new ArrayList<>();
        for (int i = 0; i < first.size(); i++) {
            T elementInFirst = first.get(i);
            boolean find = false;
            for (int j = 0; j < secondCopy.size(); j++) {
                if(elementInFirst.compareTo(secondCopy.get(j)) == 0){
                    find = true;
                    secondCopy.remove(j);
                    break;
                }
            }
            if(!find){
                result.add(elementInFirst);
            }
        }
        return result;
    }

    /**
     * 找到只在第一个list中而不在第二个list中的元素，并返回这些元素的位置。这个方法不会更改传入的list。
     *
     * @param first 第一个list
     * @param second 第二个list
     * @param <T> 数据类型
     * @return 只在第一个list中而不在第二个list中的元素
     */
    public static <T extends Comparable<T>> int[] positionsOfOnlyInFirstElement(List<T> first, List<T> second){
        List<T> secondCopy = new ArrayList<>(second);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < first.size(); i++) {
            T elementInFirst = first.get(i);
            boolean find = false;
            for (int j = 0; j < secondCopy.size(); j++) {
                if(elementInFirst.compareTo(secondCopy.get(j)) == 0){
                    find = true;
                    secondCopy.remove(j);
                    break;
                }
            }
            if(!find){
                result.add(i);
            }
        }
        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        return res;
    }

    /**
     * 找到两个list共有的元素，并返回这些元素在第一个list中的位置
     *
     * @param first 第一个list
     * @param second 第二个list
     * @param <T> 数据类型
     * @return 两个list共有的元素在第一个list中的位置
     */
    public static <T extends Comparable<T>> int[] shared(List<T> first, List<T> second){
        List<T> secondCopy = new ArrayList<>(second);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < first.size(); i++) {
            T element = first.get(i);
            boolean find = false;
            for (int j = 0; j < secondCopy.size(); j++) {
                if(element.compareTo(secondCopy.get(j)) == 0){
                    find = true;
                    secondCopy.remove(j);
                    break;
                }
            }
            if(find){
                result.add(i);
            }
        }
        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        return res;
    }
}
