package org.fengye.school.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    public int pageSize = 10;

    public int currentPage = 0;


    public void addPage() {
        currentPage++;
    }


    public void refresh() {
        currentPage = 0;
    }

}
