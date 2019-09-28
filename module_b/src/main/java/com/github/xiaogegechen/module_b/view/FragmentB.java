package com.github.xiaogegechen.module_b.view;

import android.view.View;
import android.widget.GridView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.module_b.R;
import com.github.xiaogegechen.module_b.adapter.ConstellationAdapter;
import com.github.xiaogegechen.module_b.model.Constellation;
import com.github.xiaogegechen.module_b.model.Params;
import com.github.xiaogegechen.module_b.presenter.FragmentBPresenterImpl;
import com.github.xiaogegechen.module_b.presenter.IFragmentBPresenter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_B_FRAGMENT_B_PATH;

@Route(path = MODULE_B_FRAGMENT_B_PATH)
public class FragmentB extends BaseFragment implements IFragmentBView{

    private static final String TAG = "FragmentB";

    private GridView mGridView;
    private IFragmentBPresenter mFragmentBPresenter;

    @Override
    public void initView(@NotNull View view) {
        mGridView = view.findViewById(R.id.module_b_grid_view);
    }

    @Override
    public void initData() {
        mFragmentBPresenter = new FragmentBPresenterImpl(obtainContext());
        mFragmentBPresenter.attach(this);
        List<Constellation> constellationList = new ArrayList<>();
        // TODO 以资源引用的方式使用这些字符串
        constellationList.add(new Constellation(R.drawable.baiyangzuo, "白羊座","3.21~4.19"));
        constellationList.add(new Constellation(R.drawable.jinniuzuo, "金牛座","4.20~5.20"));
        constellationList.add(new Constellation(R.drawable.shuangzizuo, "双子座","5.21~6.21"));
        constellationList.add(new Constellation(R.drawable.juxiezuo, "巨蟹座","6.22~7.22"));
        constellationList.add(new Constellation(R.drawable.shizizuo, "狮子座","7.23~8.22"));
        constellationList.add(new Constellation(R.drawable.chunvzuo, "处女座","8.23~9.22"));
        constellationList.add(new Constellation(R.drawable.tianchengzuo, "天秤座","9.23~10.23"));
        constellationList.add(new Constellation(R.drawable.tianxiezuo, "天蝎座","10.24~11.22"));
        constellationList.add(new Constellation(R.drawable.sheshouzuo, "射手座","11.23~12.21"));
        constellationList.add(new Constellation(R.drawable.mojiezuo, "摩羯座","12.22~1.19"));
        constellationList.add(new Constellation(R.drawable.shuipingzuo, "水瓶座","1.20~2.18"));
        constellationList.add(new Constellation(R.drawable.shuangyuzuo, "双鱼座","2.19~3.20"));
        mGridView.setAdapter(new ConstellationAdapter(constellationList, obtainContext()));
        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            LogUtil.d(TAG, "mGridView is click, position is: " + position);
            Constellation constellation = (Constellation) parent.getAdapter().getItem(position);
            // 传递星座名和iconId
            String consName = constellation.getName();
            int iconId = constellation.getIconId();
            Params params = new Params(consName, iconId);
            mFragmentBPresenter.gotoDetailActivity(params);
        });
    }

    @Override
    public void onDestroy() {
        mFragmentBPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_b_fragment_b;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(@NotNull String message) {

    }
}
