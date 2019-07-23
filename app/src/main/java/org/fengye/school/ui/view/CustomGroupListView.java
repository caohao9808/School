package org.fengye.school.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;
import java.util.List;

public class CustomGroupListView extends QMUIGroupListView {


    public CustomGroupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGroupListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void reBuild(List<String> titles, List<List<ItemBean>> itemBeans) {

        List<QMUIGroupListView.Section> sections = new ArrayList<>();

        for (int i = 0; i < titles.size(); i++) {
            Section section = new Section(getContext());
            section.setTitle(titles.get(i));
            sections.add(section);
        }

        for (int i = 0; i < sections.size(); i++) {

            Section section = sections.get(i);

            for (ItemBean itemBean : itemBeans.get(i)) {
                section.addItemView(createItemView(itemBean.getDrawable(),
                        itemBean.title, itemBean.getDetail(),
                        itemBean.getOrientation(),
                        itemBean.getAccessoryType()),
                        itemBean.getListener());
            }
            section.addTo(this);

        }


    }

    public void addSession(String title, List<ItemBean> itemBeans) {


        Section section = new Section(getContext());
        section.setTitle(title);

        for (ItemBean itemBean : itemBeans) {
            section.addItemView(createItemView(itemBean.getDrawable(),
                    itemBean.title, itemBean.getDetail(),
                    itemBean.getOrientation(),
                    itemBean.getAccessoryType()),
                    itemBean.getListener());
        }
        section.addTo(this);


    }

    public static class ItemBean {
        private Drawable drawable;
        private String title;
        private String detail;
        private int orientation = HORIZONTAL;
        private int accessoryType = QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON;
        private OnClickListener listener;

        public ItemBean(String title) {
            this.title = title;
        }

        public ItemBean(String title, String detail) {
            this.title = title;
            this.detail = detail;
        }

        public ItemBean(Drawable drawable, String title) {
            this.drawable = drawable;
            this.title = title;
        }

        public ItemBean(Drawable drawable, String title, String detail) {
            this.drawable = drawable;
            this.title = title;
            this.detail = detail;
        }

        public ItemBean(Drawable drawable, String title, OnClickListener listener) {
            this.drawable = drawable;
            this.title = title;
            this.listener = listener;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public int getAccessoryType() {
            return accessoryType;
        }

        public void setAccessoryType(int accessoryType) {
            this.accessoryType = accessoryType;
        }

        public OnClickListener getListener() {
            return listener;
        }

        public void setListener(OnClickListener listener) {
            this.listener = listener;
        }
    }


}
